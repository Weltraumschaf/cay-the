package de.weltraumschaf.caythe.frontend.pascal.parsers;

import de.weltraumschaf.caythe.intermediate.TypeFactory;
import de.weltraumschaf.caythe.intermediate.symboltableimpl.Predefined;
import de.weltraumschaf.caythe.intermediate.Definition;
import de.weltraumschaf.caythe.frontend.TokenType;
import de.weltraumschaf.caythe.intermediate.TypeSpecification;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;
import de.weltraumschaf.caythe.frontend.Token;
import de.weltraumschaf.caythe.frontend.pascal.PascalTokenType;
import de.weltraumschaf.caythe.frontend.pascal.PascalTopDownParser;
import java.util.EnumSet;

import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.*;
import static de.weltraumschaf.caythe.frontend.pascal.PascalErrorCode.*;
import static de.weltraumschaf.caythe.intermediate.symboltableimpl.DefinitionImpl.*;
import static de.weltraumschaf.caythe.intermediate.symboltableimpl.SymbolTableKeyImpl.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class ConstantDefinitionsParser extends DeclarationsParser {

    public ConstantDefinitionsParser(PascalTopDownParser parent) {
        super(parent);
    }

    // Synchronization set for a constant identifier.
    private static final EnumSet<PascalTokenType> IDENTIFIER_SET =
        DeclarationsParser.TYPE_START_SET.clone();
    static {
        IDENTIFIER_SET.add(IDENTIFIER);
    }

    // Synchronization set for starting a constant.
    static final EnumSet<PascalTokenType> CONSTANT_START_SET =
        EnumSet.of(IDENTIFIER, INTEGER, REAL, PLUS, MINUS, STRING, SEMICOLON);

    // Synchronization set for the = token.
    private static final EnumSet<PascalTokenType> EQUALS_SET =
        CONSTANT_START_SET.clone();
    static {
        EQUALS_SET.add(EQUALS);
        EQUALS_SET.add(SEMICOLON);
    }

    // Synchronization set for the start of the next definition or declaration.
    private static final EnumSet<PascalTokenType> NEXT_START_SET =
        DeclarationsParser.TYPE_START_SET.clone();
    static {
        NEXT_START_SET.add(SEMICOLON);
        NEXT_START_SET.add(IDENTIFIER);
    }

    @Override
    public void parse(Token token) throws Exception {
        token = synchronize(IDENTIFIER_SET);

        // Loop to parse a sequence of constant definitions
        // separated by semicolons.
        while (token.getType() == IDENTIFIER) {
            String name = token.getText().toLowerCase();
            SymbolTableEntry constantId = symbolTableStack.lookupLocal(name);

            // Enter the new identifier into the symbol table
            // but don't set how it's defined yet.
            if (constantId == null) {
                constantId = symbolTableStack.enterLocal(name);
                constantId.appendLineNumber(token.getLineNumber());
            }
            else {
                errorHandler.flag(token, IDENTIFIER_REDEFINED, this);
                constantId = null;
            }

            token = nextToken();  // consume the identifier token

            // Synchronize on the = token.
            token = synchronize(EQUALS_SET);
            if (token.getType() == EQUALS) {
                token = nextToken();  // consume the =
            }
            else {
                errorHandler.flag(token, MISSING_EQUALS, this);
            }

            // Parse the constant value.
            Token constantToken = token;
            Object value = parseConstant(token);

            // Set identifier to be a constant and set its value.
            if (constantId != null) {
                constantId.setDefinition(CONSTANT);
                constantId.setAttribute(CONSTANT_VALUE, value);

                // Set the constant's type.
                TypeSpecification constantType =
                    constantToken.getType() == IDENTIFIER
                        ? getConstantType(constantToken)
                        : getConstantType(value);
                constantId.setTypeSpecification(constantType);
            }

            token = currentToken();
            TokenType tokenType = token.getType();

            // Look for one or more semicolons after a definition.
            if (tokenType == SEMICOLON) {
                while (token.getType() == SEMICOLON) {
                    token = nextToken();  // consume the ;
                }
            }
            // If at the start of the next definition or declaration,
            // then missing a semicolon.
            else if (NEXT_START_SET.contains(tokenType)) {
                errorHandler.flag(token, MISSING_SEMICOLON, this);
            }

            token = synchronize(IDENTIFIER_SET);
        }
    }

    protected Object parseConstant(Token token) throws Exception {
        TokenType sign = null;

        // Synchronize at the start of a constant.
        token = synchronize(CONSTANT_START_SET);
        TokenType tokenType = token.getType();

        // Plus or minus sign?
        if ((tokenType == PLUS) || (tokenType == MINUS)) {
            sign = tokenType;
            token = nextToken();  // consume sign
        }

        // Parse the constant.
        switch ((PascalTokenType) token.getType()) {

            case IDENTIFIER: {
                return parseIdentifierConstant(token, sign);
            }

            case INTEGER: {
                Integer value = (Integer) token.getValue();
                nextToken();  // consume the number
                return sign == MINUS ? -value : value;
            }

            case REAL: {
                Float value = (Float) token.getValue();
                nextToken();  // consume the number
                return sign == MINUS ? -value : value;
            }

            case STRING: {
                if (sign != null) {
                    errorHandler.flag(token, INVALID_CONSTANT, this);
                }

                nextToken();  // consume the string
                return (String) token.getValue();
            }

            default: {
                errorHandler.flag(token, INVALID_CONSTANT, this);
                return null;
            }
        }
    }

    protected Object parseIdentifierConstant(Token token, TokenType sign) throws Exception {
        String name = token.getText().toLowerCase();
        SymbolTableEntry id = symbolTableStack.lookup(name);

        nextToken();  // consume the identifier

        // The identifier must have already been defined
        // as an constant identifier.
        if (id == null) {
            errorHandler.flag(token, IDENTIFIER_UNDEFINED, this);
            return null;
        }

        Definition definition = id.getDefinition();

        if (definition == CONSTANT) {
            Object value = id.getAttribute(CONSTANT_VALUE);
            id.appendLineNumber(token.getLineNumber());

            if (value instanceof Integer) {
                return sign == MINUS ? -((Integer) value) : value;
            }
            else if (value instanceof Float) {
                return sign == MINUS ? -((Float) value) : value;
            }
            else if (value instanceof String) {
                if (sign != null) {
                    errorHandler.flag(token, INVALID_CONSTANT, this);
                }

                return value;
            }
            else {
                return null;
            }
        }
        else if (definition == ENUMERATION_CONSTANT) {
            Object value = id.getAttribute(CONSTANT_VALUE);
            id.appendLineNumber(token.getLineNumber());

            if (sign != null) {
                errorHandler.flag(token, INVALID_CONSTANT, this);
            }

            return value;
        }
        else if (definition == null) {
            errorHandler.flag(token, NOT_CONSTANT_IDENTIFIER, this);
            return null;
        }
        else {
            errorHandler.flag(token, INVALID_CONSTANT, this);
            return null;
        }
    }

    protected TypeSpecification getConstantType(Object value) {
        TypeSpecification constantType = null;

        if (value instanceof Integer) {
            constantType = Predefined.integerType;
        }
        else if (value instanceof Float) {
            constantType = Predefined.realType;
        }
        else if (value instanceof String) {
            if (((String) value).length() == 1) {
                constantType = Predefined.charType;
            }
            else {
                constantType = TypeFactory.createType((String) value);
            }
        }

        return constantType;
    }

    protected TypeSpecification getConstantType(Token identifier) {
        String name = identifier.getText().toLowerCase();
        SymbolTableEntry id = symbolTableStack.lookup(name);

        if (id == null) {
            return null;
        }

        Definition definition = id.getDefinition();

        if ((definition == CONSTANT) || (definition == ENUMERATION_CONSTANT)) {
            return id.getTypeSpec();
        }
        else {
            return null;
        }
    }
}

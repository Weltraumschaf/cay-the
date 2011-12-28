package de.weltraumschaf.caythe.frontend.pascal.parsers;

import de.weltraumschaf.caythe.intermediate.typeimpl.TypeKeyImpl;
import de.weltraumschaf.caythe.frontend.TokenType;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;
import java.util.ArrayList;
import de.weltraumschaf.caythe.intermediate.TypeFactory;
import de.weltraumschaf.caythe.intermediate.TypeSpecification;
import de.weltraumschaf.caythe.frontend.Token;
import de.weltraumschaf.caythe.frontend.pascal.PascalTokenType;
import de.weltraumschaf.caythe.frontend.pascal.PascalTopDownParser;
import java.util.EnumSet;

import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.*;
import static de.weltraumschaf.caythe.frontend.pascal.PascalErrorCode.*;
import static de.weltraumschaf.caythe.intermediate.typeimpl.TypeFormImpl.ENUMERATION;
import static de.weltraumschaf.caythe.intermediate.symboltableimpl.DefinitionImpl.*;
import static de.weltraumschaf.caythe.intermediate.symboltableimpl.SymbolTableKeyImpl.CONSTANT_VALUE;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class EnumerationTypeParser extends TypeSpecificationParser {

    public EnumerationTypeParser(PascalTopDownParser parent) {
        super(parent);
    }

    // Synchronization set to start an enumeration constant.
    private static final EnumSet<PascalTokenType> ENUM_CONSTANT_START_SET =
        EnumSet.of(IDENTIFIER, COMMA);

    // Synchronization set to follow an enumeration definition.
    private static final EnumSet<PascalTokenType> ENUM_DEFINITION_FOLLOW_SET =
        EnumSet.of(RIGHT_PAREN, SEMICOLON);
    static {
        ENUM_DEFINITION_FOLLOW_SET.addAll(DeclarationsParser.VAR_START_SET);
    }

    @Override
    public TypeSpecification parse(Token token) throws Exception {
        TypeSpecification enumerationType = TypeFactory.createType(ENUMERATION);
        int value = -1;
        ArrayList<SymbolTableEntry> constants = new ArrayList<SymbolTableEntry>();

        token = nextToken();  // consume the opening (

        do {
            token = synchronize(ENUM_CONSTANT_START_SET);
            parseEnumerationIdentifier(token, ++value, enumerationType,
                                       constants);

            token = currentToken();
            TokenType tokenType = token.getType();

            // Look for the comma.
            if (tokenType == COMMA) {
                token = nextToken();  // consume the comma

                if (ENUM_DEFINITION_FOLLOW_SET.contains(token.getType())) {
                    errorHandler.flag(token, MISSING_IDENTIFIER, this);
                }
            }
            else if (ENUM_CONSTANT_START_SET.contains(tokenType)) {
                errorHandler.flag(token, MISSING_COMMA, this);
            }
        } while (!ENUM_DEFINITION_FOLLOW_SET.contains(token.getType()));

        // Look for the closing ).
        if (token.getType() == RIGHT_PAREN) {
            token = nextToken();  // consume the )
        }
        else {
            errorHandler.flag(token, MISSING_RIGHT_PAREN, this);
        }

        enumerationType.setAttribute(TypeKeyImpl.ENUMERATION_CONSTANTS, constants);
        return enumerationType;
    }

    /**
     * Parse an enumeration identifier.
     * @param token the current token.
     * @param value the identifier's integer value (sequence number).
     * @param enumerationType the enumeration type specification.
     * @param constants the array of symbol table entries for the
     * enumeration constants.
     * @throws Exception if an error occurred.
     */
    private void parseEnumerationIdentifier(Token token, int value,
                                            TypeSpecification enumerationType,
                                            ArrayList<SymbolTableEntry> constants)
        throws Exception
    {
        TokenType tokenType = token.getType();

        if (tokenType == IDENTIFIER) {
            String name = token.getText().toLowerCase();
            SymbolTableEntry constantId = symbolTableStack.lookupLocal(name);

            if (constantId != null) {
                errorHandler.flag(token, IDENTIFIER_REDEFINED, this);
            }
            else {
                constantId = symbolTableStack.enterLocal(name);
                constantId.setDefinition(ENUMERATION_CONSTANT);
                constantId.setTypeSpecification(enumerationType);
                constantId.setAttribute(CONSTANT_VALUE, value);
                constantId.appendLineNumber(token.getLineNumber());
                constants.add(constantId);
            }

            token = nextToken();  // consume the identifier
        }
        else {
            errorHandler.flag(token, MISSING_IDENTIFIER, this);
        }
    }
}

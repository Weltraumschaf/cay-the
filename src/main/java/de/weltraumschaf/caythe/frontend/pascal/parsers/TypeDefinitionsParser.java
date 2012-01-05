package de.weltraumschaf.caythe.frontend.pascal.parsers;

import de.weltraumschaf.caythe.frontend.TokenType;
import de.weltraumschaf.caythe.intermediate.TypeSpecification;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;
import de.weltraumschaf.caythe.frontend.Token;
import de.weltraumschaf.caythe.frontend.pascal.PascalTokenType;
import de.weltraumschaf.caythe.frontend.pascal.PascalTopDownParser;
import de.weltraumschaf.caythe.intermediate.symboltableimpl.DefinitionImpl;
import java.util.EnumSet;

import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.*;
import static de.weltraumschaf.caythe.frontend.pascal.PascalErrorCode.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class TypeDefinitionsParser extends DeclarationsParser {

    public TypeDefinitionsParser(PascalTopDownParser parent) {
        super(parent);
    }

    // Synchronization set for a type identifier.
    private static final EnumSet<PascalTokenType> IDENTIFIER_SET =
        DeclarationsParser.VAR_START_SET.clone();
    static {
        IDENTIFIER_SET.add(IDENTIFIER);
    }

    // Synchronization set for the = token.
    private static final EnumSet<PascalTokenType> EQUALS_SET =
        ConstantDefinitionsParser.CONSTANT_START_SET.clone();
    static {
        EQUALS_SET.add(EQUALS);
        EQUALS_SET.add(SEMICOLON);
    }

    // Synchronization set for what follows a definition or declaration.
    private static final EnumSet<PascalTokenType> FOLLOW_SET =
        EnumSet.of(SEMICOLON);

    // Synchronization set for the start of the next definition or declaration.
    private static final EnumSet<PascalTokenType> NEXT_START_SET =
        DeclarationsParser.VAR_START_SET.clone();
    static {
        NEXT_START_SET.add(SEMICOLON);
        NEXT_START_SET.add(IDENTIFIER);
    }

    @Override
    public SymbolTableEntry parse(Token token, SymbolTableEntry parentId) throws Exception {
        token = synchronize(IDENTIFIER_SET);

        // Loop to parse a sequence of type definitions
        // separated by semicolons.
        while (token.getType() == IDENTIFIER) {
            String name = token.getText().toLowerCase();
            SymbolTableEntry typeId = symbolTableStack.lookupLocal(name);

            // Enter the new identifier into the symbol table
            // but don't set how it's defined yet.
            if (typeId == null) {
                typeId = symbolTableStack.enterLocal(name);
                typeId.appendLineNumber(token.getLineNumber());
            }
            else {
                errorHandler.flag(token, IDENTIFIER_REDEFINED, this);
                typeId = null;
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

            // Parse the type specification.
            TypeSpecificationParser typeSpecificationParser =
                new TypeSpecificationParser(this);
            TypeSpecification type = typeSpecificationParser.parse(token);

            // Set identifier to be a type and set its type specificationt.
            if (typeId != null) {
                typeId.setDefinition(DefinitionImpl.TYPE);
            }

            // Cross-link the type identifier and the type specification.
            if ((type != null) && (typeId != null)) {
                if (type.getIdentifier() == null) {
                    type.setIdentifier(typeId);
                }

                typeId.setTypeSpecification(type);
            }
            else {
                token = synchronize(FOLLOW_SET);
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

        return null;
    }
}

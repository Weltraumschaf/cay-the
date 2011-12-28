package de.weltraumschaf.caythe.frontend.pascal.parsers;

import de.weltraumschaf.caythe.intermediate.symboltableimpl.DefinitionImpl;
import de.weltraumschaf.caythe.intermediate.Definition;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;
import de.weltraumschaf.caythe.intermediate.TypeSpecification;
import de.weltraumschaf.caythe.frontend.Token;
import de.weltraumschaf.caythe.frontend.pascal.PascalTokenType;
import de.weltraumschaf.caythe.frontend.pascal.PascalTopDownParser;
import java.util.EnumSet;

import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.*;
import static de.weltraumschaf.caythe.intermediate.symboltableimpl.DefinitionImpl.*;
import static de.weltraumschaf.caythe.frontend.pascal.PascalErrorCode.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class SimpleTypeParser extends TypeSpecificationParser {

    public SimpleTypeParser(PascalTopDownParser parent) {
        super(parent);
    }

    // Synchronization set for starting a simple type specification.
    static final EnumSet<PascalTokenType> SIMPLE_TYPE_START_SET =
        ConstantDefinitionsParser.CONSTANT_START_SET.clone();
    static {
        SIMPLE_TYPE_START_SET.add(LEFT_PAREN);
        SIMPLE_TYPE_START_SET.add(COMMA);
        SIMPLE_TYPE_START_SET.add(SEMICOLON);
    }

    @Override
    public TypeSpecification parse(Token token) throws Exception {
        // Synchronize at the start of a simple type specification.
        token = synchronize(SIMPLE_TYPE_START_SET);

        switch ((PascalTokenType) token.getType()) {

            case IDENTIFIER: {
                String name = token.getText().toLowerCase();
                SymbolTableEntry id = symbolTableStack.lookup(name);

                if (id != null) {
                    Definition definition = id.getDefinition();

                    // It's either a type identifier
                    // or the start of a subrange type.
                    if (definition == DefinitionImpl.TYPE) {
                        id.appendLineNumber(token.getLineNumber());
                        token = nextToken();  // consume the identifier

                        // Return the type of the referent type.
                        return id.getTypeSpec();
                    }
                    else if ((definition != CONSTANT) &&
                             (definition != ENUMERATION_CONSTANT)) {
                        errorHandler.flag(token, NOT_TYPE_IDENTIFIER, this);
                        token = nextToken();  // consume the identifier
                        return null;
                    }
                    else {
                        SubrangeTypeParser subrangeTypeParser =
                            new SubrangeTypeParser(this);
                        return subrangeTypeParser.parse(token);
                    }
                }
                else {
                    errorHandler.flag(token, IDENTIFIER_UNDEFINED, this);
                    token = nextToken();  // consume the identifier
                    return null;
                }
            }

            case LEFT_PAREN: {
                EnumerationTypeParser enumerationTypeParser =
                    new EnumerationTypeParser(this);
                return enumerationTypeParser.parse(token);
            }

            case COMMA:
            case SEMICOLON: {
                errorHandler.flag(token, INVALID_TYPE, this);
                return null;
            }

            default: {
                SubrangeTypeParser subrangeTypeParser =
                    new SubrangeTypeParser(this);
                return subrangeTypeParser.parse(token);
            }
        }
    }
}

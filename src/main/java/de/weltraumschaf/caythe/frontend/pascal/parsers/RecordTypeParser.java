package de.weltraumschaf.caythe.frontend.pascal.parsers;

import de.weltraumschaf.caythe.intermediate.symboltableimpl.DefinitionImpl;
import de.weltraumschaf.caythe.intermediate.typeimpl.TypeKeyImpl;
import de.weltraumschaf.caythe.intermediate.typeimpl.TypeFormImpl;
import de.weltraumschaf.caythe.intermediate.TypeFactory;
import de.weltraumschaf.caythe.intermediate.TypeSpecification;
import de.weltraumschaf.caythe.frontend.Token;
import de.weltraumschaf.caythe.frontend.pascal.PascalTokenType;
import de.weltraumschaf.caythe.frontend.pascal.PascalTopDownParser;
import java.util.EnumSet;

import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.*;
import static de.weltraumschaf.caythe.frontend.pascal.PascalErrorCode.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class RecordTypeParser extends TypeSpecificationParser {

    public RecordTypeParser(PascalTopDownParser parent) {
        super(parent);
    }

    // Synchronization set for the END.
    private static final EnumSet<PascalTokenType> END_SET =
        DeclarationsParser.VAR_START_SET.clone();
    static {
        END_SET.add(END);
        END_SET.add(SEMICOLON);
    }

    @Override
    public TypeSpecification parse(Token token) throws Exception {
        TypeSpecification recordType = TypeFactory.createType(TypeFormImpl.RECORD);
        token = nextToken();  // consume RECORD

        // Push a symbol table for the RECORD type specification.
        recordType.setAttribute(TypeKeyImpl.RECORD_SYMTAB, symbolTableStack.push());

        // Parse the field declarations.
        VariableDeclarationsParser variableDeclarationsParser =
            new VariableDeclarationsParser(this);
        variableDeclarationsParser.setDefinition(DefinitionImpl.FIELD);
        variableDeclarationsParser.parse(token);

        // Pop off the record's symbol table.
        symbolTableStack.pop();

        // Synchronize at the END.
        token = synchronize(END_SET);

        // Look for the END.
        if (token.getType() == END) {
            token = nextToken();  // consume END
        }
        else {
            errorHandler.flag(token, MISSING_END, this);
        }

        return recordType;
    }
}

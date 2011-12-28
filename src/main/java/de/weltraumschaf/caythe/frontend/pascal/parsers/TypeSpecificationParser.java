package de.weltraumschaf.caythe.frontend.pascal.parsers;

import de.weltraumschaf.caythe.frontend.Token;
import de.weltraumschaf.caythe.frontend.pascal.PascalTokenType;
import de.weltraumschaf.caythe.frontend.pascal.PascalTopDownParser;
import de.weltraumschaf.caythe.intermediate.TypeSpecification;
import java.util.EnumSet;

import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class TypeSpecificationParser extends PascalTopDownParser {

    public TypeSpecificationParser(PascalTopDownParser parent) {
        super(parent);
    }

    // Synchronization set for starting a type specification.
    static final EnumSet<PascalTokenType> TYPE_START_SET =
        SimpleTypeParser.SIMPLE_TYPE_START_SET.clone();
    static {
        TYPE_START_SET.add(ARRAY);
        TYPE_START_SET.add(RECORD);
        TYPE_START_SET.add(SEMICOLON);
    }

    public TypeSpecification parse(Token token) throws Exception {
        // Synchronize at the start of a type specification.
        token = synchronize(TYPE_START_SET);

        switch ((PascalTokenType) token.getType()) {
            case ARRAY: {
                ArrayTypeParser arrayTypeParser = new ArrayTypeParser(this);
                return arrayTypeParser.parse(token);
            }

            case RECORD: {
                RecordTypeParser recordTypeParser = new RecordTypeParser(this);
                return recordTypeParser.parse(token);
            }

            default: {
                SimpleTypeParser simpleTypeParser = new SimpleTypeParser(this);
                return simpleTypeParser.parse(token);
            }
        }
    }
}

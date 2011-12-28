package de.weltraumschaf.caythe.frontend.pascal.parsers;

import de.weltraumschaf.caythe.frontend.Token;
import de.weltraumschaf.caythe.frontend.TokenType;
import de.weltraumschaf.caythe.frontend.pascal.PascalTokenType;
import de.weltraumschaf.caythe.frontend.pascal.PascalTopDownParser;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;
import de.weltraumschaf.caythe.intermediate.TypeFactory;
import de.weltraumschaf.caythe.intermediate.TypeForm;
import de.weltraumschaf.caythe.intermediate.TypeSpecification;
import java.util.ArrayList;
import java.util.EnumSet;

import static de.weltraumschaf.caythe.frontend.pascal.PascalErrorCode.*;
import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.*;
import static de.weltraumschaf.caythe.intermediate.typeimpl.TypeFormImpl.ARRAY;
import static de.weltraumschaf.caythe.intermediate.typeimpl.TypeFormImpl.SUBRANGE;
import static de.weltraumschaf.caythe.intermediate.typeimpl.TypeFormImpl.ENUMERATION;
import static de.weltraumschaf.caythe.intermediate.typeimpl.TypeKeyImpl.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class ArrayTypeParser extends TypeSpecificationParser {

    public ArrayTypeParser(PascalTopDownParser parent) {
        super(parent);
    }

    // Synchronization set for the [ token.
    private static final EnumSet<PascalTokenType> LEFT_BRACKET_SET =
        SimpleTypeParser.SIMPLE_TYPE_START_SET.clone();
    static {
        LEFT_BRACKET_SET.add(LEFT_BRACKET);
        LEFT_BRACKET_SET.add(RIGHT_BRACKET);
    }

    // Synchronization set for the ] token.
    private static final EnumSet<PascalTokenType> RIGHT_BRACKET_SET =
        EnumSet.of(RIGHT_BRACKET, OF, SEMICOLON);

    // Synchronization set for OF.
    private static final EnumSet<PascalTokenType> OF_SET =
        TypeSpecificationParser.TYPE_START_SET.clone();
    static {
        OF_SET.add(OF);
        OF_SET.add(SEMICOLON);
    }

    @Override
    public TypeSpecification parse(Token token) throws Exception {
        TypeSpecification arrayType = TypeFactory.createType(ARRAY);
        token = nextToken();  // consume ARRAY

        // Synchronize at the [ token.
        token = synchronize(LEFT_BRACKET_SET);

        if (token.getType() != LEFT_BRACKET) {
            errorHandler.flag(token, MISSING_LEFT_BRACKET, this);
        }

        // Parse the list of index types.
        TypeSpecification elementType = parseIndexTypeList(token, arrayType);

        // Synchronize at the ] token.
        token = synchronize(RIGHT_BRACKET_SET);
        if (token.getType() == RIGHT_BRACKET) {
            token = nextToken();  // consume [
        }
        else {
            errorHandler.flag(token, MISSING_RIGHT_BRACKET, this);
        }

        // Synchronize at OF.
        token = synchronize(OF_SET);
        if (token.getType() == OF) {
            token = nextToken();  // consume OF
        }
        else {
            errorHandler.flag(token, MISSING_OF, this);
        }

        // Parse the element type.
        elementType.setAttribute(ARRAY_ELEMENT_TYPE, parseElementType(token));

        return arrayType;
    }

    // Synchronization set to start an index type.
    private static final EnumSet<PascalTokenType> INDEX_START_SET =
        SimpleTypeParser.SIMPLE_TYPE_START_SET.clone();
    static {
        INDEX_START_SET.add(COMMA);
    }

    // Synchronization set to end an index type.
    private static final EnumSet<PascalTokenType> INDEX_END_SET =
        EnumSet.of(RIGHT_BRACKET, OF, SEMICOLON);

    // Synchronization set to follow an index type.
    private static final EnumSet<PascalTokenType> INDEX_FOLLOW_SET =
        INDEX_START_SET.clone();
    static {
        INDEX_FOLLOW_SET.addAll(INDEX_END_SET);
    }

    /**
     * Parse the list of index type specifications.
     * @param token the current token.
     * @param arrayType the current array type specification.
     * @return the element type specification.
     * @throws Exception if an error occurred.
     */
    private TypeSpecification parseIndexTypeList(Token token, TypeSpecification arrayType) throws Exception {
        TypeSpecification elementType = arrayType;
        boolean anotherIndex = false;

        token = nextToken();  // consume the [ token

        // Parse the list of index type specifications.
        do {
            anotherIndex = false;

            // Parse the index type.
            token = synchronize(INDEX_START_SET);
            parseIndexType(token, elementType);

            // Synchronize at the , token.
            token = synchronize(INDEX_FOLLOW_SET);
            TokenType tokenType = token.getType();

            if ((tokenType != COMMA) && (tokenType != RIGHT_BRACKET)) {
                if (INDEX_START_SET.contains(tokenType)) {
                    errorHandler.flag(token, MISSING_COMMA, this);
                    anotherIndex = true;
                }
            }

            // Create an ARRAY element type object
            // for each subsequent index type.
            else if (tokenType == COMMA) {
                TypeSpecification newElementType = TypeFactory.createType(ARRAY);
                elementType.setAttribute(ARRAY_ELEMENT_TYPE, newElementType);
                elementType = newElementType;

                token = nextToken();  // consume the , token
                anotherIndex = true;
            }
        } while (anotherIndex);

        return elementType;
    }

    private void parseIndexType(Token token, TypeSpecification arrayType) throws Exception {
        SimpleTypeParser simpleTypeParser = new SimpleTypeParser(this);
        TypeSpecification indexType = simpleTypeParser.parse(token);
        arrayType.setAttribute(ARRAY_INDEX_TYPE, indexType);

        if (indexType == null) {
            return;
        }

        TypeForm form = indexType.getForm();
        int count = 0;

        // Check the index type and set the element count.
        if (form == SUBRANGE) {
            Integer minValue =
                (Integer) indexType.getAttribute(SUBRANGE_MIN_VALUE);
            Integer maxValue =
                (Integer) indexType.getAttribute(SUBRANGE_MAX_VALUE);

            if ((minValue != null) && (maxValue != null)) {
                count = maxValue - minValue + 1;
            }
        }
        else if (form == ENUMERATION) {
            ArrayList<SymbolTableEntry> constants = (ArrayList<SymbolTableEntry>)
                indexType.getAttribute(ENUMERATION_CONSTANTS);
            count = constants.size();
        }
        else {
            errorHandler.flag(token, INVALID_INDEX_TYPE, this);
        }

        arrayType.setAttribute(ARRAY_ELEMENT_COUNT, count);
    }

    private TypeSpecification parseElementType(Token token) throws Exception {
        TypeSpecificationParser typeSpecificationParser =
            new TypeSpecificationParser(this);
        return typeSpecificationParser.parse(token);
    }
}

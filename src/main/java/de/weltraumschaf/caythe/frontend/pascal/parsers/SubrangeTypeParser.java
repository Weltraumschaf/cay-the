package de.weltraumschaf.caythe.frontend.pascal.parsers;

import de.weltraumschaf.caythe.frontend.TokenType;
import de.weltraumschaf.caythe.intermediate.TypeFactory;
import de.weltraumschaf.caythe.frontend.Token;
import de.weltraumschaf.caythe.frontend.pascal.PascalTopDownParser;
import de.weltraumschaf.caythe.intermediate.TypeSpecification;
import de.weltraumschaf.caythe.intermediate.symboltableimpl.Predefined;
import de.weltraumschaf.caythe.intermediate.typeimpl.TypeFormImpl;

import static de.weltraumschaf.caythe.intermediate.typeimpl.TypeKeyImpl.*;
import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.*;
import static de.weltraumschaf.caythe.frontend.pascal.PascalErrorCode.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class SubrangeTypeParser extends TypeSpecificationParser {

    public SubrangeTypeParser(PascalTopDownParser parent) {
        super(parent);
    }

    @Override
    public TypeSpecification parse(Token token) throws Exception {
        TypeSpecification subrangeType = TypeFactory.createType(TypeFormImpl.SUBRANGE);
        Object minValue = null;
        Object maxValue = null;

        // Parse the minimum constant.
        Token constantToken = token;
        ConstantDefinitionsParser constantParser =
            new ConstantDefinitionsParser(this);
        minValue = constantParser.parseConstant(token);

        // Set the minimum constant's type.
        TypeSpecification minType = constantToken.getType() == IDENTIFIER
                               ? constantParser.getConstantType(constantToken)
                               : constantParser.getConstantType(minValue);

        minValue = checkValueType(constantToken, minValue, minType);

        token = currentToken();
        Boolean sawDotDot = false;

        // Look for the .. token.
        if (token.getType() == DOT_DOT) {
            token = nextToken();  // consume the .. token
            sawDotDot = true;
        }

        TokenType tokenType = token.getType();

        // At the start of the maximum constant?
        if (ConstantDefinitionsParser.CONSTANT_START_SET.contains(tokenType)) {
            if (!sawDotDot) {
                errorHandler.flag(token, MISSING_DOT_DOT, this);
            }

            // Parse the maximum constant.
            token = synchronize(ConstantDefinitionsParser.CONSTANT_START_SET);
            constantToken = token;
            maxValue = constantParser.parseConstant(token);

            // Set the maximum constant's type.
            TypeSpecification maxType = constantToken.getType() == IDENTIFIER
                               ? constantParser.getConstantType(constantToken)
                               : constantParser.getConstantType(maxValue);

            maxValue = checkValueType(constantToken, maxValue, maxType);

            // Are the min and max value types valid?
            if ((minType == null) || (maxType == null)) {
                errorHandler.flag(constantToken, INCOMPATIBLE_TYPES, this);
            }

            // Are the min and max value types the same?
            else if (minType != maxType) {
                errorHandler.flag(constantToken, INVALID_SUBRANGE_TYPE, this);
            }

            // Min value > max value?
            else if ((minValue != null) && (maxValue != null) &&
                     ((Integer) minValue >= (Integer) maxValue)) {
                errorHandler.flag(constantToken, MIN_GT_MAX, this);
            }
        }
        else {
            errorHandler.flag(constantToken, INVALID_SUBRANGE_TYPE, this);
        }

        subrangeType.setAttribute(SUBRANGE_BASE_TYPE, minType);
        subrangeType.setAttribute(SUBRANGE_MIN_VALUE, minValue);
        subrangeType.setAttribute(SUBRANGE_MAX_VALUE, maxValue);

        return subrangeType;
    }

    /**
     * Check a value of a type specification.
     * @param token the current token.
     * @param value the value.
     * @param type the type specifiction.
     * @return the value.
     */
    private Object checkValueType(Token token, Object value, TypeSpecification type) {
        if (type == null) {
            return value;
        }

        if (type == Predefined.integerType) {
            return value;
        }
        else if (type == Predefined.charType) {
            char ch = ((String) value).charAt(0);
            return Character.getNumericValue(ch);
        }
        else if (type.getForm() == TypeFormImpl.ENUMERATION) {
            return value;
        }
        else {
            errorHandler.flag(token, INVALID_SUBRANGE_TYPE, this);
            return value;
        }
    }
}

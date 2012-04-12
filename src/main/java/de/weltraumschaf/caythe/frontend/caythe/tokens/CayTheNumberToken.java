package de.weltraumschaf.caythe.frontend.caythe.tokens;

import de.weltraumschaf.caythe.frontend.Source;
import static de.weltraumschaf.caythe.frontend.caythe.CayTheErrorCode.*;
import de.weltraumschaf.caythe.frontend.caythe.CayTheToken;
import static de.weltraumschaf.caythe.frontend.caythe.CayTheTokenType.*;
import de.weltraumschaf.caythe.util.NumberComputer;
import de.weltraumschaf.caythe.util.NumberComputer.RangeException;

/**
 * Extracts numbers (float and integer) tokens from given source.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CayTheNumberToken extends CayTheToken {

    public CayTheNumberToken(Source source) throws Exception {
        super(source);
    }

    @Override
    public void customExtraction() throws Exception {
	StringBuilder textBuffer = new StringBuilder();  // token's characters
        extractNumber(textBuffer);
        text = textBuffer.toString();
    }

    /**
     * Does not consider for leading signs only the absolute number will be extracted.
     * Leading signs should be consumed as {@link CayTheSpecialSymbolToken}.
     *
     * @param textBuffer
     * @throws Exception
     */
    protected void extractNumber(StringBuilder textBuffer) throws Exception {
        String wholeDigits    = null;  // digits before the decimal point
        String fractionDigits = null;  // digits after the decimal point
        String exponentDigits = null;  // exponent digits
        char exponentSign     = '+';   // exponent sign '+' or '-'
        char currentChar;              // current character

        type = INTEGER;  // assume INTEGER token type for now

        // Extract the digits of the whole part of the number.
        wholeDigits = unsignedIntegerDigits(textBuffer);
        if (type == ERROR) {
            return;
        }

        // Is there a . ?
        // It could be a decimal point or the start of a .. token.
        currentChar = currentChar();

        if (currentChar == '.') {
            type = REAL;  // decimal point, so token type is REAL
            textBuffer.append(currentChar);
            nextChar();  // consume decimal point

            // Collect the digits of the fraction part of the number.
            fractionDigits = unsignedIntegerDigits(textBuffer);

            if (ERROR == type) {
                return;
            }
        }

        // Is there an exponent part?
        // There cannot be an exponent if we already saw a ".." token.
        currentChar = currentChar();

        if ((currentChar == 'E') || (currentChar == 'e')) {
            type = REAL;  // exponent, so token type is REAL
            textBuffer.append(currentChar);
            nextChar();  // consume 'E' or 'e'
            currentChar = currentChar();

            // Exponent sign?
            if ((currentChar == '+') || (currentChar == '-')) {
                textBuffer.append(currentChar);
                exponentSign = currentChar;
                nextChar();  // consume '+' or '-'
            }

            // Extract the digits of the exponent.
            exponentDigits = unsignedIntegerDigits(textBuffer);
        }

        // Compute the value of an integer number token.
        if (INTEGER == type) {
            try {
                int integerValue = NumberComputer.computeIntegerValue(wholeDigits);
                value = new Integer(integerValue);
            }
            catch (RangeException ex) {
                type  = ERROR;
                value = RANGE_INTEGER;
            }
        }
        // Compute the value of a real number token.
        else if (REAL == type) {
            try {
                float floatValue = NumberComputer.computeFloatValue(wholeDigits,
                                                                    fractionDigits,
                                                                    exponentDigits,
                                                                    exponentSign);
                value = new Float(floatValue);
            }
            catch (NumberComputer.RangeException ex) {
                type  = ERROR;
                value = RANGE_REAL;
            }
        }
    }

    private String unsignedIntegerDigits(StringBuilder textBuffer) throws Exception {
        char currentChar = currentChar();

        // Must have at least one digit.
        if (!Character.isDigit(currentChar)) {
            type  = ERROR;
            value = INVALID_NUMBER;
            return null;
        }

        // Extract the digits.
        StringBuilder digits = new StringBuilder();

        while (Character.isDigit(currentChar)) {
            textBuffer.append(currentChar);
            digits.append(currentChar);
            nextChar();  // consume digit
            currentChar = currentChar();
        }

        return digits.toString();
    }

}

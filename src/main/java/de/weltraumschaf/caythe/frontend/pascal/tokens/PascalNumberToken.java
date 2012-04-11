package de.weltraumschaf.caythe.frontend.pascal.tokens;

import de.weltraumschaf.caythe.frontend.Source;
import static de.weltraumschaf.caythe.frontend.pascal.PascalErrorCode.*;
import de.weltraumschaf.caythe.frontend.pascal.PascalToken;
import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.*;
import de.weltraumschaf.caythe.util.NumberComputer;

/**
 * Extracts numbers (float and integer) tokens from given source.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class PascalNumberToken extends PascalToken {

    public PascalNumberToken(Source source) throws Exception {
        super(source);
    }

    @Override
    public void extract() throws Exception {
	StringBuilder textBuffer = new StringBuilder();  // token's characters
        extractNumber(textBuffer);
        text = textBuffer.toString();
    }

    /**
     * Does not consider for leading signs only the absolute number will be extracted.
     * Leading signs should be consumed as {@link PascalSpecialSymbolToken}.
     * 
     * @param textBuffer
     * @throws Exception
     */
    protected void extractNumber(StringBuilder textBuffer) throws Exception {
        String wholeDigits    = null;  // digits before the decimal point
        String fractionDigits = null;  // digits after the decimal point
        String exponentDigits = null;  // exponent digits
        char exponentSign     = '+';   // exponent sign '+' or '-'
        boolean sawDotDot     = false; // true if saw .. token
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
            if (peekChar() == '.') {
                sawDotDot = true;  // it's a ".." token, so don't consume it
            }
            else {
                type = REAL;  // decimal point, so token type is REAL
                textBuffer.append(currentChar);
                nextChar();  // consume decimal point

                // Collect the digits of the fraction part of the number.
                fractionDigits = unsignedIntegerDigits(textBuffer);

                if (ERROR == type) {
                    return;
                }
            }
        }

        // Is there an exponent part?
        // There cannot be an exponent if we already saw a ".." token.
        currentChar = currentChar();

        if (!sawDotDot && ((currentChar == 'E') || (currentChar == 'e'))) {
            type = REAL;  // exponent, so token type is REAL
            textBuffer.append(currentChar);
            currentChar = nextChar();  // consume 'E' or 'e'

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
            int integerValue = computeIntegerValue(wholeDigits);

            if (ERROR != type) {
                value = new Integer(integerValue);
            }
        }
        // Compute the value of a real number token.
        else if (REAL == type) {
            float floatValue = computeFloatValue(
		wholeDigits,
		fractionDigits,
		exponentDigits,
		exponentSign
	    );

            if (ERROR != type) {
                value = new Float(floatValue);
            }
        }
    }

    /**
     * Returns digit characters from the passed in {@link StringBuilder} until first non digit
     * character.
     *
     * Digit characters are all characters true for {@link Character#isDigit(char)}.
     * There must be at least one digit character, unless the token is marked
     * erroneous and the method returns null.
     *
     * @param textBuffer
     * @return
     * @throws Exception
     */
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
            currentChar = nextChar();  // consume digit
        }

        return digits.toString();
    }

    private int computeIntegerValue(String digits) {
        try {
            return NumberComputer.computeIntegerValue(digits);
        }
        catch (NumberComputer.RangeException ex) {
            type  = ERROR;
            value = RANGE_INTEGER;
            return 0;
        }
    }

    private float computeFloatValue(String wholeDigits, String fractionDigits,
                                    String exponentDigits, char exponentSign)
    {
        try {
            return NumberComputer.computeFloatValue(wholeDigits, fractionDigits, exponentDigits, exponentSign);
        }
        catch (NumberComputer.RangeException ex) {
            type  = ERROR;
            value = RANGE_REAL;
            return 0.0f;
        }
    }
}

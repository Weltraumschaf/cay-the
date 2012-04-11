package de.weltraumschaf.caythe.util;

/**
 * Computes either floats or integers from given strings.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class NumberComputer {

    /**
     * Signals if the passed in strings does not fit into the runtime
     * float or integer representations.
     */
    public static class RangeException extends Throwable {

        /**
         * Signals integer range exception.
         */
        public static final int RANGE_INTEGER = 1;
        /**
         * Signals float range exception.
         */
        public static final int RANGE_FLOAT = 2;

        /**
         * The error code.
         *
         * By default this is 0.
         */
        private int code;

        /**
         * Designated constructor.
         *
         * @param message The error message.
         * @param code The error code.
         * @param cause Former thrown exception.
         */
        public RangeException(final String message, final int code, Throwable cause) {
            super(message, cause);
            this.code = code;
        }

        /**
         * Creates an error where {@link Throwable#getCause()} is null.
         *
         * @param message The error message.
         * @param code The error code.
         */
        public RangeException(String message, int code) {
            this(message, code, null);
        }

        /**
         * Creates an error where {@link code} is -1 and {@link Throwable#getCause()}
         * is null.
         *
         * @param message The error message.
         */
        public RangeException(String message) {
            this(message, 0, null);
        }

        /**
         * Returns the error code.
         *
         * @return
         */
        public int getCode() {
            return code;
        }
    }

    /**
     * Max size of float exponent.
     */
    private static final int MAX_EXPONENT = 37;

    /**
     * Computes a native positiv integer value from given string.
     *
     * Eg.
     * "42" reultsin (Java.lang.Integer)42
     *
     * @param digits
     * @return
     * @throws de.weltraumschaf.caythe.util.NumberComputer.RangeException
     */
    public static int computeIntegerValue(String digits) throws RangeException {
        // Return 0 if no digits.
        if (null == digits) {
            return 0;
        }

        int integerValue = 0;
        int prevValue    = -1; // overflow occurred if prevValue > integerValue
        int index        = 0;

        // Loop over the digits to compute the integer value
        // as long as there is no overflow.
        while (( index < digits.length() ) && ( integerValue >= prevValue )) {
            prevValue    = integerValue;
            integerValue = 10 * integerValue
                         + Character.getNumericValue(digits.charAt(index++));
        }

        // No overflow:  Return the integer value.
        if (integerValue >= prevValue) {
            return integerValue;
        }
        // Overflow:  Set the integer out of range error.
        else {
            throw new RangeException("Given integer is out of range: " + digits, RangeException.RANGE_INTEGER);
        }
    }

    public static float computeFloatValue(String wholeDigits, String fractionDigits) throws RangeException {
        return computeFloatValue(wholeDigits, fractionDigits, null, '+');
    }

    /**
     * Computes a float value from the given string parts of a float string.
     *
     * @param wholeDigits
     * @param fractionDigits
     * @param exponentDigits
     * @param exponentSign
     * @return
     * @throws de.weltraumschaf.caythe.util.NumberComputer.RangeException
     */
    public static float computeFloatValue(String wholeDigits, String fractionDigits,
                                    String exponentDigits, char exponentSign) throws RangeException
    {
        double floatValue = 0.0;
        int exponentValue = (null != exponentDigits)
                          ? computeIntegerValue(exponentDigits)
                          : 0;
        String digits     = wholeDigits; // whole and fraction digits

        // Negate the exponent if the exponent sign is '-'.
        if (exponentSign == '-') {
            exponentValue = -exponentValue;
        }

        // If there are any fraction digits, adjust the exponent value
        // and append the fraction digits.
        if (fractionDigits != null) {
            exponentValue -= fractionDigits.length();
            digits += fractionDigits;
        }

        // Check for a real number out of range error.
        if (Math.abs(exponentValue + wholeDigits.length()) > MAX_EXPONENT) {
            throw new RangeException("Max exponent exceeded!", RangeException.RANGE_FLOAT);
        }

        // Loop over the digits to compute the float value.
        int index = 0;

        while (index < digits.length()) {
            floatValue = 10*floatValue +
                         Character.getNumericValue(digits.charAt(index++));
        }

        // Adjust the float value based on the exponent value.
        if (0 != exponentValue) {
            floatValue *= Math.pow(10, exponentValue);
        }

        return (float) floatValue;
    }
}

package de.weltraumschaf.caythe;

/**
 * Helper methods for string manipulation.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class StringUtil {

    static final int MAX_LENGTH_TO_SHORTEN = 60;

    private StringUtil() {
        super();
    }

    /**
     * Makes the first character of a given string upper case.
     *
     * @param input may be {@code null} or empty
     * @return never {@code null}, empty if {@code null} or empty was given as input
     */
    public static String upperCaseFirst(final String input) {
        if (null == input) {
            return "";
        }

        if (input.trim().isEmpty()) {
            return "";
        }

        return Character.toUpperCase(input.charAt(0)) + input.substring(1);
    }

    public static String shorten(final String input) {
        if (null == input) {
            return "";
        }

        final String trimmedInput = input.trim();

        if (trimmedInput.isEmpty()) {
            return "";
        }

        if (trimmedInput.length() < MAX_LENGTH_TO_SHORTEN) {
            return trimmedInput;
        }

        final String begin = trimmedInput.substring(0, 7) + "...";
        final int start = trimmedInput.length() - (MAX_LENGTH_TO_SHORTEN - begin.length());
        return begin + trimmedInput.substring(start);
    }
}

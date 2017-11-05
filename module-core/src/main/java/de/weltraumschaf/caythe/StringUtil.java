package de.weltraumschaf.caythe;

/**
 * Helper methods for string manipulation.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class StringUtil {
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
}

package de.weltraumschaf.caythe.intermediate;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to notify collected intermediate model errors.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Notification {

    /**
     * Collected errors.
     */
    private final List<String> errors = new ArrayList<>();

    /**
     * Collect an line of error.
     *
     * The first parameter is a sprintf style format string.
     *
     * Example:
     * error(format, arg1, arg2 .. argN)
     *
     * @param format Format string.
     * @param args   Variable number of objects referenced in the format string.
     */
    public void error(final String format, final Object... args) {
        errors.add(String.format(format, args));
    }

    /**
     * Returns true if no error was collected.
     *
     * @return Returns true if no errors collected.
     */
    public boolean isOk() {
        return errors.isEmpty();
    }

    /**
     * Returns all errors concatenated as string.
     *
     * @return Returns empty string if {@link #isOk()} returns true, unless it returns all
     *        error messages concatenated.
     */
    public String report() {
        if (isOk()) {
            return "";
        }

        final StringBuilder report = new StringBuilder();

        for (int i = 0; i < errors.size(); ++i) {
            if (i > 0) {
                report.append(String.format("%n"));
            }

            report.append(errors.get(i));
        }

        return report.toString();
    }

}

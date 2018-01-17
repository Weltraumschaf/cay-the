package de.weltraumschaf.caythe.intermediate.equivalence;

import de.weltraumschaf.commons.validate.Validate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
     * <p>
     * The first parameter is a sprintf style format string.
     * <p>
     * Example:
     * error(format, arg1, arg2 .. argN)
     *
     * @param format Format string.
     * @param args   Variable number of objects referenced in the format string.
     */
    public void error(final String format, final Object... args) {
        Validate.notEmpty(format, "format");
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
     * error messages concatenated.
     */
    public String report() {
        if (isOk()) {
            return "";
        }

        return errors.stream().collect(Collectors.joining("\n"));
    }

}

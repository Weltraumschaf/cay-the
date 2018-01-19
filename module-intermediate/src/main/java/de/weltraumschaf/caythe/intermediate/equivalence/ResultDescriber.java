package de.weltraumschaf.caythe.intermediate.equivalence;

import de.weltraumschaf.caythe.intermediate.model.Describable;

import java.util.Collection;
import java.util.Map;

/**
 * Helper to describe failed equivalence results.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @since 1.0.0
 */
public final class ResultDescriber {

    private static final String NOT_EQUAL_STRING_FORMAT = "%s != %s";
    private static final String NOT_EQUAL_NUMBER_FORMAT = "%d != %d";

    String nodeTypeMismatch(final Describable a, final Describable b) {
        return String.format(NOT_EQUAL_STRING_FORMAT, describe(a), describe(b));
    }

    public String valueCountMismatch(final Collection<? extends Describable> a, final Collection<? extends Describable> b) {
        return String.format(NOT_EQUAL_STRING_FORMAT, a.size(), b.size());
    }

    public String valueCountMismatch(final Map<? extends Describable, ? extends Describable> a, final Map<? extends Describable, ? extends Describable> b) {
        return String.format(NOT_EQUAL_NUMBER_FORMAT, a.size(), b.size());
    }

    public String difference(final Describable a, final Describable b) {
        return String.format(NOT_EQUAL_STRING_FORMAT, describe(a), describe(b));
    }

    public String difference(final String a, final String b) {
        return String.format(NOT_EQUAL_STRING_FORMAT, a, b);
    }

    public String difference(final Number a, final Number b) {
        return String.format(NOT_EQUAL_STRING_FORMAT, a, b);
    }

    private String describe(final Describable d) {
        return d.describe().asPlainText();
    }
}

package de.weltraumschaf.caythe.frontend.transform;

import de.weltraumschaf.caythe.intermediate.equivalence.Equivalence;
import de.weltraumschaf.caythe.intermediate.equivalence.Notification;
import de.weltraumschaf.commons.validate.Validate;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher to compare intermediate model objects for equivalence.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class IntermediateModelIsEquivalent<T extends Equivalence> extends TypeSafeMatcher<T> {
    private final Notification result = new Notification();
    private final T expected;

    private IntermediateModelIsEquivalent(final T expected) {
        super();
        this.expected = Validate.notNull(expected, "expected");
    }

    boolean isOk() {
        return result.isOk();
    }

    String report() {
        return result.report();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected boolean matchesSafely(final T actual) {
        expected.probeEquivalence(actual, result);
        return isOk();
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("equivalent intermediate model objects");
    }

    @Override
    protected void describeMismatchSafely(final T item, final Description description) {
        description.appendText(report());
    }

    @Factory
    @SuppressWarnings("unchecked")
    public static <T extends Equivalence> Matcher<T> equivalent(final T expected) {
        Validate.notNull(expected, "expected");
        return new IntermediateModelIsEquivalent(expected);
    }
}

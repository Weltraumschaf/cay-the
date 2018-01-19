package de.weltraumschaf.caythe.frontend.transform;

import de.weltraumschaf.caythe.intermediate.equivalence.Equivalence;
import de.weltraumschaf.caythe.intermediate.model.Position;
import de.weltraumschaf.caythe.intermediate.model.ast.IntegerLiteral;
import de.weltraumschaf.caythe.intermediate.model.ast.RealLiteral;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link IntermediateModelIsEquivalent}.
 */
public class IntermediateModelIsEquivalentTest {
    private final Matcher<IntegerLiteral> sut = IntermediateModelIsEquivalent.equivalent(new IntegerLiteral(42L, Position.UNKNOWN));

    @Test
    public void matchesSafely_same() {
        sut.matches(new IntegerLiteral(42L, Position.UNKNOWN));

        assertThat(((IntermediateModelIsEquivalent)sut).isOk(), is(true));
        assertThat(((IntermediateModelIsEquivalent)sut).report(), is(""));
    }

    @Test
    public void matchesSafely_different() {
        sut.matches(new IntegerLiteral(23L, Position.UNKNOWN));

        assertThat(((IntermediateModelIsEquivalent)sut).isOk(), is(false));
        assertThat(((IntermediateModelIsEquivalent)sut).report(), is("Value differ: 42 != 23"));
    }

    @Test
    public void describeTo() {
        final StringDescription description = new StringDescription();

        sut.describeTo(description);

        assertThat(description.toString(), is("equivalent intermediate model objects"));
    }

    @Test
    public void describeMismatchSafely() {
        final IntegerLiteral item = new IntegerLiteral(23L, Position.UNKNOWN);
        sut.matches(item);
        final StringDescription description = new StringDescription();

        sut.describeMismatch(item, description);

        assertThat(description.toString(), is("Value differ: 42 != 23"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void equivalent() {
        assertThat(
            IntermediateModelIsEquivalent.equivalent(new IntegerLiteral(42L, Position.UNKNOWN)),
            is(not(nullValue())));
    }
}
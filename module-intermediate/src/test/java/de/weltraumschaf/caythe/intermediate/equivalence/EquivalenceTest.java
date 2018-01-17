package de.weltraumschaf.caythe.intermediate.equivalence;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link Equivalence}.
 */
public class EquivalenceTest {

    private final Equivalence<Object> sut = new Equivalence<Object>() {
        @Override
        public void probeEquivalence(final Object other, final Notification result) {
            throw new UnsupportedOperationException("Not implemented in test stub!");
        }
    };

    @Test
    public void difference() {
        assertThat(
            sut.difference("Foo", "bar %s baz s%"),
            is("Foo differ: bar %s baz s%!"));
    }

    @Test
    public void isNotEqual_falseForEqual() {
        assertThat(sut.isNotEqual("foo", "foo"), is(false));
    }

    @Test
    public void isNotEqual_trueForNotEqual() {
        assertThat(sut.isNotEqual("foo", "bar"), is(true));
    }

    @Test
    public void isNotSameSize_falseForSame() {
        assertThat(
            sut.isNotSameSize(Arrays.asList("foo", "bar"), Arrays.asList("foo", "bar")),
            is(false)
        );
    }

    @Test
    public void isNotSameSize_trueForNotSame() {
        assertThat(
            sut.isNotSameSize(Collections.singletonList("foo"), Arrays.asList("foo", "bar")),
            is(true)
        );
    }

    @Test
    public void probeEquivalenceFor_notCallingCallbackIfTypeDoesNotMatch() {
        final Bar bar = new Bar("bar");
        final Notification result = new Notification();

        bar.probeEquivalenceFor(Bar.class, new Baz(), result, other -> {
            fail("Callback must not be executed!");
        });

        assertThat(result.isOk(), is(false));
        assertThat(result.report(), is("Probed node types mismatch: 'class de.weltraumschaf.caythe.intermediate.equivalence.EquivalenceTest$Bar' != 'class de.weltraumschaf.caythe.intermediate.equivalence.EquivalenceTest$Baz'!"));
    }

    @Test
    public void probeEquivalenceFor_callingCallbackIfTypeDoesMatch() {
        final Bar bar = new Bar("bar");
        final Notification result = new Notification();

        bar.probeEquivalenceFor(Bar.class, new Bar("bar"), result, other -> {
            assertThat(bar.bar, is(other.bar));
        });

        assertThat(result.isOk(), is(true));
        assertThat(result.report(), is(""));
    }

    private interface Foo extends Equivalence<Foo> {
    }

    private static class Bar implements Foo {
        private final String bar;

        private Bar(final String bar) {
            super();
            this.bar = bar;
        }

        @Override
        public void probeEquivalence(final Foo other, final Notification result) {
            throw new UnsupportedOperationException("Not implemented in test stub!");
        }
    }

    private static class Baz implements Foo {
        @Override
        public void probeEquivalence(final Foo other, final Notification result) {
            throw new UnsupportedOperationException("Not implemented in test stub!");
        }
    }
}
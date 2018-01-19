package de.weltraumschaf.caythe.intermediate.equivalence;

import de.weltraumschaf.caythe.intermediate.model.Describable;
import de.weltraumschaf.caythe.intermediate.model.IntermediateModel;
import de.weltraumschaf.caythe.intermediate.model.ModelDescription;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link Equivalence}.
 */
public class EquivalenceTest {

    private final Equivalence<Describable> sut = new Equivalence<Describable>() {
        @Override
        public ModelDescription describe() {
            return null;
        }

        @Override
        public void probeEquivalence(final Describable other, final Notification result) {
            throw new UnsupportedOperationException("Not implemented in test stub!");
        }
    };

    @Test
    public void difference() {
        assertThat(
            sut.difference("Foo", "bar %s baz s%"),
            is("Foo differ: bar %s baz s%"));
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
        assertThat(result.report(), is("Probed node types differ: (bar) != (baz)"));
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

    private interface Foo extends Equivalence<Foo>, IntermediateModel {
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

        @Override
        public ModelDescription describe() {
            return new ModelDescription(this);
        }
    }

    private static class Baz implements Foo {
        @Override
        public void probeEquivalence(final Foo other, final Notification result) {
            throw new UnsupportedOperationException("Not implemented in test stub!");
        }

        @Override
        public ModelDescription describe() {
            return new ModelDescription(this);
        }
    }
}
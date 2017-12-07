package de.weltraumschaf.caythe.experiments.intrep;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link Tuple}
 */
public class TupleTest {

    private final Tuple sut = new Tuple();

    @Test
    public void emptyByDefault() {
        assertThat(sut.isEmpty(), is(true));
    }

    @Test
    public void sizeZeroByDefault() {
        assertThat(sut.size(), is(0));
    }

    @Test(expected = NullPointerException.class)
    public void putNullThrowsException() {
        sut.put(null);
    }

    @Test
    public void putOneValue() {
        sut.put("aString");

        assertThat(sut.size(), is(1));
        assertThat(sut.isEmpty(), is(false));
        assertThat(sut.get(0), is("aString"));
    }

    @Test
    public void putThreeValues() {
        sut.put("aString");
        sut.put(42L);
        sut.put(new Some(23));

        assertThat(sut.size(), is(3));
        assertThat(sut.isEmpty(), is(false));
    }

    @Test
    public void getSomeValues() {
        sut.put("aString");
        sut.put(42L);
        sut.put(new Some(23));

        assertThat(sut.hasType(0, String.class), is(true));
        final String valueOne = sut.get(0);
        assertThat(valueOne, is("aString"));

        assertThat(sut.hasType(1, Number.class), is(true));
        assertThat(sut.hasType(1, Long.class), is(true));
        final Long valueTwo = sut.get(1);
        assertThat(valueTwo, is(42L));

        assertThat(sut.hasType(2, Some.class), is(true));
        final Some valueThree = sut.get(2);
        assertThat(valueThree, is(new Some(23)));
    }

    private static class Some {
        private final int value;

        private Some(final int value) {
            super();
            this.value = value;
        }

        @Override
        public boolean equals(final Object o) {
            if (!(o instanceof Some)) {
                return false;
            }

            final Some some = (Some) o;
            return value == some.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }
}
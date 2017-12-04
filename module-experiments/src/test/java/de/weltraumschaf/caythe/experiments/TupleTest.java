package de.weltraumschaf.caythe.experiments;

import org.junit.Test;

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

    @Test
    public void putOneValue() {
        sut.put("aString");

        assertThat(sut.size(), is(1));
        assertThat(sut.isEmpty(), is(false));
    }

    @Test
    public void putThreeValues() {
        sut.put("aString");
        sut.put(42L);
        sut.put(new Some());

        assertThat(sut.size(), is(3));
        assertThat(sut.isEmpty(), is(false));
    }

    private static class Some {

    }
}

package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.backend.SymbolTable.Entry;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for {@link SymbolTable}.
 */
public class SymbolTableTest {

    private final SymbolTable sut = new SymbolTable();

    @Test(expected = IllegalArgumentException.class)
    public void lookup_notAvailable() {
        assertThat(sut.lookup("foo"), is(nullValue()));
    }

    @Test
    public void entered_notAvailable() {
        assertThat(sut.enered("foo"), is(false));
    }

    @Test
    public void enter_oneSymbol() {
        final Entry symbol = sut.enter("foo");

        assertThat(symbol, is(new Entry(0, "foo")));
        assertThat(sut.enered("foo"), is(true));
        assertThat(sut.lookup("foo"), is(symbol));
    }

    @Test
    public void enter_threeSymbol() {
        final Entry symbolOne = sut.enter("foo");
        assertThat(symbolOne, is(new Entry(0, "foo")));
        final Entry symbolTwo = sut.enter("bar");
        assertThat(symbolTwo, is(new Entry(1, "bar")));
        final Entry symbolThree = sut.enter("baz");
        assertThat(symbolThree, is(new Entry(2, "baz")));

        assertThat(sut.enered("foo"), is(true));
        assertThat(sut.lookup("foo"), is(symbolOne));
        assertThat(sut.enered("bar"), is(true));
        assertThat(sut.lookup("bar"), is(symbolTwo));
        assertThat(sut.enered("baz"), is(true));
        assertThat(sut.lookup("baz"), is(symbolThree));
    }
}
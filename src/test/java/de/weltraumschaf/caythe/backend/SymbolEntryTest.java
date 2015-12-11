package de.weltraumschaf.caythe.backend;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for {@link SymbolTable}.
 */
public class SymbolEntryTest {

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
        final SymbolEntry symbol = sut.enter("foo", SymbolEntry.Type.CONSTANT);

        assertThat(symbol, is(new SymbolEntry(0, "foo", SymbolEntry.Type.CONSTANT)));
        assertThat(sut.enered("foo"), is(true));
        assertThat(sut.lookup("foo"), is(symbol));
    }

    @Test
    public void enter_threeSymbol() {
        final SymbolEntry symbolOne = sut.enter("foo", SymbolEntry.Type.CONSTANT);
        assertThat(symbolOne, is(new SymbolEntry(0, "foo", SymbolEntry.Type.CONSTANT)));
        final SymbolEntry symbolTwo = sut.enter("bar", SymbolEntry.Type.CONSTANT);
        assertThat(symbolTwo, is(new SymbolEntry(1, "bar", SymbolEntry.Type.CONSTANT)));
        final SymbolEntry symbolThree = sut.enter("baz", SymbolEntry.Type.CONSTANT);
        assertThat(symbolThree, is(new SymbolEntry(2, "baz", SymbolEntry.Type.CONSTANT)));

        assertThat(sut.enered("foo"), is(true));
        assertThat(sut.lookup("foo"), is(symbolOne));
        assertThat(sut.enered("bar"), is(true));
        assertThat(sut.lookup("bar"), is(symbolTwo));
        assertThat(sut.enered("baz"), is(true));
        assertThat(sut.lookup("baz"), is(symbolThree));
    }
}

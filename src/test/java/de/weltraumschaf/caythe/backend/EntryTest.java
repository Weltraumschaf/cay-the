package de.weltraumschaf.caythe.backend;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Tests for {@link SymbolTable.SymbolEntry}.
 */
public final class EntryTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(SymbolEntry.class).verify();
    }

    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void idMustNotBeNegative() {
        new SymbolEntry(-1, "foo", SymbolEntry.Type.CONSTANT);
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void nameMustNotBeNull() {
        new SymbolEntry(0, null, SymbolEntry.Type.CONSTANT);
    }

    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void nameMustNotBeEmpty() {
        new SymbolEntry(0, "", SymbolEntry.Type.CONSTANT);
    }
}

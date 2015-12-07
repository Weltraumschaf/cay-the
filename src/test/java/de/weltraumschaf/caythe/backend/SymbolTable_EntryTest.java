package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.backend.SymbolTable.Entry;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Tests for {@link SymbolTable.Entry}.
 */
public final class SymbolTable_EntryTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Entry.class).verify();
    }

    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void idMustNotBeNegative() {
        new Entry(-1, "foo");
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void nameMustNotBeNull() {
        new Entry(0, null);
    }

    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void nameMustNotBeEmpty() {
        new Entry(0, "");
    }
}

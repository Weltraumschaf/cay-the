
package de.weltraumschaf.caythe.backend.symtab;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Tests for {@link VariableSymbol}.
 */
public class VariableSymbolTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(VariableSymbol.class).verify();
    }

}
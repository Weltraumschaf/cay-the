
package de.weltraumschaf.caythe.backend.symtab;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Tests for {@link ConstantSymbol}.
 */
public class ConstantSymbolTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(ConstantSymbol.class).verify();
    }

}

package de.weltraumschaf.caythe.backend.symtab;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Tests for {@link LocalScope}.
 */
public class LocalScopeTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(LocalScope.class).verify();
    }

}

package de.weltraumschaf.caythe.backend.symtab;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Tests for {@lin GlobalScope}.
 */
public class GlobalScopeTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(GlobalScope.class).verify();
    }

}
package de.weltraumschaf.caythe.intermediate.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Tests for {@link Module}.
 */
public class ModuleTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Module.class).verify();
    }
}

package de.weltraumschaf.caythe.intermediate.ast;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Tests for {@link NilLiteral}.
 */
public class NilLiteralTest {
    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(NilLiteral.class).verify();
    }
}
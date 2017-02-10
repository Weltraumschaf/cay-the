package de.weltraumschaf.caythe.intermediate.ast;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Tests for {@link RealLiteral}.
 */
public class RealLiteralTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(RealLiteral.class).verify();
    }
}
package de.weltraumschaf.caythe.intermediate.ast;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link BooleanLiteral}.
 */
public class BooleanLiteralTest {
    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(BooleanLiteral.class).verify();
    }
}
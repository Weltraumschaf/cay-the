package de.weltraumschaf.caythe.intermediate.ast;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link NullLiteral}.
 */
public class NullLiteralTest {
    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(NullLiteral.class).verify();
    }
}
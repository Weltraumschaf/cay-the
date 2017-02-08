package de.weltraumschaf.caythe.intermediate.ast;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link FloatLiteral}.
 */
public class FloatLiteralTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(FloatLiteral.class).verify();
    }
}
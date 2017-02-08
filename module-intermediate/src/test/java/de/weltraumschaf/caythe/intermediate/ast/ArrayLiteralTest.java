package de.weltraumschaf.caythe.intermediate.ast;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link ArrayLiteral}.
 */
public class ArrayLiteralTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(ArrayLiteral.class).verify();
    }
}
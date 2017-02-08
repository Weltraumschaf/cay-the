package de.weltraumschaf.caythe.intermediate.ast;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link IntegerLiteral}.
 */
public class IntegerLiteralTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(IntegerLiteral.class).verify();
    }
}
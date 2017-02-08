package de.weltraumschaf.caythe.intermediate.ast;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link BinaryOperation}.
 */
public class BinaryOperationTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(BinaryOperation.class).verify();
    }
}
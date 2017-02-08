package de.weltraumschaf.caythe.intermediate.ast;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link UnaryOperation}.
 */
public class UnaryOperationTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(UnaryOperation.class).verify();
    }
}
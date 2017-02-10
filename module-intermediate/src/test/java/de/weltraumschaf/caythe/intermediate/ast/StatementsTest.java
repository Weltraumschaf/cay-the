package de.weltraumschaf.caythe.intermediate.ast;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link Statements}.
 */
public class StatementsTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Statements.class).verify();
    }
}
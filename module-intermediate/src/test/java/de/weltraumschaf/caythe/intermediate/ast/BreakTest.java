package de.weltraumschaf.caythe.intermediate.ast;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link Break}.
 */
public class BreakTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Break.class).verify();
    }
}
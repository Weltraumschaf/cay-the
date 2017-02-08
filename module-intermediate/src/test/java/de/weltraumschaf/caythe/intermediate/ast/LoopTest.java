package de.weltraumschaf.caythe.intermediate.ast;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link Loop}.
 */
public class LoopTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Loop.class).verify();
    }
}
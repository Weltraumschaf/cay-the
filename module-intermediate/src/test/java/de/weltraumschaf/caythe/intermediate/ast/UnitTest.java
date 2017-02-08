package de.weltraumschaf.caythe.intermediate.ast;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link Unit}.
 */
public class UnitTest {
    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Unit.class).verify();
    }
}
package de.weltraumschaf.caythe.intermediate.ast;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link Identifier}.
 */
public class IdentifierTest {
    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Identifier.class).verify();
    }
}
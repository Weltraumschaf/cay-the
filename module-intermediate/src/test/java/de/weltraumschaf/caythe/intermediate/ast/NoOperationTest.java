package de.weltraumschaf.caythe.intermediate.ast;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link NoOperation}.
 */
public class NoOperationTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(NoOperation.class).verify();
    }
}
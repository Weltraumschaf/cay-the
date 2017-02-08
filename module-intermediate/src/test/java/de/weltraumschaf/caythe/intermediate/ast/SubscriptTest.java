package de.weltraumschaf.caythe.intermediate.ast;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link Subscript}.
 */
public class SubscriptTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Subscript.class).verify();
    }
}
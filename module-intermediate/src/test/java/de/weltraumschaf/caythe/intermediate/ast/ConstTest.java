package de.weltraumschaf.caythe.intermediate.ast;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link Const}.
 */
public class ConstTest {
    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Const.class).verify();
    }
}
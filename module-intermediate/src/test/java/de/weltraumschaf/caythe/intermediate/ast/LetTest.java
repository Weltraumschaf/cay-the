package de.weltraumschaf.caythe.intermediate.ast;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link Let}.
 */
public class LetTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Let.class).verify();
    }
}
package de.weltraumschaf.caythe.intermediate.ast;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link HashLiteral}.
 */
public class HashLiteralTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(HashLiteral.class).verify();
    }
}
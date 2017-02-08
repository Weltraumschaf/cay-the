package de.weltraumschaf.caythe.intermediate.ast;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link FunctionLiteral}.
 */
public class FunctionLiteralTest {
    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(FunctionLiteral.class).verify();
    }
}
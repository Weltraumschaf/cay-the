package de.weltraumschaf.caythe.intermediate.ast;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link IfExpression}.
 */
public class IfExpressionTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(IfExpression.class).verify();
    }
}
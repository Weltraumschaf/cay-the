package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.Position;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static de.weltraumschaf.caythe.intermediate.model.ast.builder.BinaryOperationFactory.*;
import static de.weltraumschaf.caythe.intermediate.model.ast.builder.LiteralFactory.identifier;
import static de.weltraumschaf.caythe.intermediate.model.ast.builder.LiteralFactory.integer;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Tests for {@link IfExpression}.
 */
public class IfExpressionTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(IfExpression.class).verify();
    }

    @Test
    public void serialize() {
        final IfExpression sut = new IfExpression(
            greaterThan(identifier("a", 1, 2), identifier("b", 3, 4), 5, 6),
            addition(integer(2L, 7, 8), integer(3L, 9, 10), 11, 12),
            subtraction(integer(2L, 7, 8), integer(3L, 9, 10), 11, 12),
            new Position(1, 2)
        );

        assertThat(
            sut.serialize(),
            is("(if " +
                "(> (identifier a [1:2]) (identifier b [3:4]) [5:6]) " +
                "(+ (integer 2 [7:8]) (integer 3 [9:10]) [11:12]) " +
                "(- (integer 2 [7:8]) (integer 3 [9:10]) [11:12]) [1:2])"));
    }
}
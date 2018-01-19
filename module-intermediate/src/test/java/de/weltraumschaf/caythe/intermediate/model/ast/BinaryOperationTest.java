package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.equivalence.Notification;
import de.weltraumschaf.caythe.intermediate.model.Position;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static de.weltraumschaf.caythe.intermediate.model.ast.builder.LiteralFactory.integer;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Tests for {@link BinaryOperation}.
 */
public class BinaryOperationTest {
    private final BinaryOperation sut = new BinaryOperation(
        BinaryOperation.Operator.ADDITION,
        integer(23L, 1, 2),
        integer(42L, 3, 4),
        new Position(1, 0));

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(BinaryOperation.class).verify();
    }

    @Test
    public void serialize() {
        assertThat(sut.serialize(), is("(+ (integer 23 [1:2]) (integer 42 [3:4]) [1:0])"));
    }

    @Test
    public void probeEquivalence() {
        final Notification result = new Notification();

        sut.probeEquivalence(sut, result);

        assertThat(result.isOk(), is(true));
        assertThat(result.report(), is(""));
    }

    @Test
    public void probeEquivalence_otherHasDifferentOperator() {
        final BinaryOperation other = new BinaryOperation(
            BinaryOperation.Operator.SUBTRACTION,
            integer(23L, Position.UNKNOWN),
            integer(42L, Position.UNKNOWN),
            Position.UNKNOWN);
        final Notification result = new Notification();

        sut.probeEquivalence(other, result);

        assertThat(result.isOk(), is(false));
        assertThat(result.report(), is("Operator differ: (+) != (-)"));
    }

    @Test
    public void probeEquivalence_otherHasDifferentOperatorAndLeftOperand() {
        final BinaryOperation other = new BinaryOperation(
            BinaryOperation.Operator.SUBTRACTION,
            integer(32L, Position.UNKNOWN),
            integer(42L, Position.UNKNOWN),
            Position.UNKNOWN);
        final Notification result = new Notification();

        sut.probeEquivalence(other, result);

        assertThat(result.isOk(), is(false));
        assertThat(
            result.report(),
            is("Operator differ: (+) != (-)\n" +
                "Value differ: 23 != 32"));
    }

    @Test
    public void probeEquivalence_otherHasDifferentOperatorAndRightOperand() {
        final BinaryOperation other = new BinaryOperation(
            BinaryOperation.Operator.SUBTRACTION,
            integer(23L, Position.UNKNOWN),
            integer(24L, Position.UNKNOWN),
            Position.UNKNOWN);
        final Notification result = new Notification();

        sut.probeEquivalence(other, result);

        assertThat(result.isOk(), is(false));
        assertThat(
            result.report(),
            is("Operator differ: (+) != (-)\n" +
                "Value differ: 42 != 24"));
    }

    @Test
    public void probeEquivalence_otherHasDifferentOperatorAndBothOperands() {
        final BinaryOperation other = new BinaryOperation(
            BinaryOperation.Operator.SUBTRACTION,
            integer(32L, Position.UNKNOWN),
            integer(24L, Position.UNKNOWN),
            Position.UNKNOWN);
        final Notification result = new Notification();

        sut.probeEquivalence(other, result);

        assertThat(result.isOk(), is(false));
        assertThat(
            result.report(),
            is("Operator differ: (+) != (-)\n" +
                "Value differ: 23 != 32\n" +
                "Value differ: 42 != 24"));
    }
}
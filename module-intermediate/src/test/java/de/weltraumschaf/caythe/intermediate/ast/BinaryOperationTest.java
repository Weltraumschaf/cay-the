package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.Position;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static de.weltraumschaf.caythe.intermediate.ast.builder.LiteralFactory.integer;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Tests for {@link BinaryOperation}.
 */
public class BinaryOperationTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(BinaryOperation.class).verify();
    }

    @Test
    public void serialize() {
        final BinaryOperation sut = new BinaryOperation(
            BinaryOperation.Operator.ADDITION,
            integer(23L, 1,2),
            integer(42L, 3,4 ),
            new Position(1, 0));

        assertThat(sut.serialize(), is("(+ (integer 23 [1:2]) (integer 42 [3:4]) [1:0])"));
    }
}
package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.Position;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import java.util.Collections;

import static de.weltraumschaf.caythe.intermediate.ast.builder.BinaryOperationBuilder.addition;
import static de.weltraumschaf.caythe.intermediate.ast.builder.LiteralBuilder.integer;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Tests for {@link Unit}.
 */
public class UnitTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Unit.class).verify();
    }

    @Test
    public void serialize_empty() {
        final Unit sut = new Unit(
            Collections.emptyList(),
            new Position(1, 2)
        );

        assertThat(sut.serialize(), is("(unit [1:2])"));
    }

    @Test
    public void serialize_oneStatement() {
        final Unit sut = new Unit(
            Collections.singletonList(new Statement(
                addition(
                    integer(2L , 7 ,8),
                    integer(3L, 9, 10),
                    11, 12),
                new Position(1, 2)
            )),
            new Position(1, 2)
        );

        assertThat(
            sut.serialize(),
            is("(unit (statement (+ (integer 2 [7:8]) (integer 3 [9:10]) [11:12]) [1:2]) [1:2])"));
    }
}
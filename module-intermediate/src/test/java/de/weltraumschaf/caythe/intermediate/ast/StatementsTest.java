package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.Position;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import java.util.Collections;
import java.util.Stack;

import static de.weltraumschaf.caythe.intermediate.ast.builder.BinaryOperationBuilder.addition;
import static de.weltraumschaf.caythe.intermediate.ast.builder.LiteralBuilder.integer;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Tests for {@link Statements}.
 */
public class StatementsTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Statements.class).verify();
    }

    @Test
    public void serialize_endlessLoop() {
        final Statements sut = new Statements(
            Collections.singletonList(
                addition(
                    integer(2L , 7 ,8),
                    integer(3L, 9, 10),
                    11, 12)),
            new Position(1, 2)
        );

        assertThat(sut.serialize(), is("(statements (+ (integer 2 [7:8]) (integer 3 [9:10]) [11:12]) [1:2])"));
    }

}
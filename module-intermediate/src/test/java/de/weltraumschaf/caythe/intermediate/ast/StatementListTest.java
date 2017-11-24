package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.Position;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static de.weltraumschaf.caythe.intermediate.ast.builder.BinaryOperationBuilder.addition;
import static de.weltraumschaf.caythe.intermediate.ast.builder.LiteralBuilder.integer;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link StatementList}.
 */
public final class StatementListTest {
    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(StatementList.class).verify();
    }

    @Test
    public void serialize_empty() {
        final StatementList sut = new StatementList(
            Collections.emptyList(),
            new Position(1, 2));

        assertThat(sut.serialize(), is("(statement-list [1:2])"));
    }

    @Test
    public void serialize_oneStatement() {
        final StatementList sut = new StatementList(
            Collections.singleton(new Statement(
                addition(
                    integer(2L , 7 ,8),
                    integer(3L, 9, 10),
                    11, 12),
                new Position(1, 2)
            )),
            new Position(1, 2));

        assertThat(
            sut.serialize(),
            is("(statement-list (statement (+ (integer 2 [7:8]) (integer 3 [9:10]) [11:12]) [1:2]) [1:2])"));
    }

    @Test
    public void serialize_threeStatement() {
        final StatementList sut = new StatementList(
            Arrays.asList(new Statement(
                addition(
                    integer(2L, 7, 8),
                    integer(3L, 9, 10),
                    11, 12),
                new Position(1, 2)
            ), new Statement(
                addition(
                    integer(2L, 7, 8),
                    integer(3L, 9, 10),
                    11, 12),
                new Position(1, 2)
            ), new Statement(
                addition(
                    integer(2L, 7, 8),
                    integer(3L, 9, 10),
                    11, 12),
                new Position(1, 2)
            )),
            new Position(1, 2));

        assertThat(
            sut.serialize(),
            is("(statement-list " +
                "(statement (+ (integer 2 [7:8]) (integer 3 [9:10]) [11:12]) [1:2]) " +
                "(statement (+ (integer 2 [7:8]) (integer 3 [9:10]) [11:12]) [1:2]) " +
                "(statement (+ (integer 2 [7:8]) (integer 3 [9:10]) [11:12]) [1:2]) " +
                "[1:2])"));
    }
}

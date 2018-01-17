package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.model.Position;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static de.weltraumschaf.caythe.intermediate.model.ast.builder.BinaryOperationFactory.addition;
import static de.weltraumschaf.caythe.intermediate.model.ast.builder.LiteralFactory.bool;
import static de.weltraumschaf.caythe.intermediate.model.ast.builder.LiteralFactory.integer;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link Loop}.
 */
public class LoopTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Loop.class).verify();
    }

    @Test
    public void serialize_endlessLoop() {
        final Loop sut = new Loop(
            bool(true, 1, 2),
            addition(
                integer(2L, 7, 8),
                integer(3L, 9, 10),
                11, 12),
            new Position(1, 2));

        assertThat(
            sut.serialize(),
            is("(loop (noop) (boolean true [1:2]) (noop) (+ (integer 2 [7:8]) (integer 3 [9:10]) [11:12]) [1:2])"));
    }

}
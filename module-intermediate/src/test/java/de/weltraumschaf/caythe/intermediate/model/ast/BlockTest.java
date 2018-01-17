package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.caythe.intermediate.model.ast.builder.BlockBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import java.util.Collections;

import static de.weltraumschaf.caythe.intermediate.model.ast.builder.LiteralFactory.integer;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link Block}.
 */
public final class BlockTest {
    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Block.class).verify();
    }

    @Test
    public void serialize_empty() {
        final Block sut = new Block(
            Collections.emptyList(),
            new Position(1, 2));

        assertThat(sut.serialize(), is("(block [1:2])"));
    }

    @Test
    public void serialize_oneStatement() {
        final Block sut = BlockBuilder
            .block(1, 2)
                .statements()
                    .binops()
                        .addition(
                            integer(2L , 7 ,8),
                            integer(3L, 9, 10),
                            11, 12)
                    .end()
                .end()
            .end();

        assertThat(
            sut.serialize(),
            is("(block (+ (integer 2 [7:8]) (integer 3 [9:10]) [11:12]) [1:2])"));
    }

    @Test
    public void serialize_threeStatement() {
        final Block sut = BlockBuilder
            .block(1, 2)
                .statements()
                    .binops()
                        .addition(
                            integer(2L, 7, 8),
                            integer(3L, 9, 10),
                            11, 12)
                        .addition(
                            integer(2L, 7, 8),
                            integer(3L, 9, 10),
                            11, 12)
                        .addition(
                            integer(2L, 7, 8),
                            integer(3L, 9, 10),
                            11, 12)
                        .end()
                    .end()
                .end();

        assertThat(
            sut.serialize(),
            is("(block " +
                "(+ (integer 2 [7:8]) (integer 3 [9:10]) [11:12]) " +
                "(+ (integer 2 [7:8]) (integer 3 [9:10]) [11:12]) " +
                "(+ (integer 2 [7:8]) (integer 3 [9:10]) [11:12]) " +
                "[1:2])"));
    }
}

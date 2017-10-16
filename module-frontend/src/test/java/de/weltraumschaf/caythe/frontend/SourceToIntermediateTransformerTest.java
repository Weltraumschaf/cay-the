package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.caythe.intermediate.ast.*;
import de.weltraumschaf.caythe.intermediate.ast.builder.UnitBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static de.weltraumschaf.caythe.intermediate.ast.builder.BinaryOperationBuilder.addition;
import static de.weltraumschaf.caythe.intermediate.ast.builder.BinaryOperationBuilder.multiplication;
import static de.weltraumschaf.caythe.intermediate.ast.builder.BinaryOperationBuilder.subtraction;
import static de.weltraumschaf.caythe.intermediate.ast.builder.LiteralBuilder.integer;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link SourceToIntermediateTransformer}.
 */
public class SourceToIntermediateTransformerTest extends VisitorTestCase {
    @SuppressWarnings("unchecked")
    private final CayTheSourceVisitor<AstNode> sut = injector().getInstance(CayTheSourceVisitor.class);

    @Test
    public void testFile() throws IOException {
        final InputStream src = getClass().getResourceAsStream("/de/weltraumschaf/caythe/frontend/test.ct");

        final AstNode ast = sut.visit(parseSource(src));

        assertThat(ast, is(not(nullValue())));
    }

    @Test
    public void simpleBinaryOperation() throws IOException {
        final String src = "2 + 3\n";
        final Unit expected = UnitBuilder
            .unit(1, 0)
            .statement(
                addition(
                    integer(2L, 1, 0),
                    integer(3L, 1, 4),
                    1, 0
                ),
                1, 0
            )
            .end();

        final AstNode ast = sut.visit(parseSource(src));

        assertThat( ast, is(expected));
    }

    @Test
    public void mathOperationWithMultipleOperands() throws IOException {
        final String src = "1 + 2 * 3 - 4\n";
        final Unit expected = UnitBuilder
            .unit(1, 0)
            .statement(
                subtraction(
                    addition(
                        integer(1L, 1, 0),
                        multiplication(
                            integer(2L, 1, 4),
                            integer(3L, 1, 8),
                            1, 4
                        ),
                        1, 0
                    ),
                    integer(4L, 1, 12),
                    1, 0
                ),
                1, 0
            )
            .end();

        final AstNode ast = sut.visit(parseSource(src));

        assertThat( ast, is(expected));
    }


    @Test
    public void mathOperationWithMultipleOperandsAndPArens() throws IOException {
        final String src = "(1 + 2) * (3 - 4)\n";
        final Unit expected = UnitBuilder
            .unit(1, 0)
            .statement(
                multiplication(
                    addition(
                        integer(1L,1, 1),
                        integer(2L, 1, 5),
                        1, 1
                    ),
                    subtraction(
                        integer(3L, 1, 11),
                        integer(4L, 1, 15),
                        1, 11
                    ),
                    1, 0
                ),
                1, 0
            )
            .end();

        final AstNode ast = sut.visit(parseSource(src));

        assertThat( ast, is(expected));
    }

}

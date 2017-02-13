package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.caythe.intermediate.ast.*;
import de.weltraumschaf.caythe.intermediate.ast.builder.UnitBuilder;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static de.weltraumschaf.caythe.intermediate.ast.builder.BinaryOperationBuilder.addition;
import static de.weltraumschaf.caythe.intermediate.ast.builder.BinaryOperationBuilder.multiplication;
import static de.weltraumschaf.caythe.intermediate.ast.builder.BinaryOperationBuilder.subtraction;
import static de.weltraumschaf.caythe.intermediate.ast.builder.LiteralBuilder.integer;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link TransformToIntermediateVisitor}.
 */
public class TransformToIntermediateVisitorTest extends VisitorTestCase {
    @SuppressWarnings("unchecked")
    private final CayTheSourceVisitor<AstNode> sut = injector().getInstance(CayTheSourceVisitor.class);


    private ParseTree parse(final String src) throws IOException {
        return parse(new ByteArrayInputStream(src.getBytes()));
    }

    private ParseTree parse(final InputStream src) throws IOException {
        return sourceParser(src).unit();
    }

    @Test
    public void foo() throws IOException {
        final InputStream src = getClass().getResourceAsStream("/de/weltraumschaf/caythe/frontend/test.ct");

        final AstNode ast = sut.visit(parse(src));

        assertThat(ast, is(not(nullValue())));
    }

    @Test
    public void simpleBinaryOperation() throws IOException {
        final String src = "2 + 3\n";
        final Unit expected = UnitBuilder
            .unit(1, 0)
            .statement(1, 0,
                addition(1, 0,
                    integer(1, 0, 2L),
                    integer(1, 4, 3L)
                )
            )
            .end();

        final AstNode ast = sut.visit(parse(src));

        assertThat( ast, is(expected));
    }

    @Test
    public void mathOperationWithMultipleOperands() throws IOException {
        final String src = "1 + 2 * 3 - 4\n";
        final Unit expected = UnitBuilder
            .unit(1, 0)
            .statement(1, 0,
                subtraction(1, 0,
                    addition(1, 0,
                        integer(1, 0, 1),
                        multiplication(1, 4,
                            integer(1, 4, 2),
                            integer(1, 8, 3)
                        )
                    ),
                    integer(1, 12, 4)
                )
            )
            .end();

        final AstNode ast = sut.visit(parse(src));

        assertThat( ast, is(expected));
    }


    @Test
    @Ignore
    public void mathOperationWithMultipleOperandsAndPArens() throws IOException {
        final String src = "(1 + 2) * (3 - 4)\n";
        final Unit expected = UnitBuilder
            .unit(1, 0)
            .statement(1, 0,
                multiplication(1, 0,
                    addition(1, 0,
                        integer(1, 1, 1),
                        integer(1, 4, 2)
                    ),
                    subtraction(1, 0,
                        integer(1, 8, 3),
                        integer(1, 12, 4)
                    )
                )
            )
            .end();

        final AstNode ast = sut.visit(parse(src));

        assertThat( ast, is(expected));
    }

}

package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.caythe.intermediate.ast.*;
import de.weltraumschaf.caythe.intermediate.ast.builder.UnitBuilder;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static de.weltraumschaf.caythe.intermediate.ast.builder.BinaryOperationBuilder.addition;
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
//        final String src = "a = 2\n" +
//            "\n" +
//            "b = 1 + 1\n" +
//            "c = 1 ^ 2 ^ 3\n";

        final InputStream src = getClass().getResourceAsStream("/de/weltraumschaf/caythe/frontend/test.ct");
        final ParseTree parseTree = parse(src);

        final AstNode ast = sut.visit(parseTree);

        assertThat(ast, is(not(nullValue())));
    }

    @Test
    public void simpleBinaryOperation() throws IOException {
        final String src = "2 + 3\n";
        final ParseTree parseTree = parse(src);

        final AstNode ast = sut.visit(parseTree);

        final Unit expected = UnitBuilder
            .unit(1, 0)
            .statement(1, 0,
                addition(1, 0,
                    integer(1, 0, 2L),
                    integer(1, 4, 3L)
                )
            )
            .end();

        assertThat( ast, is(expected));
    }

}

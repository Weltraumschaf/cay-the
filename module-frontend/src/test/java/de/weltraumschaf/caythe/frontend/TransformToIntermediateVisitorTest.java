package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.caythe.intermediate.ast.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

        assertThat(
            ast,
            is(new Unit(
                list(new Statements(
                    list(new BinaryOperation(
                        BinaryOperation.Operator.ADD,
                        new IntegerLiteral(2L, new Position(1,0)),
                        new IntegerLiteral(3L, new Position(1,4)),
                        new Position(1,0))),
                    new Position(1,0))),
                new Position(1,0))
        ));
    }

    private List<AstNode> list (final AstNode... nodes) {
        final List<AstNode> list = new ArrayList<>();
        Collections.addAll(list, nodes);
        return list;
    }
}

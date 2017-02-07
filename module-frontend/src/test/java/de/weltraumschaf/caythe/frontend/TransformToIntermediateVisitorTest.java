package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.caythe.intermediate.ast.AstNode;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link TransformToIntermediateVisitor}.
 */
public class TransformToIntermediateVisitorTest extends VisitorTestCase {
    @SuppressWarnings("unchecked")
    private final CayTheSourceVisitor<AstNode> sut = injector().getInstance(CayTheSourceVisitor.class);

    @Test
    public void foo() throws IOException {
//        final String src = "a = 2\n" +
//            "\n" +
//            "b = 1 + 1\n" +
//            "c = 1 ^ 2 ^ 3\n";

        final InputStream src = getClass().getResourceAsStream("/de/weltraumschaf/caythe/frontend/test.ct");
        final CayTheSourceParser.UnitContext parseTree = sourceParser(src).unit();

        final AstNode ast = sut.visit(parseTree);

        assertThat(ast, is(not(nullValue())));
    }
}

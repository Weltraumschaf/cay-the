package de.weltraumschaf.caythe.frontend.transform;

import de.weltraumschaf.caythe.intermediate.model.ast.Block;
import de.weltraumschaf.caythe.intermediate.model.ast.builder.BlockBuilder;
import de.weltraumschaf.caythe.intermediate.model.*;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static de.weltraumschaf.caythe.intermediate.model.ast.builder.LiteralFactory.integer;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link SourceToIntermediateTransformer}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public class SourceToIntermediateTransformerTest extends TransformVisitorTestCase {

    @Test
    public void defaultResult() {
        final SourceToIntermediateTransformer sut = createSut("/foo/bar/baz/Snafu.ct");

        final Type result = sut.defaultResult();

        assertThat(result, is(not(nullValue())));
        assertThat(result.getName(), is(new TypeName("foo.bar.baz", "Snafu")));
        assertThat(result.getFacet(), is(Facet.UNDEFINED));
        assertThat(result.getVisibility(), is(Visibility.UNDEFINED));
        assertThat(result.getConstructor(), is(Method.NONE));
        assertThat(result.getImports(), hasSize(0));
        assertThat(result.getDelegates(), hasSize(0));
        assertThat(result.getProperties(), hasSize(0));
        assertThat(result.getMethods(), hasSize(0));
    }

    @Test
    @Ignore
    public void simpleBinaryOperation() throws IOException {
        final String src = "2 + 3\n";
        final String file = "/de/weltraumschaf/caythe/frontend/Type.ct";
        final Block expected = BlockBuilder
            .block(file, 1, 0)
                .statements()
                    .binops()
                        .addition(
                            integer(2L, 1, 0),
                            integer(3L, 1, 4),
                            1, 0)
                        .end()
                    .end()
                .end();
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type ast = sut.visit(parseSource(src));

        assertThat(ast, is(expected));
    }

    @Test
    @Ignore
    public void mathOperationWithMultipleOperands() throws IOException {
        final String src = "1 + 2 * 3 - 4\n";
        final String file = "/de/weltraumschaf/caythe/frontend/Type.ct";
        final Block expected = BlockBuilder
            .block(file, 1, 0)
                .statements()
                    .binops()
                    .subtraction(
//                        addition(
//                            integer(1L, 1, 0),
//                            multiplication(
//                                integer(2L, 1, 4),
//                                integer(3L, 1, 8),
//                                1, 4
//                            ),
//                            1, 0
//                        ),
                        null,
                        integer(4L, 1, 12),
                        1, 0)
                    .end()
                .end()
            .end();
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type ast = sut.visit(parseSource(src));

        assertThat(ast, is(expected));
    }


    @Test
    @Ignore
    public void mathOperationWithMultipleOperandsAndPArens() throws IOException {
        final String src = "(1 + 2) * (3 - 4)\n";
        final String file = "/de/weltraumschaf/caythe/frontend/Type.ct";
        final Block expected = BlockBuilder
            .block(file, 1, 0)
                .statements()
                    .binops()
                        .multiplication(
//                            addition(
//                                integer(1L, 1, 1),
//                                integer(2L, 1, 5),
//                                1, 1
//                            ),
//                            subtraction(
//                                integer(3L, 1, 11),
//                                integer(4L, 1, 15),
//                                1, 11
//                            ),
                            null,
                            null,
                            1, 0)
                        .end()
                    .end()
                .end();
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type ast = sut.visit(parseSource(src));

        assertThat(ast, is(expected));
    }

}

package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.caythe.intermediate.ast.*;
import de.weltraumschaf.caythe.intermediate.ast.builder.UnitBuilder;
import de.weltraumschaf.caythe.intermediate.model.*;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import static de.weltraumschaf.caythe.intermediate.ast.builder.BinaryOperationBuilder.addition;
import static de.weltraumschaf.caythe.intermediate.ast.builder.BinaryOperationBuilder.multiplication;
import static de.weltraumschaf.caythe.intermediate.ast.builder.BinaryOperationBuilder.subtraction;
import static de.weltraumschaf.caythe.intermediate.ast.builder.LiteralBuilder.integer;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link SourceToIntermediateTransformer}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public class SourceToIntermediateTransformerTest extends VisitorTestCase {

    private SourceToIntermediateTransformer createSut(final String src) {
        return new SourceToIntermediateTransformer(TypeName.fromFileName(Paths.get(src)));
    }

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
    public void typeDeclaration() throws IOException {
        final String file = "/de/weltraumschaf/caythe/frontend/Type.ct";
        final InputStream src = getClass().getResourceAsStream(file);
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseSource(src));

        assertThat(result, is(not(nullValue())));
        assertThat(result.getName(), is(new TypeName("de.weltraumschaf.caythe.frontend", "Type")));
        assertThat(result.getFacet(), is(Facet.CLASS));
        assertThat(result.getVisibility(), is(Visibility.EXPORT));
    }

    @Test
    public void importDeclarations() throws IOException {
        final String file = "/de/weltraumschaf/caythe/frontend/Type.ct";
        final InputStream src = getClass().getResourceAsStream(file);
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseSource(src));

        assertThat(result, is(not(nullValue())));
        assertThat(result.getImports(), hasSize(3));
        assertThat(result.getImports(), containsInAnyOrder(
            new Import(TypeName.fromFullQualifiedName("org.caythe.core.basetypes.Object"), ""),
            new Import(TypeName.fromFullQualifiedName("org.caythe.core.basetypes.Integer"), ""),
            new Import(TypeName.fromFullQualifiedName("org.caythe.core.basetypes.String"), "FooString")
        ));
    }

    @Test
    @Ignore
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
        final SourceToIntermediateTransformer sut = createSut("/de/weltraumschaf/caythe/frontend/Type.ct");

        final Type ast = sut.visit(parseSource(src));

        assertThat( ast, is(expected));
    }

    @Test
    @Ignore
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
        final SourceToIntermediateTransformer sut = createSut("/de/weltraumschaf/caythe/frontend/Type.ct");

        final Type ast = sut.visit(parseSource(src));

        assertThat( ast, is(expected));
    }


    @Test
    @Ignore
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
        final SourceToIntermediateTransformer sut = createSut("/de/weltraumschaf/caythe/frontend/Type.ct");

        final Type ast = sut.visit(parseSource(src));

        assertThat( ast, is(expected));
    }

}

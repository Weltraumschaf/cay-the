package de.weltraumschaf.caythe.frontend.transform;

import de.weltraumschaf.caythe.frontend.VisitorTestCase;
import de.weltraumschaf.caythe.intermediate.ast.AstNode;
import de.weltraumschaf.caythe.intermediate.ast.Unit;
import de.weltraumschaf.caythe.intermediate.ast.builder.UnitBuilder;
import de.weltraumschaf.caythe.intermediate.model.*;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;

import static de.weltraumschaf.caythe.intermediate.ast.builder.BinaryOperationBuilder.*;
import static de.weltraumschaf.caythe.intermediate.ast.builder.LiteralBuilder.integer;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link SourceToIntermediateTransformer}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public class SourceToIntermediateTransformer_TypeDeclarationTest extends TransformVisitorTestCase {

    private static final String FIXTURE_DIR = "/type_decl";
    private static final String EXPECTED_PACKAGE = EXPECTED_BASE_PACKAGE + ".type_decl";

    @Test
    public void emptyClass() throws IOException {
        final String file = createFixtureFile(FIXTURE_DIR + "/EmptyClass.ct");
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseFile(file));

        assertThat(result.getName(), is(new TypeName(EXPECTED_PACKAGE, "EmptyClass")));
        assertThat(result.getFacet(), is(Facet.CLASS));
        assertThat(result.getVisibility(), is(Visibility.EXPORT));
    }

    @Test
    public void emptyInterface() throws IOException {
        final String file = createFixtureFile(FIXTURE_DIR + "/EmptyInterface.ct");
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseFile(file));

        assertThat(result.getName(), is(new TypeName(EXPECTED_PACKAGE, "EmptyInterface")));
        assertThat(result.getFacet(), is(Facet.INTERFACE));
        assertThat(result.getVisibility(), is(Visibility.PUBLIC));
    }

    @Test
    public void emptyAnnotation() throws IOException {
        final String file = createFixtureFile(FIXTURE_DIR + "/EmptyAnnotation.ct");
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseFile(file));

        assertThat(result.getName(), is(new TypeName(EXPECTED_PACKAGE, "EmptyAnnotation")));
        assertThat(result.getFacet(), is(Facet.ANNOTATION));
        assertThat(result.getVisibility(), is(Visibility.PACKAGE));
    }

    @Test
    public void emptyEnum() throws IOException {
        final String file = createFixtureFile(FIXTURE_DIR + "/EmptyEnum.ct");
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseFile(file));

        assertThat(result.getName(), is(new TypeName(EXPECTED_PACKAGE, "EmptyEnum")));
        assertThat(result.getFacet(), is(Facet.ENUM));
        assertThat(result.getVisibility(), is(Visibility.EXPORT));
    }
}

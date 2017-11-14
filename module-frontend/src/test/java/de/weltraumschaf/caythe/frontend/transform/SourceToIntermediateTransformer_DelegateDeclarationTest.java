package de.weltraumschaf.caythe.frontend.transform;

import de.weltraumschaf.caythe.intermediate.model.*;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link SourceToIntermediateTransformer}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public class SourceToIntermediateTransformer_DelegateDeclarationTest extends TransformVisitorTestCase {

    private static final String FIXTURE_DIR = "/delegate_decl";
    private static final String EXPECTED_PACKAGE = EXPECTED_BASE_PACKAGE + ".delegate_decl";

    @Test
    public void noDelegate() throws IOException {
        final String file = createFixtureFile(FIXTURE_DIR + "/ClassNoDelegate.ct");
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseFile(file));

        assertThat(result.getName(), is(new TypeName(EXPECTED_PACKAGE, "ClassNoDelegate")));
        assertThat(result.getDelegates(), hasSize(0));
    }

    @Test
    public void oneDelegate() throws IOException {
        final String file = createFixtureFile(FIXTURE_DIR + "/AnnotationOneDelegate.ct");
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseFile(file));

        assertThat(result.getName(), is(new TypeName(EXPECTED_PACKAGE, "AnnotationOneDelegate")));
        assertThat(result.getDelegates(), hasSize(1));
        assertThat(
            result.getDelegates(),
            containsInAnyOrder(
                new Delegate("Foo")));
    }

    @Test
    public void twoDelegate() throws IOException {
        final String file = createFixtureFile(FIXTURE_DIR + "/InterfaceTwoDelegate.ct");
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseFile(file));

        assertThat(result.getName(), is(new TypeName(EXPECTED_PACKAGE, "InterfaceTwoDelegate")));
        assertThat(result.getDelegates(), hasSize(2));
        assertThat(
            result.getDelegates(),
            containsInAnyOrder(
                new Delegate("Foo"),
                new Delegate("Bar")));
    }

    @Test
    public void threeDelegate() throws IOException {
        final String file = createFixtureFile(FIXTURE_DIR + "/EnumThreeDelegate.ct");
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseFile(file));

        assertThat(result.getName(), is(new TypeName(EXPECTED_PACKAGE, "EnumThreeDelegate")));
        assertThat(result.getDelegates(), hasSize(3));
        assertThat(
            result.getDelegates(),
            containsInAnyOrder(
                new Delegate("Foo"),
                new Delegate("Bar"),
                new Delegate("Baz")));
    }

    @Test
    public void fourDelegate() throws IOException {
        final String file = createFixtureFile(FIXTURE_DIR + "/ClassFourDelegate.ct");
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseFile(file));

        assertThat(result.getName(), is(new TypeName(EXPECTED_PACKAGE, "ClassFourDelegate")));
        assertThat(result.getDelegates(), hasSize(4));
        assertThat(
            result.getDelegates(),
            containsInAnyOrder(
                new Delegate("Foo"),
                new Delegate("Bar"),
                new Delegate("Baz"),
                new Delegate("Snafu")));
    }

}

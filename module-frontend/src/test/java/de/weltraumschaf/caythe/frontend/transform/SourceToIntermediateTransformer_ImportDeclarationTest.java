package de.weltraumschaf.caythe.frontend.transform;

import de.weltraumschaf.caythe.intermediate.model.Import;
import de.weltraumschaf.caythe.intermediate.model.Type;
import de.weltraumschaf.caythe.intermediate.model.TypeName;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link SourceToIntermediateTransformer}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public class SourceToIntermediateTransformer_ImportDeclarationTest extends TransformVisitorTestCase {

    private static final String FIXTURE_DIR = "/import_decl";
    private static final String EXPECTED_PACKAGE = EXPECTED_BASE_PACKAGE + ".import_decl";

    @Test
    public void noImport() throws IOException {
        final String file = createFixtureFile(FIXTURE_DIR + "/NoImport.ct");
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseFile(file));

        assertThat(result.getName(), is(new TypeName(EXPECTED_PACKAGE, "NoImport")));
        assertThat(result.getImports(), hasSize(0));
    }

    @Test
    public void oneImport() throws IOException {
        final String file = createFixtureFile(FIXTURE_DIR + "/OneImport.ct");
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseFile(file));

        assertThat(result.getName(), is(new TypeName(EXPECTED_PACKAGE, "OneImport")));
        assertThat(result.getImports(), hasSize(1));
        assertThat(
            result.getImports(),
            containsInAnyOrder(
                new Import(new TypeName("org.snafu.core","Foo"))));
    }

    @Test
    public void twoImport() throws IOException {
        final String file = createFixtureFile(FIXTURE_DIR + "/TwoImport.ct");
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseFile(file));

        assertThat(result.getName(), is(new TypeName(EXPECTED_PACKAGE, "TwoImport")));
        assertThat(result.getImports(), hasSize(2));
        assertThat(
            result.getImports(),
            containsInAnyOrder(
                new Import(new TypeName("org.snafu.core","Foo")),
                new Import(new TypeName("org.snafu.core","Bar"))));
    }

    @Test
    public void threeImportWithAlias() throws IOException {
        final String file = createFixtureFile(FIXTURE_DIR + "/ThreeImportWithAlias.ct");
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseFile(file));

        assertThat(result.getName(), is(new TypeName(EXPECTED_PACKAGE, "ThreeImportWithAlias")));
        assertThat(result.getImports(), hasSize(3));
        assertThat(
            result.getImports(),
            containsInAnyOrder(
                new Import(new TypeName("org.snafu.core","Foo")),
                new Import(new TypeName("org.snafu.core","Bar"), "Snafu"),
                new Import(new TypeName("org.snafu.core","Baz"))));
    }

    @Test
    public void fourImportWithAlias() throws IOException {
        final String file = createFixtureFile(FIXTURE_DIR + "/FourImportWithAlias.ct");
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseFile(file));

        assertThat(result.getName(), is(new TypeName(EXPECTED_PACKAGE, "FourImportWithAlias")));
        assertThat(result.getImports(), hasSize(4));
        assertThat(
            result.getImports(),
            containsInAnyOrder(
                new Import(new TypeName("org.snafu.core","Foo")),
                new Import(new TypeName("org.snafu.core","Bar"), "Snafu"),
                new Import(new TypeName("org.snafu.core","Baz")),
                new Import(new TypeName("org.snafu.core","Snafu"), "Bar" )));
    }
}

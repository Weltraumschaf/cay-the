package de.weltraumschaf.caythe.frontend;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.weltraumschaf.caythe.intermediate.Coordinate;
import de.weltraumschaf.caythe.intermediate.Manifest;
import de.weltraumschaf.caythe.intermediate.Version;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link DefaultCayTheManifestVisitor}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class DefaultCayTheManifestVisitorTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    private final Injector injector = Guice.createInjector(new DependencyInjectionConfig());
    private final Parsers parsers = new Parsers(injector);
    @SuppressWarnings("unchecked")
    private final CayTheManifestVisitor<Manifest> sut = injector.getInstance(CayTheManifestVisitor.class);

    private InputStream stream(final String input) {
        return new ByteArrayInputStream(input.getBytes());
    }

    private CayTheManifestParser parser(final String input) throws IOException {
        return parser(stream(input));
    }

    private CayTheManifestParser parser(final InputStream input) throws IOException {
        return parsers.newManifestParser(input);
    }

    @Test
    public void visit_missingGroup() throws IOException {
        thrown.expect(SyntaxError.class);
        thrown.expectMessage("There is no group declared in the manifest! A group directive is mandatory.");

        final String src =
            "artifact   example\n" +
                "version    1.2.3\n" +
                "\n" +
                "namespace  de.weltraumschaf.example\n";

        final Manifest manifest = sut.visit(parser(src).manifest());
    }

    @Test
    public void visit_missingArtifact() throws IOException {
        thrown.expect(SyntaxError.class);
        thrown.expectMessage("There is no artifact declared in the manifest! A artifact directive is mandatory.");

        final String src =
            "group      de.weltraumschaf\n" +
                "version    1.2.3\n" +
                "\n" +
                "namespace  de.weltraumschaf.example\n";

        final Manifest manifest = sut.visit(parser(src).manifest());
    }

    @Test
    public void visit_missingVersion() throws IOException {
        thrown.expect(SyntaxError.class);
        thrown.expectMessage("There is no version declared in the manifest! A version directive is mandatory.");

        final String src =
            "group      de.weltraumschaf\n" +
                "artifact   example\n" +
                "\n" +
                "namespace  de.weltraumschaf.example\n";

        final Manifest manifest = sut.visit(parser(src).manifest());
    }

    @Test
    public void visit_missingNamespace() throws IOException {
        thrown.expect(SyntaxError.class);
        thrown.expectMessage("There is no namespace declared in the manifest! A namespace directive is mandatory.");

        final String src =
            "group      de.weltraumschaf\n" +
                "artifact   example\n" +
                "version    1.2.3\n";

        final Manifest manifest = sut.visit(parser(src).manifest());
    }

    @Test
    @Ignore
    public void visit_duplicateGroup() throws IOException {
        thrown.expect(SyntaxError.class);
        thrown.expectMessage("");

        final String src =
            "group      de.weltraumschaf\n" +
                "artifact   example\n" +
                "group      com.weltraumschaf\n" +
                "version    1.2.3\n" +
                "\n" +
                "namespace  de.weltraumschaf.example\n";

        final Manifest manifest = sut.visit(parser(src).manifest());
    }

    @Test
    @Ignore
    public void visit_duplicateArtifact() throws IOException {
        thrown.expect(SyntaxError.class);
        thrown.expectMessage("");

        final String src =
            "group      de.weltraumschaf\n" +
                "artifact   example\n" +
                "artifact   snafu\n" +
                "version    1.2.3\n" +
                "\n" +
                "namespace  de.weltraumschaf.example\n";

        final Manifest manifest = sut.visit(parser(src).manifest());
    }

    @Test
    @Ignore
    public void visit_duplicateVersion() throws IOException {
        thrown.expect(SyntaxError.class);
        thrown.expectMessage("");

        final String src =
            "group      de.weltraumschaf\n" +
                "artifact   example\n" +
                "version    1.2.3\n" +
                "version    2.4.2\n" +
                "\n" +
                "namespace  de.weltraumschaf.example\n";

        final Manifest manifest = sut.visit(parser(src).manifest());
    }

    @Test
    @Ignore
    public void visit_duplicateNamespace() throws IOException {
        thrown.expect(SyntaxError.class);
        thrown.expectMessage("");

        final String src =
            "group      de.weltraumschaf\n" +
                "artifact   example\n" +
                "version    1.2.3\n" +
                "\n" +
                "namespace  de.weltraumschaf.example\n" +
                "namespace  de.weltraumschaf.snafu\n";

        final Manifest manifest = sut.visit(parser(src).manifest());
    }

    @Test
    @Ignore
    public void visit_importMissingGroup() throws IOException {
        thrown.expect(SyntaxError.class);
        thrown.expectMessage("");

        final String src =
            "group      de.weltraumschaf\n" +
                "artifact   example\n" +
                "version    1.2.3\n" +
                "\n" +
                "namespace  de.weltraumschaf.example\n" +
                "\n" +
                "import test:7.8.9\n";

        final Manifest manifest = sut.visit(parser(src).manifest());
    }

    @Test
    @Ignore
    public void visit_importMissingArtifact() throws IOException {
        thrown.expect(SyntaxError.class);
        thrown.expectMessage("");

        final String src =
            "group      de.weltraumschaf\n" +
                "artifact   example\n" +
                "version    1.2.3\n" +
                "\n" +
                "namespace  de.weltraumschaf.example\n" +
                "\n" +
                "import de.weltraumschaf:7.8.9\n";

        final Manifest manifest = sut.visit(parser(src).manifest());
    }

    @Test
    @Ignore
    public void visit_importMissingVersion() throws IOException {
        thrown.expect(SyntaxError.class);
        thrown.expectMessage("");

        final String src =
            "group      de.weltraumschaf\n" +
                "artifact   example\n" +
                "version    1.2.3\n" +
                "\n" +
                "namespace  de.weltraumschaf.example\n" +
                "\n" +
                "import de.weltraumschaf:test\n";

        final Manifest manifest = sut.visit(parser(src).manifest());
    }

    @Test
    @Ignore
    public void visit_importDuplicate() throws IOException {
        thrown.expect(SyntaxError.class);
        thrown.expectMessage("");

        final String src =
            "group      de.weltraumschaf\n" +
                "artifact   example\n" +
                "version    1.2.3\n" +
                "\n" +
                "namespace  de.weltraumschaf.example\n" +
                "\n" +
                "import de.weltraumschaf:test:7.8.9\n" +
                "import de.weltraumschaf:test:7.8.9\n";

        final Manifest manifest = sut.visit(parser(src).manifest());
    }

    @Test
    public void visit() throws IOException {
        final String src =
            "group      de.weltraumschaf\n" +
                "artifact   example\n" +
                "version    1.2.3\n" + "\n" +
                "namespace  de.weltraumschaf.example\n" +
                "\n" +
                "import de.weltraumschaf:core:4.5.6\n" +
                "import de.weltraumschaf:test:7.8.9\n";

        final Manifest manifest = sut.visit(parser(src).manifest());

        assertThat(manifest.getCoordinate(), is(new Coordinate("de.weltraumschaf", "example", new Version(1, 2, 3))));
        assertThat(manifest.getNamespace(), is("de.weltraumschaf.example"));
        assertThat(manifest.getImports(),
            containsInAnyOrder(new Coordinate("de.weltraumschaf", "core", new Version(4, 5, 6)),
                new Coordinate("de.weltraumschaf", "test", new Version(7, 8, 9))));
    }
}

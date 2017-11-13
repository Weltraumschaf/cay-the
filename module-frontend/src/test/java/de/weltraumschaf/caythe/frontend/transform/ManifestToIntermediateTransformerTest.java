package de.weltraumschaf.caythe.frontend.transform;

import de.weltraumschaf.caythe.frontend.CayTheManifestVisitor;
import de.weltraumschaf.caythe.frontend.SyntaxError;
import de.weltraumschaf.caythe.frontend.VisitorTestCase;
import de.weltraumschaf.caythe.frontend.transform.ManifestToIntermediateTransformer;
import de.weltraumschaf.caythe.intermediate.model.Coordinate;
import de.weltraumschaf.caythe.intermediate.model.Manifest;

import de.weltraumschaf.caythe.intermediate.model.Version;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link ManifestToIntermediateTransformer}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class ManifestToIntermediateTransformerTest extends VisitorTestCase {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    @SuppressWarnings("unchecked")
    private final CayTheManifestVisitor<Manifest> sut = new ManifestToIntermediateTransformer();

    @Test
    public void visit_missingGroup() throws IOException {
        thrown.expect(SyntaxError.class);
        thrown.expectMessage("There is no group declared in the manifest! A group directive is mandatory.");

        final String src =
            "artifact   example\n" +
                "version    1.2.3\n" +
                "\n" +
                "namespace  de.weltraumschaf.example\n";

        final Manifest manifest = sut.visit(parseManifest(src));
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

        final Manifest manifest = sut.visit(parseManifest(src));
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

        final Manifest manifest = sut.visit(parseManifest(src));
    }

    @Test
    public void visit_missingNamespace() throws IOException {
        thrown.expect(SyntaxError.class);
        thrown.expectMessage("There is no namespace declared in the manifest! A namespace directive is mandatory.");

        final String src =
            "group      de.weltraumschaf\n" +
                "artifact   example\n" +
                "version    1.2.3\n";

        final Manifest manifest = sut.visit(parseManifest(src));
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

        final Manifest manifest = sut.visit(parseManifest(src));
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

        final Manifest manifest = sut.visit(parseManifest(src));
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

        final Manifest manifest = sut.visit(parseManifest(src));
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

        final Manifest manifest = sut.visit(parseManifest(src));
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

        final Manifest manifest = sut.visit(parseManifest(src));
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

        final Manifest manifest = sut.visit(parseManifest(src));
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

        final Manifest manifest = sut.visit(parseManifest(src));
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

        final Manifest manifest = sut.visit(parseManifest(src));
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

        final Manifest manifest = sut.visit(parseManifest(src));

        assertThat(manifest.getCoordinate(), is(new Coordinate("de.weltraumschaf", "example", new Version(1, 2, 3))));
        assertThat(manifest.getNamespace(), is("de.weltraumschaf.example"));
        assertThat(manifest.getImports(),
            containsInAnyOrder(new Coordinate("de.weltraumschaf", "core", new Version(4, 5, 6)),
                new Coordinate("de.weltraumschaf", "test", new Version(7, 8, 9))));
    }
}

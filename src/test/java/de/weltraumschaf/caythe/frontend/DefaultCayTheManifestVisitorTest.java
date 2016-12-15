package de.weltraumschaf.caythe.frontend;

import com.google.inject.Injector;
import de.weltraumschaf.caythe.core.Injectors;
import de.weltraumschaf.caythe.intermediate.Coordinate;
import de.weltraumschaf.caythe.intermediate.Manifest;
import de.weltraumschaf.caythe.intermediate.Version;
import de.weltraumschaf.commons.application.IO;
import de.weltraumschaf.commons.application.IOStreams;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link DefaultCayTheManifestVisitor}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class DefaultCayTheManifestVisitorTest {

    private final IO io = new IOStreams(mock(InputStream.class), System.out, System.err);
    private final Injector injector = new Injectors(io).newInterpreterInjector();
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
    public void foo() throws IOException {
        final String src =
            "group      de.weltraumschaf\n" +
            "artifact   example\n" +
            "version    1.2.3\n" +
            "\n" +
            "namespace  de.weltraumschaf.example\n" +
            "\n" +
            "import de.weltraumschaf:core:4.5.6\n" +
            "import de.weltraumschaf:test:7.8.9\n";

        final Manifest manifest = sut.visit(parser(src).manifest());

        assertThat(
            manifest.getCoordinate(),
            is(new Coordinate("de.weltraumschaf", "example", new Version(1, 2, 3))));
        assertThat(manifest.getNamespace(), is("de.weltraumschaf.example"));
        assertThat(
            manifest.getImports(),
            containsInAnyOrder(
                new Coordinate("de.weltraumschaf", "core", new Version(4, 5,6)),
                new Coordinate("de.weltraumschaf", "test", new Version(7, 8,9))));
    }
}

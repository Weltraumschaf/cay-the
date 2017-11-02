package de.weltraumschaf.caythe.cli.run;

import de.weltraumschaf.caythe.intermediate.model.Coordinate;
import de.weltraumschaf.caythe.intermediate.model.Manifest;
import de.weltraumschaf.caythe.intermediate.model.Version;
import de.weltraumschaf.commons.application.IO;
import org.junit.Test;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link ModuleInspector}.
 */
public final class ModuleInspectorTest {
    private final Path moduleDir = mock(Path.class);
    private final ModuleInspector sut = new ModuleInspector(mock(IO.class));

    @Test
    public void preamble() {
        when(moduleDir.toString()).thenReturn("/foo/bar");

        assertThat(sut.preamble(moduleDir), is(
            "Inspecting Module\n"
                + "=================\n"
                + "\n"
                + "Location : /foo/bar\n"
                + "\n"));
    }

    @Test
    public void dumpManifestInfo_noImports() {
        final Manifest manifest = new Manifest(
            new Coordinate("de.weltraumschaf", "test-module", new Version(1, 2, 3)),
            "de.weltraumschaf.testmod",
            Collections.emptyList()
        );
        final IO io = mock(IO.class);

        assertThat(sut.dumpManifestInfo(manifest), is(
            "Manifest\n"
                + "--------\n"
                + "\n"
                + "group     : de.weltraumschaf\n"
                + "artifact  : test-module\n"
                + "version   : 1.2.3\n"
                + "namespace : de.weltraumschaf.testmod\n"
                + "imports   : -\n"));
    }

    @Test
    public void dumpManifestInfo_oneImport() {
        final Manifest manifest = new Manifest(
            new Coordinate("de.weltraumschaf", "test-module", new Version(1, 2, 3)),
            "de.weltraumschaf.testmod",
            Collections.singletonList(
                new Coordinate("org.snafu", "foo", new Version(1, 0, 1)))
        );
        final IO io = mock(IO.class);

        assertThat(sut.dumpManifestInfo(manifest), is(
            "Manifest\n"
                + "--------\n"
                + "\n"
                + "group     : de.weltraumschaf\n"
                + "artifact  : test-module\n"
                + "version   : 1.2.3\n"
                + "namespace : de.weltraumschaf.testmod\n"
                + "imports   : \n"
                + "  org.snafu:foo:1.0.1\n"));
    }

    @Test
    public void dumpManifestInfo_multipleImports() {
        final Manifest manifest = new Manifest(
            new Coordinate("de.weltraumschaf", "test-module", new Version(1, 2, 3)),
            "de.weltraumschaf.testmod",
            Arrays.asList(
                new Coordinate("org.snafu", "foo", new Version(1, 0, 1)),
                new Coordinate("org.snafu", "bar", new Version(1, 0, 2)),
                new Coordinate("org.snafu", "baz", new Version(1, 0, 3))
            )
        );
        final IO io = mock(IO.class);

        assertThat(sut.dumpManifestInfo(manifest), is(
            "Manifest\n"
                + "--------\n"
                + "\n"
                + "group     : de.weltraumschaf\n"
                + "artifact  : test-module\n"
                + "version   : 1.2.3\n"
                + "namespace : de.weltraumschaf.testmod\n"
                + "imports   : \n"
                + "  org.snafu:foo:1.0.1\n"
                + "  org.snafu:bar:1.0.2\n"
                + "  org.snafu:baz:1.0.3\n"));
    }
}

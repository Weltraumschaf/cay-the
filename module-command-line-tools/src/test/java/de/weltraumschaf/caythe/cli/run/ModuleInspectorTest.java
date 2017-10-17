package de.weltraumschaf.caythe.cli.run;

import de.weltraumschaf.caythe.intermediate.model.Coordinate;
import de.weltraumschaf.caythe.intermediate.model.Manifest;
import de.weltraumschaf.caythe.intermediate.model.Version;
import de.weltraumschaf.commons.application.IO;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link ModuleInspector}.
 */
public final class ModuleInspectorTest {
    private final ModuleInspector sut = new ModuleInspector();

    @Test
    public void dumpManifestInfo_noImports() {
        final Manifest manifest = new Manifest(
            new Coordinate("de.weltraumschaf", "test-module", new Version(1, 2, 3)),
            "de.weltraumschaf.testmod",
            Collections.emptyList()
        );
        final IO io = mock(IO.class);

        sut.dumpManifestInfo(manifest, io);

        verify(io, times(1))
            .print("group     : de.weltraumschaf\n"
                + "artifact  : test-module\n"
                + "version   : 1.2.3\n"
                + "namespace : de.weltraumschaf.testmod\n"
                + "imports   : -\n");
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

        sut.dumpManifestInfo(manifest, io);

        verify(io, times(1))
            .print("group     : de.weltraumschaf\n"
                + "artifact  : test-module\n"
                + "version   : 1.2.3\n"
                + "namespace : de.weltraumschaf.testmod\n"
                + "imports   : org.snafu:foo:1.0.1\n");
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

        sut.dumpManifestInfo(manifest, io);

        verify(io, times(1))
            .print("group     : de.weltraumschaf\n"
                + "artifact  : test-module\n"
                + "version   : 1.2.3\n"
                + "namespace : de.weltraumschaf.testmod\n"
                + "imports   : org.snafu:foo:1.0.1\n" +
                "org.snafu:bar:1.0.2\n" +
                "org.snafu:baz:1.0.3\n");
    }
}

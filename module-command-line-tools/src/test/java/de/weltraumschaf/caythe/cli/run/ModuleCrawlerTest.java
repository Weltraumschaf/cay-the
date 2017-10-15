package de.weltraumschaf.caythe.cli.run;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link ModuleCrawler}.
 */
public final class ModuleCrawlerTest {
    private final ModuleCrawler sut = new ModuleCrawler();

    private static Path testModule() throws URISyntaxException {
        return Paths.get(
            ModuleCrawlerTest.class
                .getResource("/de/weltraumschaf/caythe/cli/run/test-module").toURI());
    }

    @Test
    public void find_manifestFile() throws URISyntaxException, IOException {
        final Path moduleDir = testModule();

        final ModuleFiles result = sut.find(moduleDir);

        assertThat(result.getManifestFile(), is(moduleDir.resolve("Module.mf")));
    }

    @Test
    public void find_sourceFiles() throws URISyntaxException, IOException {
        final Path moduleDir = testModule();

        final ModuleFiles result = sut.find(moduleDir);

        assertThat(result.getSourceFiles(), containsInAnyOrder(
            moduleDir.resolve("Foo.ct"),
            moduleDir.resolve("package-a/Bar.ct"),
            moduleDir.resolve("package-a/package-b/Baz.ct")
        ));
    }

    @Test
    public void find_otherFiles() throws URISyntaxException, IOException {
        final Path moduleDir = testModule();

        final ModuleFiles result = sut.find(moduleDir);

        assertThat(result.getOtherFiles(), containsInAnyOrder(
            moduleDir.resolve("Readme.md"),
            moduleDir.resolve("package-a/PackageInfo.md"),
            moduleDir.resolve("package-a/package-b/PackageInfo.md")
        ));
    }
}

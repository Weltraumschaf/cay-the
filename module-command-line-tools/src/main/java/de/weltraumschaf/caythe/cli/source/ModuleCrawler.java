package de.weltraumschaf.caythe.cli.source;

import de.weltraumschaf.commons.validate.Validate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Finds all files for a module.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class ModuleCrawler {
    private static final String EXTENSION_OF_MANIFESTS_FILES = ".mf";
    private static final String EXTENSION_OF_SOURCE_FILES = ".ct";

    /**
     * Finds all files in the given module dir.
     *
     * @param moduleDir must not be {@code null}
     * @return never {@code null}
     */
    public ModuleFiles find(final Path moduleDir) throws IOException {
        Validate.notNull(moduleDir, "moduleDir");
        return new ModuleFiles(loadManifest(moduleDir),
            loadSourceFiles(moduleDir),
            loadOtherFiles(moduleDir));
    }

    private Path loadManifest(final Path dir) throws IOException {
        final Path manifestFile = dir.resolve("Module" + EXTENSION_OF_MANIFESTS_FILES);

        if (!Files.exists(manifestFile)) {
            throw new FileNotFoundException(
                String.format("Manifest file %s does not exist!", manifestFile));
        }

        if (!Files.isReadable(manifestFile)) {
            throw new IOException(
                String.format("Manifest file %s is not readable!", manifestFile));
        }

        return manifestFile;
    }

    private Collection<Path> loadSourceFiles(final Path moduleDir) throws IOException {
        final FindSourceFiles finder = new FindSourceFiles();
        Files.walkFileTree(moduleDir, finder);
        return finder.getFound();
    }

    private Collection<Path> loadOtherFiles(final Path moduleDir) throws IOException {
        final FindOtherFiles finder = new FindOtherFiles();
        Files.walkFileTree(moduleDir, finder);
        return finder.getFound();
    }

    private static class FindSourceFiles extends SimpleFileVisitor<Path> {
        private final Collection<Path> found = new ArrayList<>();

        Collection<Path> getFound() {
            return found;
        }

        @Override
        public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
            if (isSourceFile(file)) {
                found.add(file);
            }

            return super.visitFile(file, attrs);
        }

        private boolean isSourceFile(final Path file) {
            return file.toString().endsWith(EXTENSION_OF_SOURCE_FILES);
        }
    }

    private static class FindOtherFiles extends SimpleFileVisitor<Path> {
        private final Collection<Path> found = new ArrayList<>();

        Collection<Path> getFound() {
            return found;
        }

        @Override
        public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
            if (isOtherFile(file)) {
                found.add(file);
            }

            return super.visitFile(file, attrs);
        }

        private boolean isOtherFile(final Path file) {
            if (file.toString().endsWith(EXTENSION_OF_MANIFESTS_FILES)) {
                return false;
            }

            if (file.toString().endsWith(EXTENSION_OF_SOURCE_FILES)) {
                return false;
            }

            return true;
        }
    }
}

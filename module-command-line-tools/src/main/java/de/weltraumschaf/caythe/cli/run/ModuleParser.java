package de.weltraumschaf.caythe.cli.run;

import de.weltraumschaf.caythe.frontend.*;
import de.weltraumschaf.caythe.intermediate.ast.AstNode;
import de.weltraumschaf.caythe.intermediate.model.Manifest;
import de.weltraumschaf.caythe.intermediate.model.Module;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Parses all files in a module.
 */
final class ModuleParser {
    private Parsers parsers = new Parsers();

    /**
     * Parses all files in the given directory.
     *
     * @param moduleDir must not be {@code null}
     * @return never {@code null}
     * @throws IOException on any subsequent I/O error related to the parsed files
     */
    Module parse(final Path moduleDir) throws IOException {
        final ModuleFiles moduleFiles = findModuleFiles(moduleDir);
        final Manifest manifest = parseModuleManifest(moduleFiles);
        final Collection<AstNode> units = parseSourceFiles(moduleFiles);

        return new Module(manifest, units, moduleFiles.getOtherFiles());
    }

    private ModuleFiles findModuleFiles(final Path moduleDir) throws IOException {
        return new ModuleCrawler().find(moduleDir);
    }

    private Manifest parseModuleManifest(final ModuleFiles moduleFiles) throws IOException {
        try (final InputStream src = Files.newInputStream(moduleFiles.getManifestFile())) {
            final CayTheManifestParser parser = parsers.newManifestParser(src);
            return new ManifestToIntermediateTransformer().visit(parser.manifest());
        }
    }

    private Collection<AstNode> parseSourceFiles(final ModuleFiles moduleFiles) throws IOException {
        final Collection<AstNode> units = new ArrayList<>();

        for (final Path file : moduleFiles.getSourceFiles()) {
            try (final InputStream src = Files.newInputStream(file)) {
                final CayTheSourceParser parser = parsers.newSourceParser(src);
                final AstNode unit = new SourceToIntermediateTransformer().visit(parser.unit());
                units.add(unit);
            }
        }

        return Collections.unmodifiableCollection(units);
    }
}

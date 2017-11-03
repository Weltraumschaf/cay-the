package de.weltraumschaf.caythe.cli.source;

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
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0Ø
 */
public final class ModuleParser {
    private Parsers parsers = new Parsers();

    /**
     * Parses all files in the given directory.
     *
     * @param moduleFiles must not be {@code null}
     * @return never {@code null}
     * @throws IOException on any subsequent I/O error related to the parsed files
     */
    public Module parse(final ModuleFiles moduleFiles) throws IOException {
        final Manifest manifest = parseModuleManifest(moduleFiles);
        final Collection<AstNode> types = parseSourceFiles(moduleFiles);

        return new Module(manifest, types, moduleFiles.getOtherFiles());
    }

    private Manifest parseModuleManifest(final ModuleFiles moduleFiles) throws IOException {
        try (final InputStream src = Files.newInputStream(moduleFiles.getManifestFile())) {
            final CayTheManifestParser parser = parsers.newManifestParser(src);
            @SuppressWarnings("unchecked") final CayTheManifestVisitor<Manifest> visitor =
                parsers.injector().getInstance(CayTheManifestVisitor.class);
            return visitor.visit(parser.manifest());
        }
    }

    private Collection<AstNode> parseSourceFiles(final ModuleFiles moduleFiles) throws IOException {
        final Collection<AstNode> types = new ArrayList<>();

        for (final Path file : moduleFiles.getSourceFiles()) {
            try (final InputStream src = Files.newInputStream(file)) {
                final CayTheSourceParser parser = parsers.newSourceParser(src);
                @SuppressWarnings("unchecked") final CayTheSourceBaseVisitor<AstNode> visitor =
                    parsers.injector().getInstance(CayTheSourceBaseVisitor.class);
                final AstNode type = visitor.visit(parser.type());
                types.add(type);
            }
        }

        return Collections.unmodifiableCollection(types);
    }
}

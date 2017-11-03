package de.weltraumschaf.caythe.cli.inspect;

import de.weltraumschaf.caythe.cli.source.ModuleFiles;
import de.weltraumschaf.caythe.frontend.CayTheManifestParser;
import de.weltraumschaf.caythe.frontend.CayTheSourceParser;
import de.weltraumschaf.caythe.frontend.Parsers;
import de.weltraumschaf.commons.application.IO;
import de.weltraumschaf.commons.validate.Validate;
import org.antlr.v4.gui.Trees;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.Tree;

import javax.print.PrintException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Draw and save the parse tree of given file to a PostScript file.
 */
final class ParseTreeDrawer {

    private static final String FORMAT_NAME = "ps";
    private Parsers parsers = new Parsers();
    private final IO io;

    ParseTreeDrawer(final IO io) {
        super();
        this.io = Validate.notNull(io, "io");
    }

    void draw(final ModuleFiles files, final Path targetDirectory) throws IOException, PrintException {
        if (!Files.isDirectory(targetDirectory)) {
            throw new IllegalArgumentException(String.format("Not a directory: %s!", targetDirectory));
        }

        io.println(String.format("Generating parse tree images into %s ...", targetDirectory));
        writeManifestTree(files.getManifestFile(), targetDirectory.resolve(convertFileName(files.getManifestFile())));

        for (final Path src : files.getSourceFiles()) {
            writeSourceTree(src, targetDirectory.resolve(convertFileName(src)));
        }
    }

    Path convertFileName(final Path in) {
        final String file = in.toString();
        final int start = file.lastIndexOf('/') + 1;
        final int stop = file.lastIndexOf('.') + 1;
        return Paths.get(file.substring(start, stop) + FORMAT_NAME);
    }

    private void writeManifestTree(final Path src, final Path target) throws IOException, PrintException {
        try (final InputStream in = Files.newInputStream(src)) {
            final CayTheManifestParser parser = parsers.newManifestParser(in);

            drawAndWrite(target, parser, parser.manifest());
        }
    }

    private void writeSourceTree(final Path src, final Path target) throws IOException, PrintException {
        try (final InputStream in = Files.newInputStream(src)) {
            final CayTheSourceParser parser = parsers.newSourceParser(in);

            drawAndWrite(target, parser, parser.type());
        }
    }

    private void drawAndWrite(final Path target, final Parser parser, final Tree parseTree) throws IOException, PrintException {
        io.println(String.format("Writing %s ...", target));
        Trees.save(parseTree, parser, target.toString());
    }

}

package de.weltraumschaf.caythe.cli.run;

import de.weltraumschaf.caythe.frontend.CayTheManifestParser;
import de.weltraumschaf.caythe.frontend.CayTheSourceParser;
import de.weltraumschaf.caythe.frontend.Parsers;
import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.Tree;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Draw and save the parse tree of given file to a PNG image file.
 */
final class ParseTreeDrawer {

    private static final String FORMAT_NAME = "png";
    private Parsers parsers = new Parsers();

    void writeManifestTree(final Path src, final Path target) throws IOException {
        try (final InputStream in = Files.newInputStream(src)) {
            final CayTheManifestParser parser = parsers.newManifestParser(in);

            drawAndWrite(target, parser, parser.manifest());
        }
    }

    void writeSourceTree(final Path src, final Path target) throws IOException {
        try (final InputStream in = Files.newInputStream(src)) {
            final CayTheSourceParser parser = parsers.newSourceParser(in);

            drawAndWrite(target, parser, parser.type());
        }
    }

    private void drawAndWrite(final Path target, final Parser parser, final Tree parseTree) throws IOException {
        final TreeViewer viewer = new TreeViewer(Arrays.asList(parser.getRuleNames()), parseTree);

        try (final OutputStream out = Files.newOutputStream(target)) {
            ImageIO.write(drawImage(viewer), FORMAT_NAME, out);
        }
    }

    private BufferedImage drawImage(final TreeViewer viewer) {
        final BufferedImage image = new BufferedImage(
            viewer.getSize().width,
            viewer.getSize().height,
            BufferedImage.TYPE_INT_ARGB);
        final Graphics graphics = image.createGraphics();

        viewer.paint(graphics);
        graphics.dispose();

        return image;
    }
}

package de.weltraumschaf.caythe.frontend;

import org.antlr.v4.runtime.tree.ParseTree;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Base test case for visitor tests.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public abstract class VisitorTestCase {
    private final Parsers parsers = new Parsers();

    private InputStream stream(final String input) {
        return new ByteArrayInputStream(input.getBytes());
    }

    public final CayTheManifestParser createManifestParser(final String input) throws IOException {
        return createManifestParser(stream(input));
    }

    public final CayTheManifestParser createManifestParser(final InputStream input) throws IOException {
        return parsers.newManifestParser(input);
    }

    public ParseTree parseManifest(final String src) throws IOException {
        return parseManifest(new ByteArrayInputStream(src.getBytes()));
    }

    public ParseTree parseManifest(final InputStream src) throws IOException {
        return createManifestParser(src).manifest();
    }

    public final CayTheSourceParser createSourceParser(final String input) throws IOException {
        return createSourceParser(stream(input));
    }

    public final CayTheSourceParser createSourceParser(final InputStream input) throws IOException {
        return parsers.newSourceParser(input);
    }

    public ParseTree parseSource(final String src) throws IOException {
        return parseSource(new ByteArrayInputStream(src.getBytes()));
    }

    public ParseTree parseSource(final InputStream src) throws IOException {
        return createSourceParser(src).type();
    }

    public ParseTree parseFile(final String file) throws IOException {
        return parseSource(getClass().getResourceAsStream(file));
    }
}

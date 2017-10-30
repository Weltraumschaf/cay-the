package de.weltraumschaf.caythe.frontend;

import com.google.inject.Injector;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

abstract class VisitorTestCase {
    private final Parsers parsers = new Parsers();

    final Injector injector() {
        return parsers.injector();
    }

    private final InputStream stream(final String input) {
        return new ByteArrayInputStream(input.getBytes());
    }

    final CayTheManifestParser createManifestParser(final String input) throws IOException {
        return createManifestParser(stream(input));
    }

    final CayTheManifestParser createManifestParser(final InputStream input) throws IOException {
        return parsers.newManifestParser(input);
    }

    ParseTree parseManifest(final String src) throws IOException {
        return parseManifest(new ByteArrayInputStream(src.getBytes()));
    }

    ParseTree parseManifest(final InputStream src) throws IOException {
        return createManifestParser(src).manifest();
    }

    final CayTheSourceParser createSourceParser(final String input) throws IOException {
        return createSourceParser(stream(input));
    }

    final CayTheSourceParser createSourceParser(final InputStream input) throws IOException {
        return parsers.newSourceParser(input);
    }

    ParseTree parseSource(final String src) throws IOException {
        return parseSource(new ByteArrayInputStream(src.getBytes()));
    }

    ParseTree parseSource(final InputStream src) throws IOException {
        return createSourceParser(src).type();
    }

}

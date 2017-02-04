package de.weltraumschaf.caythe.frontend;

import com.google.inject.Guice;
import com.google.inject.Injector;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

abstract class VisitorTestCase {
    final Injector injector = Guice.createInjector(new DependencyInjectionConfig());
    private final Parsers parsers = new Parsers(injector);

    final InputStream stream(final String input) {
        return new ByteArrayInputStream(input.getBytes());
    }

    final CayTheManifestParser manifestParser(final String input) throws IOException {
        return manifestParser(stream(input));
    }

    final CayTheManifestParser manifestParser(final InputStream input) throws IOException {
        return parsers.newManifestParser(input);
    }

    final CayTheSourceParser sourceParser(final String input) throws IOException {
        return sourceParser(stream(input));
    }

    final CayTheSourceParser sourceParser(final InputStream input) throws IOException {
        return parsers.newSourceParser(input);
    }

}

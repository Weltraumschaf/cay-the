package de.weltraumschaf.caythe.frontend;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.weltraumschaf.caythe.backend.CayTheManifestLexer;
import de.weltraumschaf.caythe.backend.CayTheManifestParser;
import de.weltraumschaf.caythe.backend.CayTheSourceParser;
import de.weltraumschaf.commons.validate.Validate;
import org.antlr.v4.runtime.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * Factory to create parsers.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class Parsers {

    private final Injector injector;

    public Parsers() {
        this(Guice.createInjector(new DependencyInjectionConfig()));
    }

    public Parsers(final Injector injector) {
        super();
        this.injector = Validate.notNull(injector, "injector");
    }

    /**
     * Creates a new parser instance for regular source files.
     *
     * @param src must not be {@code null}
     * @return never {@code null} always new instance
     * @throws IOException if the source can't be read
     */
    public de.weltraumschaf.caythe.backend.CayTheSourceParser newSourceParser(final InputStream src) throws IOException {
        Validate.notNull(src, "src");
        final Lexer lexer = new de.weltraumschaf.caythe.backend.CayTheSourceLexer(newCharStream(src));
        return addErrorListener(new CayTheSourceParser(newTokenStream(lexer)));
    }

    /**
     * Creates a new parser instance for manifest files.
     *
     * @param src must not be {@code null}
     * @return never {@code null} always new instance
     * @throws IOException if the source can't be read
     */
    public de.weltraumschaf.caythe.backend.CayTheManifestParser newManifestParser(final InputStream src) throws IOException {
        Validate.notNull(src, "src");
        final Lexer lexer = new CayTheManifestLexer(newCharStream(src));
        return addErrorListener(new CayTheManifestParser(newTokenStream(lexer)));
    }

    private CharStream newCharStream(final InputStream src) throws IOException {
        return new ANTLRInputStream(src);
    }

    private TokenStream newTokenStream(final Lexer lexer) {
        return new CommonTokenStream(lexer);
    }

    private <P extends Parser> P addErrorListener(final P parser) {
        parser.addErrorListener(injector.getInstance(ANTLRErrorListener.class));
        return parser;
    }
}

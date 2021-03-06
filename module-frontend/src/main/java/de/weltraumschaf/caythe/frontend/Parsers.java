package de.weltraumschaf.caythe.frontend;

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

    private boolean debug;

    public void debug(boolean debug) {
        this.debug = debug;
    }

    /**
     * Creates a new parser instance for regular source files.
     *
     * @param src must not be {@code null}
     * @return never {@code null} always new instance
     * @throws IOException if the source can't be read
     */
    public CayTheSourceParser newSourceParser(final InputStream src) throws IOException {
        Validate.notNull(src, "src");
        final Lexer lexer = new CayTheSourceLexer(newCharStream(src));
        return addErrorListener(new CayTheSourceParser(newTokenStream(lexer)));
    }

    /**
     * Creates a new parser instance for manifest files.
     *
     * @param src must not be {@code null}
     * @return never {@code null} always new instance
     * @throws IOException if the source can't be read
     */
    public CayTheManifestParser newManifestParser(final InputStream src) throws IOException {
        Validate.notNull(src, "src");
        final Lexer lexer = new CayTheManifestLexer(newCharStream(src));
        return addErrorListener(new CayTheManifestParser(newTokenStream(lexer)));
    }

    private CharStream newCharStream(final InputStream src) throws IOException {
        return CharStreams.fromStream(src);
    }

    private TokenStream newTokenStream(final Lexer lexer) {
        return new CommonTokenStream(lexer);
    }

    private <P extends Parser> P addErrorListener(final P parser) {
        final ErrorListener listener = new DefaultErrorListener();
        listener.debug(debug);
        parser.addErrorListener(listener);
        return parser;
    }
}

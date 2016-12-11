package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.commons.validate.Validate;
import org.antlr.v4.runtime.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * Factory to create parsers.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class Parsers {

    /**
     * Creates a new parser instance for regular source files.
     *
     * @param src must not be {@code null}
     * @return never {@code null} alsways new instance
     * @throws IOException if the source can't be read
     */
    public CayTheSourceParser newSourceParser(final InputStream src) throws IOException {
        Validate.notNull(src, "src");
        final CharStream input = new ANTLRInputStream(src);
        final Lexer lexer = new CayTheSourceLexer(input);
        final TokenStream tokens = new CommonTokenStream(lexer);

        return new CayTheSourceParser(tokens);
    }

    /**
     * Creates a new parser instance for manifest files.
     *
     * @param src must not be {@code null}
     * @return never {@code null} alsways new instance
     * @throws IOException if the source can't be read
     */
    public CayTheManifestParser newManifestParser(final InputStream src) throws IOException {
        Validate.notNull(src, "src");
        final CharStream input = new ANTLRInputStream(src);
        final Lexer lexer = null;//new CaytheManifestLexer(input);
        final TokenStream tokens = new CommonTokenStream(lexer);

        return new CayTheManifestParser(tokens);
    }
}

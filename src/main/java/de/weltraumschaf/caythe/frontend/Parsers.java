package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.commons.validate.Validate;
import java.io.IOException;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

/**
 * Factory to create a ready to use parser.
 *
 * @since 1.0.0
 */
public final class Parsers {

    /**
     * Expected encoding of the parsed files.
     */
    private static final String ENCODING = "UTF-8";

    /**
     * Creates a new parser.
     *
     * @param file must not be {@code null} or empty
     * @param debugEnabled if {@code true} the {@link BailErrorStrategy} is used
     * @return never {@code null} always new instance
     * @throws IOException if the given source file can't be read
     */
    public static CayTheParser newParser(final String file, final boolean debugEnabled) throws IOException {
        final CharStream input = new ANTLRFileStream(Validate.notEmpty(file, "file"), ENCODING);
        final CayTheLexer lexer = new CayTheLexer(input);
        final TokenStream tokens = new CommonTokenStream(lexer);
        final CayTheParser parser = new CayTheParser(tokens);

        if (debugEnabled) {
            parser.setErrorHandler(new BailErrorStrategy());
        }

        return parser;
    }
}

package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.caythe.CayThe;
import de.weltraumschaf.commons.application.IO;
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
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class Parsers {

    /**
     * For the {@link ErrorListener}.
     */
    private final IO io;

    /**
     * Dedicated constructor.
     *
     * @param io must not be {@code null}
     */
    public Parsers(final IO io) {
        super();
        this.io = Validate.notNull(io, "io");
    }

    /**
     * Creates a new parser.
     *
     * @param file must not be {@code null} or empty
     * @param debugEnabled if {@code true} the {@link BailErrorStrategy} is used
     * @return never {@code null} always new instance
     * @throws IOException if the given source file can't be read
     */
    public CayTheParser newParser(final String file, final boolean debugEnabled) throws IOException {
        final CharStream input = new ANTLRFileStream(Validate.notEmpty(file, "file"), CayThe.DEFAULT_ENCODING);
        final CayTheLexer lexer = new CayTheLexer(input);
        final TokenStream tokens = new CommonTokenStream(lexer);
        final CayTheParser parser = new CayTheParser(tokens);

        parser.removeErrorListeners();
        parser.addErrorListener(new ErrorListener(io, debugEnabled));
        parser.setErrorHandler(new BailErrorStrategy());

        return parser;
    }
}

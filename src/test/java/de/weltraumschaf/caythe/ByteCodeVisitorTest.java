package de.weltraumschaf.caythe;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

/**
 */
public class ByteCodeVisitorTest {

    private static final String ENCODING = "UTF-8";
    private final ByteCodeVisitor sut = new ByteCodeVisitor();

    private ParseTree createParseTree(final String filename) throws IOException, URISyntaxException {
        final URL source = getClass().getResource("/examples/" + filename);
        final File file = new File(source.toURI());
        final CharStream input = new ANTLRFileStream(file.getAbsolutePath(), ENCODING);
        final CayTheLexer lexer = new CayTheLexer(input);
        final TokenStream tokens = new CommonTokenStream(lexer);
        final CayTheParser parser = new CayTheParser(tokens);
        parser.setErrorHandler(new BailErrorStrategy());
        return parser.compilationUnit();
    }

    @Test
    public void number1() throws IOException, URISyntaxException {
        sut.visit(createParseTree("number1.ct"));
    }

}

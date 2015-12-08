package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.frontend.Parsers;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import de.weltraumschaf.caythe.frontend.CayTheParser;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;

/**
 */
public class InterpreterTest {

    private final BufferEnvironment env = new BufferEnvironment();
    private final Interpreter sut = new Interpreter(env);

    private ParseTree createParseTree(final String filename) throws IOException, URISyntaxException {
        final URL source = getClass().getResource("/examples/" + filename);
        final File file = new File(source.toURI());
        final CayTheParser parser = Parsers.newParser(file.getAbsolutePath(), true);
        return parser.compilationUnit();
    }

    @Test
    @Ignore
    public void number1() throws IOException, URISyntaxException {
        sut.visit(createParseTree("number1.ct"));
    }

    @Test
    public void print() throws IOException, URISyntaxException {
        sut.visit(createParseTree("print.ct"));

        assertThat(env.getOut(), is("42\\nhello world"));
    }
}

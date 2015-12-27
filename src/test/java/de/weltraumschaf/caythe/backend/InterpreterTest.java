package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.backend.env.BufferEnvironment;
import de.weltraumschaf.caythe.backend.interpreter.Interpreter;
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
    public void print() throws IOException, URISyntaxException {
        sut.visit(createParseTree("print.ct"));

        assertThat(env.getOut(), is("42\\nhello world"));
    }

    @Test
    public void ifElse() throws IOException, URISyntaxException {
        sut.visit(createParseTree("if.ct"));

        assertThat(env.getOut(), is("3"));
    }

    @Test
    public void whileLoop() throws IOException, URISyntaxException {
        sut.visit(createParseTree("while.ct"));

        assertThat(env.getOut(), is("0123456789"));
    }

    @Test
    @Ignore
    public void complex() throws IOException, URISyntaxException {
        sut.visit(createParseTree("complex.ct"));

        assertThat(env.getOut(), is("712.560foo1baz2baz3baz4baz5snafu6snafu7snafu8snafu9snafudone"));
    }
}

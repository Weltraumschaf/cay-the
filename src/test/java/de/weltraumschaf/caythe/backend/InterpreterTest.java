package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.backend.env.BufferedEnvironment;
import de.weltraumschaf.caythe.backend.interpreter.Interpreter;
import de.weltraumschaf.caythe.backend.symtab.BuildInTypeSymbol;
import de.weltraumschaf.caythe.backend.symtab.ConstantSymbol;
import de.weltraumschaf.caythe.backend.symtab.FunctionSymbol;
import de.weltraumschaf.caythe.backend.symtab.Scope;
import de.weltraumschaf.caythe.backend.symtab.SymbolTable;
import de.weltraumschaf.caythe.frontend.Parsers;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import de.weltraumschaf.caythe.frontend.CayTheParser;
import de.weltraumschaf.caythe.log.Logging;
import de.weltraumschaf.commons.application.IOStreams;
import java.util.Arrays;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link Interpreter}.
 */
public class InterpreterTest {

    private final BufferedEnvironment env = new BufferedEnvironment();
    private final SymbolTable table = SymbolTable.newTable();
    private final Interpreter sut = new Interpreter(env, table, Logging.newNull());

    private ParseTree createParseTree(final String filename) throws IOException, URISyntaxException {
        final URL source = getClass().getResource("/examples/" + filename);
        final File file = new File(source.toURI());
        final Parsers parsers = new Parsers(IOStreams.newDefault());
        final CayTheParser parser = parsers.newParser(file.getAbsolutePath(), true);
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
    public void complex() throws IOException, URISyntaxException {
        sut.visit(createParseTree("complex.ct"));

        assertThat(env.getOut(), is("712.560foo1baz2baz3baz4baz5snafu6snafu7snafu8snafu9snafudone"));
    }

    @Test
    public void functionDecls() throws IOException, URISyntaxException {
        sut.visit(createParseTree("functionDecls.ct"));
        final Scope globals = table.globalScope();

        assertThat(globals.isFunctionDefined("one"), is(true));
        assertThat(globals.resolveFunction("one"),
            is(new FunctionSymbol(
                    "one",
                    FunctionSymbol.VOID,
                    FunctionSymbol.NOARGS,
                    globals)));
        assertThat(globals.isFunctionDefined("two"), is(true));
        assertThat(globals.resolveFunction("two"),
            is(new FunctionSymbol(
                    "two",
                    Arrays.asList(BuildInTypeSymbol.INT),
                    FunctionSymbol.NOARGS,
                    globals)));
        assertThat(globals.isFunctionDefined("three"), is(true));
        assertThat(globals.resolveFunction("three"),
            is(new FunctionSymbol(
                    "three",
                    Arrays.asList(BuildInTypeSymbol.INT, BuildInTypeSymbol.FLOAT),
                    FunctionSymbol.NOARGS,
                    globals)));
        assertThat(globals.isFunctionDefined("four"), is(true));
        assertThat(globals.resolveFunction("four"),
            is(new FunctionSymbol(
                    "four",
                    FunctionSymbol.VOID,
                    Arrays.asList(new ConstantSymbol("a", BuildInTypeSymbol.INT)),
                    globals)));
        assertThat(globals.isFunctionDefined("five"), is(true));
        assertThat(globals.resolveFunction("five"),
            is(new FunctionSymbol(
                    "five",
                    FunctionSymbol.VOID,
                    Arrays.asList(
                        new ConstantSymbol("a", BuildInTypeSymbol.INT),
                        new ConstantSymbol("b", BuildInTypeSymbol.FLOAT)
                    ),
                    globals)));
        assertThat(globals.isFunctionDefined("six"), is(true));
        assertThat(globals.resolveFunction("six"),
            is(new FunctionSymbol(
                    "six",
                    Arrays.asList(BuildInTypeSymbol.INT),
                    Arrays.asList(new ConstantSymbol("a", BuildInTypeSymbol.INT)),
                    globals)));
        assertThat(globals.resolveFunction("seven"),
            is(new FunctionSymbol(
                    "seven",
                    Arrays.asList(BuildInTypeSymbol.INT, BuildInTypeSymbol.INT),
                    Arrays.asList(
                        new ConstantSymbol("a", BuildInTypeSymbol.INT),
                        new ConstantSymbol("b", BuildInTypeSymbol.FLOAT)
                    ),
                    globals)));
        assertThat(env.getOut(), is(""));
    }

    @Test
    public void functionCalls() throws IOException, URISyntaxException {
        sut.visit(createParseTree("functionCalls.ct"));

        assertThat(env.getOut(), is(String.format(
            "functiondecl:%n"
            + "decl end.%n"
            + "foo%n"
            + "bar: snafu%n"
            + "65%n")));
    }
}

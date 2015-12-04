package de.weltraumschaf.caythe;

import de.weltraumschaf.commons.application.InvokableAdapter;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

/**
 */
public final class CliApplication extends InvokableAdapter {

    private static final String ENCODING = "UTF-8";
    private boolean echoDebug = true;

    public CliApplication(final String[] args) {
        super(args);
    }

    @Override
    public void execute() throws Exception {
        final String filename = "";
        final CharStream input = new ANTLRFileStream(filename, ENCODING);
        final CayTheLexer lexer = new CayTheLexer(input);
        final TokenStream tokens = new CommonTokenStream(lexer);
        final CayTheParser parser = new CayTheParser(tokens);

        if (echoDebug) {
            parser.setErrorHandler(new BailErrorStrategy());
        }

        final ByteCodeVisitor visitor = new ByteCodeVisitor();
        final Programm programm = visitor.visit(parser.equation());
        final VirtualMachine vm = new VirtualMachine(programm);
        vm.run();
    }

}

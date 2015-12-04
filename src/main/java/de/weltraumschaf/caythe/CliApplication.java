package de.weltraumschaf.caythe;

import de.weltraumschaf.commons.application.InvokableAdapter;
import de.weltraumschaf.commons.application.Version;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

/**
 */
public final class CliApplication extends InvokableAdapter {

    private static final String ENCODING = "UTF-8";
    /**
     * Version information.
     */
    private final Version version;
    private boolean echoDebug = true;

    public CliApplication(final String[] args) {
        super(args);
        version = new Version("/de/weltraumschaf/caythe/version.properties");
    }

    public static void main(final String[] args) {
        InvokableAdapter.main(new CliApplication(args));
    }

    @Override
    public void execute() throws Exception {
        version.load();
        final CliOptions options = CliOptions.gatherOptions(getArgs());

        if (options.isVersion()) {
            showVersion();
            return;
        }

        if (options.isHelp()) {
            showHelp();
            return;
        }

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

    /**
     * Show help message.
     */
    private void showHelp() {
        getIoStreams().println(CliOptions.helpMessage());
    }

    /**
     * Show version message.
     */
    private void showVersion() {
        getIoStreams().println(version.getVersion());
    }
}

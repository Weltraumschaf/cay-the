package de.weltraumschaf.caythe.cli;

import de.weltraumschaf.caythe.CayThe;
import de.weltraumschaf.caythe.backend.interpreter.Interpreter;
import de.weltraumschaf.caythe.backend.DefaultEnvironmnet;
import de.weltraumschaf.caythe.frontend.CayTheBaseVisitor;
import de.weltraumschaf.caythe.frontend.CayTheParser;
import de.weltraumschaf.caythe.frontend.Parsers;
import de.weltraumschaf.commons.application.InvokableAdapter;
import de.weltraumschaf.commons.application.Version;

/**
 * This class provides the {@link #main(java.lang.String[]) main entry point} for the command line application.
 *
 * @since 1.0.0
 */
public final class CliApplication extends InvokableAdapter {

    /**
     * Version information.
     */
    private final Version version;

    public CliApplication(final String[] args) {
        super(args);
        version = new Version(CayThe.BASE_PACKAGE_DIR  + "/version.properties");
    }

    public static void main(final String[] args) {
        final CliApplication app = new CliApplication(args);
        app.debug = Boolean.valueOf(System.getenv(CayThe.ENV_DEBUG));
        InvokableAdapter.main(app);
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

        final CayTheParser parser = Parsers.newParser(options.getFile(), debug);
        final CayTheBaseVisitor<?> visitor;

        if (options.isInterpret()) {
            visitor = new Interpreter(new DefaultEnvironmnet(getIoStreams()));
        } else {
            throw new UnsupportedOperationException("Not implemented yet!");
        }

        visitor.visit(parser.compilationUnit());
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

package de.weltraumschaf.caythe.cli;

import de.weltraumschaf.caythe.CayThe;
import de.weltraumschaf.caythe.backend.KernelApi;
import de.weltraumschaf.caythe.backend.interpreter.Interpreter;
import de.weltraumschaf.caythe.backend.env.DefaultEnvironmnet;
import de.weltraumschaf.caythe.frontend.CayTheBaseVisitor;
import de.weltraumschaf.caythe.frontend.CayTheParser;
import de.weltraumschaf.caythe.frontend.Parsers;
import de.weltraumschaf.caythe.log.Logging;
import de.weltraumschaf.commons.application.InvokableAdapter;
import de.weltraumschaf.commons.application.Version;
import java.io.IOException;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.misc.ParseCancellationException;

/**
 * This class provides the {@link #main(java.lang.String[]) main entry point} for the command line application.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class CliApplication extends InvokableAdapter {

    /**
     * Version information.
     */
    private final Version version;

    /**
     * Dedicated constructor.
     *
     * @param args must not be {@code null}
     */
    public CliApplication(final String[] args) {
        super(args);
        version = new Version(CayThe.BASE_PACKAGE_DIR + "/version.properties");
    }

    /**
     * Invoked from JVM.
     *
     * @param args CLI arguments
     */
    public static void main(final String[] args) {
        final CliApplication app = new CliApplication(args);
        app.debug = Boolean.valueOf(System.getenv(CayThe.ENV_DEBUG));
        InvokableAdapter.main(app);
    }

    /**
     * Do initial stuff.
     *
     * @return never {@code null}
     * @throws IOException if version file can not be loaded
     */
    private CliOptions setup() throws IOException {
        Logging.setStdout(getIoStreams().getStdout());
        version.load();
        return CliOptions.gatherOptions(getArgs());
    }

    @Override
    public void execute() throws Exception {
        final CliOptions options = setup();

        if (options.isVersion()) {
            showVersion();
            return;
        }

        if (options.isHelp()) {
            showHelp();
            return;
        }

        final CayTheBaseVisitor<?> visitor;

        if (options.isInterpret()) {
            if (isDebugEnabled()) {
                visitor = new Interpreter(new DefaultEnvironmnet(getIoStreams()), Logging.newSysOut());
            } else {
                visitor = new Interpreter(new DefaultEnvironmnet(getIoStreams()));
            }
        } else {
            throw new UnsupportedOperationException("Not implemented yet!");
        }

        parse(options.getFile(), visitor);
    }

    /**
     * Parse and visit the source code.
     *
     * @param filename must not be {@code null} or empty
     * @param visitor must not be {@code null}
     * @throws IOException if source file can't be read
     */
    private void parse(final String filename, final CayTheBaseVisitor<?> visitor) throws IOException {
        final CayTheParser parser = Parsers.newParser(filename, isDebugEnabled());

        try {
            visitor.visit(parser.compilationUnit());
        } catch (final KernelApi.ExitException ex) {
            exit(ex.getCode());
        } catch (final ParseCancellationException ex) {
            getIoStreams().errorln(
                String.format("Parsing cancelded with message: %s%n at line %d col %d: %s",
                    ex.getMessage(),
                    parser.getCurrentToken().getLine(),
                    parser.getCurrentToken().getCharPositionInLine(),
                    parser.getCurrentToken().getText()));

            if (isDebugEnabled()) {
                getIoStreams().printStackTrace(ex);
            }
        }
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

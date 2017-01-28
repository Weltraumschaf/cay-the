package de.weltraumschaf.caythe.cli;

import com.beust.jcommander.ParameterException;
import de.weltraumschaf.caythe.CayThe;
import de.weltraumschaf.commons.application.ApplicationException;
import de.weltraumschaf.commons.application.InvokableAdapter;
import de.weltraumschaf.commons.application.Version;
import de.weltraumschaf.commons.validate.Validate;

import java.io.IOException;

/**
 * This class provides the {@link #main(java.lang.String[]) main entry point} for the command line application.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class CliApplication extends InvokableAdapter {

    /**
     * Version information.
     */
    private final Version version = new Version(CayThe.BASE_PACKAGE_DIR + "/version.properties");
    /**
     * Command line arguments.
     */
    private final CliOptions options = new CliOptions();
    /**
     * Provides sub commands.
     */
    private final SubCommandFactory subCommands = new DefaultSubCommandFactory();

    /**
     * Dedicated constructor.
     *
     * @param args must not be {@code null}
     */
    public CliApplication(final String[] args) {
        super(args);
    }

    /**
     * Invoked from JVM.
     *
     * @param args CLI arguments
     */
    public static void main(final String[] args) {
        final CliApplication app = new CliApplication(args);
        InvokableAdapter.main(app);
    }

    /**
     * Do initial stuff.
     *
     * @return never {@code null}
     * @throws ApplicationException if CLI arguments could not be parsed, or version information could not be loaded
     */
    private SubCommandName setup() throws ApplicationException {
        try {
            options.parse(getArgs());
        } catch (final ParameterException ex) {
            throw badArgumentError(ex, options.getParsedCommand());
        }

        debug = options.isDebug();

        try {
            version.load();
        } catch (final IOException ex) {
            throw new ApplicationException(ExitCodeImpl.FATAL, errorMessage("Can not read version information!", SubCommandName.NONE), ex);
        }

        return options.getParsedCommand();
    }

    @Override
    public void execute() throws Exception {
        final SubCommandName commandName = setup();

        try {
            if (SubCommandName.NONE == commandName) {
                executeMainCommand();
            } else {
                executeSubCommand(commandName);
            }
        } catch (final RuntimeException e) {
            getIoStreams().errorln(e.getMessage());

            if (isDebugEnabled()) {
                getIoStreams().printStackTrace(e);
            }

            exit(ExitCodeImpl.FATAL);
        }
    }

    /**
     * Executes the main application code here.
     *
     * @throws ApplicationException on any application error such as bad arguments
     */
    private void executeMainCommand() throws ApplicationException {
        if (options.isHelp()) {
            showHelp();
            return;
        }

        if (options.getMain().isVersion()) {
            showVersion();
            return;
        }

        throw badArgumentError();
    }

    /**
     * Executes a sub command named by fist CLI argument.
     *
     * @param commandName must not be {@code null}
     * @throws Exception if anything went wrong
     */
    private void executeSubCommand(final SubCommandName commandName) throws Exception {
        Validate.notNull(commandName, "commandName");

        if (options.isHelp()) {
            showHelp(commandName);
            return;
        }

        subCommands.forName(commandName, new CliContext(getIoStreams(), options, version)).execute();
    }


    /**
     * Throw a generic bad CLI argument error.
     *
     * @throws ApplicationException always
     */
    private ApplicationException badArgumentError() throws ApplicationException {
        return badArgumentError("Bad arguments!", SubCommandName.NONE);
    }

    /**
     * Throw bad CLI argument error with custom message.
     *
     * @param messageFormat additional error message
     */
    private ApplicationException badArgumentError(final String messageFormat, final SubCommandName name, final Object... args) {
        return badArgumentError(messageFormat, null, name, args);
    }

    /**
     * Throw a generic bad CLI argument error with exception for debug output the stack trace.
     *
     * @param ex may be {@code null}
     */
    private ApplicationException badArgumentError(final ParameterException ex, final SubCommandName name) {
        return badArgumentError("Bad arguments (cause: %s)!", ex, name, ex.getMessage());
    }

    /**
     * Throw bad CLI argument error with custom message with exception for debug output the stack trace.
     *
     * @param messageFormat additional error message
     * @param ex            may be {@code null}
     */
    private ApplicationException badArgumentError(final String messageFormat, final ParameterException ex, final SubCommandName name, final Object... args) {
        return new ApplicationException(ExitCodeImpl.BAD_ARGUMENT, errorMessage(messageFormat, name, args), ex);
    }

    /**
     * Appends usage to given message.
     *
     * @param messageFormat must not be {@code null} or empty
     * @param name          must not be {@code null}
     * @param args          optional arguments for the message format string.
     * @return never {@code null} or empty
     */
    @SuppressWarnings("StringBufferReplaceableByString")
    private String errorMessage(final String messageFormat, final SubCommandName name, final Object... args) {
        return new StringBuilder()
            .append(String.format(Validate.notEmpty(messageFormat, "messageFormat"), args))
            .append(CayThe.NL)
            .append("Usage: ")
            .append(' ')
            .append(options.usage(name))
            .toString();
    }

    /**
     * Show help message.
     */
    private void showHelp() {
        showHelp(SubCommandName.NONE);
    }

    private void showHelp(final SubCommandName name) {
        getIoStreams().println(options.help(name));
    }

    /**
     * Show version message.
     */
    private void showVersion() {
        getIoStreams().println(version.getVersion());
    }
}

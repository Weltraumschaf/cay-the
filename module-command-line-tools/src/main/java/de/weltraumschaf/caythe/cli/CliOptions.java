package de.weltraumschaf.caythe.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterDescription;
import de.weltraumschaf.caythe.CayThe;
import de.weltraumschaf.caythe.cli.create.CreateCliOptions;
import de.weltraumschaf.caythe.cli.repl.ReplCliOptions;
import de.weltraumschaf.caythe.cli.repl.ReplSubCommand;
import de.weltraumschaf.caythe.cli.run.RunCliOptions;
import de.weltraumschaf.commons.jcommander.JCommanderImproved;
import de.weltraumschaf.commons.validate.Validate;

import java.util.List;

/**
 * Facade to get CLI options.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class CliOptions {
    /**
     * Holds the argument parser.
     */
    private JCommander parser;
    /**
     * Holds the main program options.
     */
    private MainCliOptions main;
    /**
     * Holds the create command options.
     */
    private CreateCliOptions create;
    private ReplCliOptions repl;
    private RunCliOptions run;

    /**
     * Dedicated constructor.
     */
    CliOptions() {
        super();
        reset();
    }

    /**
     * Get the main program options.
     *
     * @return never {@code null}
     */
    public MainCliOptions getMain() {
        return main;
    }

    /**
     * Get the create command options.
     *
     * @return never {@code null}
     */
    public CreateCliOptions getCreate() {
        return create;
    }

    public ReplCliOptions getRepl() {
        return repl;
    }

    /**
     * Parse the CLI arguments.
     * <p>
     * This method {@link #reset() reset} the instance.
     * </p>
     *
     * @param args must not be {@code null}
     */
    void parse(final String... args) {
        reset();
        parser.parse(Validate.notNull(args, "args"));
    }

    /**
     * Determine which command was parsed.
     *
     * @return never {@code null}
     */
    SubCommandName getParsedCommand() {
        final String parsedCommand = parser.getParsedCommand();

        if (null == parsedCommand) {
            return SubCommandName.NONE;
        }

        return SubCommandName.valueOf(parsedCommand.toUpperCase());
    }

    /**
     * Get the main usage.
     * <p>
     * Shorthand for {@link #usage(SubCommandName)} with {@link SubCommandName#NONE}.
     * </p>
     *
     * @return never {@code null} or empty
     */
    String usage() {
        return usage(SubCommandName.NONE);
    }

    /**
     * Get usage for command.
     *
     * @param cmd may be {@code null}
     * @return never {@code null} or empty
     */
    String usage(final SubCommandName cmd) {
        String usage = "";

        switch (cmd) {
            case CREATE:
                usage += CreateCliOptions.usage();
                break;
            case REPL:
                usage += ReplCliOptions.usage();
                break;
            case RUN:
                usage += RunCliOptions.usage();
            case NONE:
            default:
                usage += MainCliOptions.usage();
                break;
        }

        return usage;
    }

    /**
     * Get the main help.
     * <p>
     * Shorthand for {@link #help(SubCommandName)} with {@link SubCommandName#NONE}.
     * </p>
     *
     * @return never {@code null} or empty
     */
    String help() {
        return help(SubCommandName.NONE);
    }

    /**
     * Get help for command.
     *
     * @param cmd may be {@code null}
     * @return never {@code null} or empty
     */
    String help(final SubCommandName cmd) {
        final String description;
        final List<ParameterDescription> parameters;

        if (SubCommandName.NONE == cmd) {
            description = MainCliOptions.DESCRIPTION;
            parameters = parser.getParameters();
        } else {
            description = parser.getCommandDescription(cmd.toString());
            parameters = parser.getCommands().get(cmd.toString()).getParameters();
        }

        final String usage = usage(cmd);
        final String example;

        switch (cmd) {
            default:
                example = MainCliOptions.EXAMPLE;
                break;
        }

        return JCommanderImproved.helpMessage(
            usage,
            description,
            example,
            CayThe.COMMAND_NAME,
            parameters);
    }

    /**
     * Resets the instance because it holds state from parsing.
     */
    private void reset() {
        main = new MainCliOptions();
        parser = new JCommander(main);
        create = new CreateCliOptions();
        parser.addCommand(SubCommandName.CREATE.toString(), create);
        repl = new ReplCliOptions();
        parser.addCommand(SubCommandName.REPL.toString(), repl);
        run = new RunCliOptions();
        parser.addCommand(SubCommandName.RUN.toString(), run);
    }

    boolean isHelp() {
        return main.isHelp() || create.isHelp() || repl.isHelp() || run.isHelp();
    }

    boolean isDebug() {
        return main.isDebug() || create.isDebug() || repl.isDebug() || run.isDebug();
    }

    boolean isVerbose() {
        return false;//create.isVerbose() || repl.isVerbose() || run.isVerbose()
    }
}

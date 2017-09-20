package de.weltraumschaf.caythe.cli;

/**
 * Available sub commands.
 * <p>
 * The CLI application follows the so called sub command pattern. This means that the first string after the command
 * is denoted as the name of a sub command to execute. A well known CLI appication which follows the same pattern is git.
 * </p>
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public enum SubCommandName {
    /**
     * Dummy for no command, but main program.
     */
    NONE,
    /**
     * Subcommand to create the scaffold for a new module.
     */
    CREATE,
    /**
     * Interprets the given sources.
     */
    RUN,
    /**
     * Starts a REPL.
     */
    REPL;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}

package de.weltraumschaf.caythe.cli;

/**
 * Implementations are a sub command of the main application.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public interface SubCommand {
    /**
     * Executes the sub command.
     *
     * @throws Exception on any error during execution
     */
    void execute() throws Exception;
}

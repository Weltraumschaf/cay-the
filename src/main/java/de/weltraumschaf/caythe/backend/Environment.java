package de.weltraumschaf.caythe.backend;

/**
 * Encapsulates the environment in which a program runs.
 *
 * @since 1.0.0
 */
public interface Environment {

    /**
     * Put string on STDOUT.
     *
     * @param output must not be {@code null}
     */
    void stdOut(String output);
    /**
     * Put string on STDERR.
     *
     * @param output must not be {@code null}
     */
    void stdErr(String output);
    /**
     * Read string from STDIN.
     *
     * @return never {@code null}
     */
    String stdIn();
}

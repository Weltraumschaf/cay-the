package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.CayThe;
import de.weltraumschaf.caythe.backend.env.Environment;
import de.weltraumschaf.commons.validate.Validate;

/**
 * Provides API for build in functions.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class KernelApi {

    /**
     * Used for I/O.
     */
    private final Environment env;

    /**
     * Dedicated constructor.
     *
     * @param env must not be {@code null}
     */
    public KernelApi(final Environment env) {
        super();
        this.env = Validate.notNull(env, "env");
    }

    /**
     * Prints the given string to standard out.
     *
     * @param output any string
     */
    public void print(final String output) {
        env.stdOut(output);
    }

    /**
     * Prints the given string to standard out with an appended new line.
     *
     * @param output any string
     */
    public void println(final String output) {
        print(output + CayThe.DEFAULT_NEWLINE);
    }

    /**
     * Prints the given string to standard error.
     *
     * @param output any string
     */
    public void error(final String output) {
        env.stdErr(output);
    }

    /**
     * Prints the given string to standard error with an appended new line.
     *
     * @param output any string
     */
    public void errorln(final String output) {
        error(output + CayThe.DEFAULT_NEWLINE);
    }


    /**
     * Exits the execution of the program immediately.
     * <p>
     * The program abortion is done by throwing always an {@link ExitException}.
     * </p>
     *
     * @param code 0 for OK, anything else for errors
     */
    public void exit(final int code) {
        throw new ExitException(code);
    }

    /**
     * Used to signal {@link KernelApi#exit(int)}.
     */
    public static final class ExitException extends RuntimeException {
        /**
         * The exit code.
         */
        private final int code;

        /**
         * Dedicated constructor.
         *
         * @param code 0 for OK, anything else for errors
         */
        public ExitException(int code) {
            super();
            this.code = code;
        }

        /**
         * Get the exit code.
         *
         * @return any integer
         */
        public int getCode() {
            return code;
        }

    }
}


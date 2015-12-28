package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.backend.env.Environment;
import de.weltraumschaf.commons.validate.Validate;

/**
 * Provides API for build in functions.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class KernelApi {

    private static final String NL = String.format("%n");
    private final Environment env;

    public KernelApi(final Environment env) {
        super();
        this.env = Validate.notNull(env, "env");
    }

    public void print(final String output) {
        env.stdOut(output);
    }

    public void println(final String output) {
        print(output + NL);
    }

    public void error(final String output) {
        env.stdErr(output);
    }

    public void errorln(final String output) {
        error(output + NL);
    }
}

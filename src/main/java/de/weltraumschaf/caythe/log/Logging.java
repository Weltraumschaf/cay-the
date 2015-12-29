package de.weltraumschaf.caythe.log;

import de.weltraumschaf.commons.validate.Validate;
import java.io.PrintStream;

/**
 * Factory to create {@link Logger loggers}.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class Logging {

    /**
     * Dependency for {@link #newSysOut() sys out logger}.
     */
    private static PrintStream stdout = System.out;

    /**
     * Not visible for pure static factory.
     */
    private Logging() {
        super();
    }

    /**
     * For injecting an other print stream for sys out logger.
     *
     * @param stdout must not be {@code null}
     */
    public static void setStdout(final PrintStream stdout) {
        Logging.stdout = Validate.notNull(stdout, "stdout");
    }

    /**
     * Creates a logger which prints to system out.
     *
     * @return never {@code null}, always new instance
     */
    public static Logger newSysOut() {
        return new SysOutLogger(stdout);
    }

    /**
     * Creates a logger which buffers everything.
     *
     * @return never {@code null}, always new instance
     */
    public static Logger newBuffered() {
        return new BufferedLogger();
    }

    /**
     * A logger which ignores everything.
     *
     * @return never {@code null}, always same instance
     */
    public static Logger newNull() {
        return Logger.NULL;
    }
}

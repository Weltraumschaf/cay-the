package de.weltraumschaf.caythe.log;

import de.weltraumschaf.commons.validate.Validate;
import java.io.PrintStream;

/**
 * Factory to create {@link Logger loggers}.
 *
 * @since 1.0.0
 */
public final class Logging {

    private static PrintStream stdout = System.out;

    private Logging() {
        super();
    }

    public static void setStdout(final PrintStream stdout) {
        Logging.stdout = Validate.notNull(stdout, "stdout");
    }

    public static Logger newSysOut() {
        return new SysOutLogger(stdout);
    }

    public static Logger newBuffered() {
        return new BufferedLogger();
    }
}

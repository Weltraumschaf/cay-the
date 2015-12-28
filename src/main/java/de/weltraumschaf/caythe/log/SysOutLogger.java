package de.weltraumschaf.caythe.log;

import de.weltraumschaf.caythe.CayThe;
import de.weltraumschaf.commons.validate.Validate;
import java.io.PrintStream;

/**
 * Logs everything to {@link System.out}.
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
final class SysOutLogger extends BaseLogger implements Logger {

    private final PrintStream out;

    SysOutLogger(final PrintStream out) {
        super();
        this.out = Validate.notNull(out, "out");
    }

    @Override
    public void log(final String msg, final Object... args) {
        out.print('[');
        out.print(CayThe.CMD_NAME);
        out.print(']');
        out.print(' ');
        out.println(format(msg, args));
    }

}

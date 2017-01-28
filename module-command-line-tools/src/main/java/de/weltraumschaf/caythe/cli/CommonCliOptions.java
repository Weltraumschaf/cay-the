package de.weltraumschaf.caythe.cli;

import com.beust.jcommander.Parameter;
import de.weltraumschaf.caythe.cli.helper.UsageBuilder;

/**
 * General available options.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public abstract class CommonCliOptions {
    @SuppressWarnings( {"CanBeFinal", "unused"})
    @Parameter(names = {"-h", "--help"}, description = "Show help.", help = true)
    private boolean help;

    @SuppressWarnings( {"CanBeFinal", "unused"})
    @Parameter(names = {"--debug"}, description = "Print debug output such as stack traces to STDOUT.")
    private boolean debug;

    public final boolean isHelp() {
        return help;
    }

    public boolean isDebug() {
        return debug;
    }

    @SuppressWarnings("SameReturnValue")
    public static String usage() {
        return UsageBuilder.generate(CommonCliOptions.class);
    }
}

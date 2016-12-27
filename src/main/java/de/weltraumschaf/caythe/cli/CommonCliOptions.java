package de.weltraumschaf.caythe.cli;

import com.beust.jcommander.Parameter;

/**
 * General available options.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
abstract class CommonCliOptions {
    @SuppressWarnings( {"CanBeFinal", "unused"})
    @Parameter(names = {"-h", "--help"}, description = "Show help.", help = true)
    private boolean help;

    @SuppressWarnings( {"CanBeFinal", "unused"})
    @Parameter(names = {"-d", "--debug"}, description = "Print debug output such as stack traces to STDOUT.")
    private boolean debug;

    final boolean isHelp() {
        return help;
    }

    boolean isDebug() {
        return debug;
    }

    @SuppressWarnings("SameReturnValue")
    static String usage() {
        return UsageBuilder.generate(CommonCliOptions.class);
    }
}

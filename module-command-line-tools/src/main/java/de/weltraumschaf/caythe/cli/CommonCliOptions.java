package de.weltraumschaf.caythe.cli;

import com.beust.jcommander.Parameter;
import de.weltraumschaf.caythe.cli.helper.UsageBuilder;
import lombok.Getter;

/**
 * General available options.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public abstract class CommonCliOptions {
    @Getter
    @SuppressWarnings( {"CanBeFinal", "unused"})
    @Parameter(names = {"-h", "--help"}, description = "Show help.", help = true)
    private boolean help;

    @Getter
    @SuppressWarnings( {"CanBeFinal", "unused"})
    @Parameter(names = {"--debug"}, description = "Print debug output such as stack traces to STDOUT.")
    private boolean debug;

    @SuppressWarnings("SameReturnValue")
    public static String usage() {
        return UsageBuilder.generate(CommonCliOptions.class);
    }
}

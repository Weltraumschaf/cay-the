package de.weltraumschaf.caythe.cli;

import com.beust.jcommander.Parameter;
import de.weltraumschaf.caythe.cli.create.CreateCliOptions;
import de.weltraumschaf.caythe.cli.helper.ExampleBuilder;
import de.weltraumschaf.caythe.cli.helper.UsageBuilder;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * CliOptions for the main command.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
final class MainCliOptions extends CommonCliOptions {
    static final String DESCRIPTION = "This is the command line tool suite for Cay-The.";
    static final String EXAMPLE = ExampleBuilder.crete()
        .text(CreateCliOptions.EXAMPLE)
        .toString();

    @SuppressWarnings( {"CanBeFinal", "unused"})
    @Parameter(names = {"--version"}, description = "Show version.")
    private boolean version;

    public boolean isVersion() {
        return version;
    }

    static String delimitedSubcommandNames() {
        return Arrays.stream(SubCommandName.values())
            .filter(item -> item != SubCommandName.NONE)
            .map(SubCommandName::toString)
            .collect(Collectors.joining("|"));

    }

    @SuppressWarnings("StringBufferReplaceableByString")
    public static String usage() {
        return new StringBuilder()
            .append(delimitedSubcommandNames())
            .append(UsageBuilder.SEPARATOR)
            .append(UsageBuilder.generate(MainCliOptions.class))
            .append(UsageBuilder.SEPARATOR)
            .append(CommonCliOptions.usage())
            .toString();
    }
}

package de.weltraumschaf.caythe.cli;

import com.beust.jcommander.Parameter;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Options for the main command.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
final class MainOptions extends CommonOptions {
    static final String DESCRIPTION = "TODO";
    static final String EXAMPLE = "TODO Add some examples.";

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
            .append(' ')
            .append("[--version]")
            .append(' ')
            .append(CommonOptions.usage())
            .toString();
    }
}

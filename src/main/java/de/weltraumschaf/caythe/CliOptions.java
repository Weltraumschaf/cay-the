package de.weltraumschaf.caythe;

import com.beust.jcommander.Parameter;
import de.weltraumschaf.commons.jcommander.JCommanderImproved;
import de.weltraumschaf.commons.validate.Validate;
import java.util.Objects;

/**
 */
public final class CliOptions {

    /**
     * New line string.
     */
    private static final String NL = String.format("%n");
    private static final String CMD_NAME = "cay-the";

    /**
     * Command line usage.
     */
    private static final String USAGE
        = " [-h] [-v]";
    /**
     * Help description.
     */
    private static final String DESCRIPTION = "TODO";
    /**
     * Help example.
     */
    private static final String EXAMPLE
        = "TODO";

    /**
     * Command line options parser.
     */
    private static final JCommanderImproved<CliOptions> PROVIDER
        = new JCommanderImproved<>(CMD_NAME, CliOptions.class);

    /**
     * Option if help is wanted.
     */
    @Parameter(
        names = {"-h", "--help"},
        description = "Show this help.")
    private boolean help;
    /**
     * Option if version is wanted.
     */
    @Parameter(
        names = {"-v", "--version"},
        description = "Show the version.")
    private boolean version;

    /**
     * Convenience method to gather the CLI options.
     *
     * @param args must not be {@code null}
     * @return never {@code null}
     */
    public static CliOptions gatherOptions(final String[] args) {
        return PROVIDER.gatherOptions(args);
    }

    /**
     * Convenience method to get the help message.
     *
     * @return never {@code null} or empty
     */
    public static String helpMessage() {
        return PROVIDER.helpMessage(USAGE, DESCRIPTION, EXAMPLE);
    }

    /**
     * Convenience method to get the usage.
     *
     * @return never {@code null} or empty
     */
    public static String usage() {
        return String.format("Usage: %s %s", CMD_NAME, USAGE);
    }

    /**
     * Whether to display the help message.
     *
     * @return {@code true} for show, else {@code false}
     */
    public boolean isHelp() {
        return help;
    }

    /**
     * Whether to display the version message.
     *
     * @return {@code true} for show, else {@code false}
     */
    public boolean isVersion() {
        return version;
    }



    @Override
    public int hashCode() {
        return Objects.hash(
            help,
            version);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof CliOptions)) {
            return false;
        }

        final CliOptions other = (CliOptions) obj;
        return Objects.equals(help, other.help)
            && Objects.equals(version, other.version);
    }
}

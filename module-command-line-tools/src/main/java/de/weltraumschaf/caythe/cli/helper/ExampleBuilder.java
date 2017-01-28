package de.weltraumschaf.caythe.cli.helper;

import de.weltraumschaf.caythe.CayThe;
import de.weltraumschaf.caythe.cli.SubCommandName;
import de.weltraumschaf.commons.validate.Validate;

/**
 * Helper class to generate CLI examples strings.
 */
public final class ExampleBuilder {

    private static final String CLI_PROMPT = "$>";

    private final StringBuilder buffer = new StringBuilder();
    private final SubCommandName subcommand;

    private ExampleBuilder(final SubCommandName subcommand) {
        super();
        this.subcommand = Validate.notNull(subcommand, "subcommand");
    }

    public static ExampleBuilder crete() {
        return new ExampleBuilder(SubCommandName.NONE);
    }

    public static ExampleBuilder crete(final SubCommandName subcommand) {
        return new ExampleBuilder(subcommand);
    }

    public ExampleBuilder nl() {
        buffer.append(CayThe.NL);
        return this;
    }

    public ExampleBuilder text(final String text) {
        buffer.append(Validate.notEmpty(text, "text"));
        return this;
    }

    public ExampleBuilder command(final String options) {
        buffer.append("  ")
            .append(CLI_PROMPT)
            .append(' ')
            .append(CayThe.COMMAND_NAME)
            .append(' ');

        if (SubCommandName.NONE != subcommand) {
            buffer.append(subcommand).append(' ');
        }

        buffer.append(Validate.notEmpty(options, "options"));
        return this;
    }

    @Override
    public String toString() {
        return buffer.toString();
    }

}

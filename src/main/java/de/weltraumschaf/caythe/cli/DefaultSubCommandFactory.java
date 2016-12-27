package de.weltraumschaf.caythe.cli;

import de.weltraumschaf.commons.validate.Validate;

/**
 * Default implementation.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
final class DefaultSubCommandFactory implements SubCommandFactory {

    @Override
    public CreateSubCommand forName(final SubCommandName name, final CliContext ctx) {
        switch (Validate.notNull(name, "name")) {
            case CREATE:
                return new CreateSubCommand(ctx);
            default:
                throw new IllegalArgumentException(String.format("Unsupported command name: '%s'!", name));
        }
    }
}

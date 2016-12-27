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
    public SubCommand forName(final SubCommandName name) {
        switch (Validate.notNull(name, "name")) {
            case COMPILE:
                return new CreateSubCommand();
            default:
                throw new IllegalArgumentException(String.format("Unsupported command name: '%s'!", name));
        }
    }
}

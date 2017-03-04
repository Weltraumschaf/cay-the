package de.weltraumschaf.caythe.cli;

import de.weltraumschaf.caythe.cli.assemble.AssembleSubCommand;
import de.weltraumschaf.caythe.cli.create.CreateSubCommand;
import de.weltraumschaf.caythe.cli.repl.ReplSubCommand;
import de.weltraumschaf.caythe.cli.run.RunSubCommand;
import de.weltraumschaf.commons.validate.Validate;

/**
 * Default implementation.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
final class DefaultSubCommandFactory implements SubCommandFactory {

    @Override
    public SubCommand forName(final SubCommandName name, final CliContext ctx) {
        switch (Validate.notNull(name, "name")) {
            case ASSEMBLE:
                return new AssembleSubCommand(ctx);
            case CREATE:
                return new CreateSubCommand(ctx);
            case REPL:
                return new ReplSubCommand(ctx);
            case RUN:
                return new RunSubCommand(ctx);
            default:
                throw new IllegalArgumentException(String.format("Unsupported command name: '%s'!", name));
        }
    }
}

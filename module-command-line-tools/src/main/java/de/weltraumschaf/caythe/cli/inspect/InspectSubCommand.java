package de.weltraumschaf.caythe.cli.inspect;

import de.weltraumschaf.caythe.cli.CliContext;
import de.weltraumschaf.caythe.cli.SubCommand;
import de.weltraumschaf.caythe.cli.source.ModuleCrawler;
import de.weltraumschaf.caythe.cli.source.ModuleFiles;
import de.weltraumschaf.caythe.cli.source.ModuleParser;
import de.weltraumschaf.caythe.cli.source.ModuleValidator;
import de.weltraumschaf.caythe.intermediate.model.Module;
import de.weltraumschaf.commons.validate.Validate;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Subcommand to inspect a module.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class InspectSubCommand implements SubCommand {
    private final CliContext ctx;

    public InspectSubCommand(final CliContext ctx) {
        super();
        this.ctx = Validate.notNull(ctx, "ctx");
    }

    @Override
    public void execute() throws Exception {
        final InspectCliOptions options = ctx.getOptions().getInspect();
        final Path moduleDir = Paths.get(options.getModule());

        final ModuleValidator validator = new ModuleValidator();
        validator.validate(moduleDir);

        final ModuleFiles files = new ModuleCrawler().find(moduleDir);
        final Path currentWorkingDir = Paths.get(".");

        if (options.isParseTree()) {
            new ParseTreeDrawer(ctx.getIo()).draw(files, currentWorkingDir);return;
        }

        final Module module = new ModuleParser().parse(files);
        validator.validate(module);

        if (options.isInspect()) {
            new ModuleInspector(ctx.getIo()).inspect(module, moduleDir);
        }

        if (options.isAstTree()) {
            new AbstractSyntaxTreeDrawer(ctx.getIo()).draw(module, currentWorkingDir);
        }

        ctx.getIo().println("Done :-)");
    }
}

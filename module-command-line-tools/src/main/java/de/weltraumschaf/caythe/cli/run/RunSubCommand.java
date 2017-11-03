package de.weltraumschaf.caythe.cli.run;

import de.weltraumschaf.caythe.cli.CliContext;
import de.weltraumschaf.caythe.cli.SubCommand;
import de.weltraumschaf.caythe.cli.source.ModuleCrawler;
import de.weltraumschaf.caythe.cli.source.ModuleFiles;
import de.weltraumschaf.caythe.cli.source.ModuleParser;
import de.weltraumschaf.caythe.cli.source.ModuleValidator;
import de.weltraumschaf.caythe.intermediate.model.Module;
import de.weltraumschaf.commons.validate.Validate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Subcommand implementation to interpret a module.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class RunSubCommand implements SubCommand {

    private final CliContext ctx;

    public RunSubCommand(final CliContext ctx) {
        super();
        this.ctx = Validate.notNull(ctx, "ctx");
    }

    @Override
    public void execute() throws Exception {
        final RunCliOptions options = ctx.getOptions().getRun();
        final Path moduleDir = Paths.get(options.getModule());

        new ModuleValidator().validate(moduleDir);

        final ModuleFiles files = new ModuleCrawler().find(moduleDir);
        final Module module = new ModuleParser().parse(files);
        final ModuleValidator validator = new ModuleValidator();
        validator.validate(module);

//            ast.accept(new AstWalkingInterpreter());
    }

}

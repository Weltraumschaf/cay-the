package de.weltraumschaf.caythe.cli.run;

import de.weltraumschaf.caythe.cli.CliContext;
import de.weltraumschaf.caythe.cli.SubCommand;
import de.weltraumschaf.caythe.frontend.CayTheManifestParser;
import de.weltraumschaf.caythe.frontend.ManifestToIntermediateTransformer;
import de.weltraumschaf.caythe.frontend.Parsers;
import de.weltraumschaf.caythe.intermediate.model.Manifest;
import de.weltraumschaf.caythe.intermediate.model.Module;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class RunSubCommand implements SubCommand {

    private final CliContext ctx;

    public RunSubCommand(final CliContext ctx) {
        super();
        this.ctx = ctx;
    }

    @Override
    public void execute() throws Exception {
        final RunCliOptions options = ctx.getOptions().getRun();
        final Path moduleDir = Paths.get(options.getModule());

        if (!Files.exists(moduleDir)) {
            throw new FileNotFoundException(String.format("Path %s does not exist!", moduleDir));
        }

        if (!Files.isDirectory(moduleDir)) {
            throw new IOException(String.format("Path %s is not a directory!", moduleDir));
        }

        if (!Files.isReadable(moduleDir)) {
            throw new IOException(String.format("Directory %s is not readable!", moduleDir));
        }

        final Module module = new ModuleParser().parse(moduleDir);
        new ModuleValidator().validate(module);

        if (options.isInspect()) {
            new ModuleInspector().inspect(module, ctx.getIo());
        }
//        final InputStream src = Files.newInputStream(file);
//        final CayTheSourceParser parser = parsers.newSourceParser(src);
//        final AstNode ast = new SourceToIntermediateTransformer().visit(parser.unit());
//
//        if (options.isTree()) {
//            final DotGenerator generator = new DotGenerator();
//            ast.accept(generator);
//            ctx.getIo().print(generator.getGraph());
//        } else {
//            ast.accept(new AstWalkingInterpreter());
//        }
    }

}

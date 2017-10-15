package de.weltraumschaf.caythe.cli.run;

import de.weltraumschaf.caythe.cli.CliContext;
import de.weltraumschaf.caythe.cli.SubCommand;
import de.weltraumschaf.caythe.frontend.Parsers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class RunSubCommand implements SubCommand {
    private Parsers parsers = new Parsers();
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

        final ModuleFiles moduleFiles = findModuleFiles(moduleDir);
        parseModuleManifest(moduleFiles);
        parseSourceFiles(moduleFiles);

//        final InputStream src = Files.newInputStream(file);
//        final CayTheSourceParser parser = parsers.newSourceParser(src);
//        final AstNode ast = new TransformToIntermediateVisitor().visit(parser.unit());
//
//        if (options.isTree()) {
//            final DotGenerator generator = new DotGenerator();
//            ast.accept(generator);
//            ctx.getIo().print(generator.getGraph());
//        } else {
//            ast.accept(new AstWalkingInterpreter());
//        }
    }

    private ModuleFiles findModuleFiles(final Path moduleDir) throws IOException {
        return new ModuleCrawler().find(moduleDir);
    }

    private void parseModuleManifest(final ModuleFiles moduleFiles) {
    }

    private void parseSourceFiles(final ModuleFiles moduleFiles) {
        
    }
}

package de.weltraumschaf.caythe.cli.run;

import de.weltraumschaf.caythe.backend.AstWalkingInterpreter;
import de.weltraumschaf.caythe.cli.CliContext;
import de.weltraumschaf.caythe.cli.SubCommand;
import de.weltraumschaf.caythe.frontend.CayTheSourceParser;
import de.weltraumschaf.caythe.frontend.Parsers;
import de.weltraumschaf.caythe.frontend.TransformToIntermediateVisitor;
import de.weltraumschaf.caythe.intermediate.ast.AstNode;

import java.io.InputStream;
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

        if (Files.isDirectory(moduleDir)) {

        } else {

        }

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
}

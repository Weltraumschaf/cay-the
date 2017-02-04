package de.weltraumschaf.caythe.cli.run;

import de.weltraumschaf.caythe.backend.TreeWalkingInterpreter;
import de.weltraumschaf.caythe.backend.experimental.types.ObjectType;
import de.weltraumschaf.caythe.cli.CliContext;
import de.weltraumschaf.caythe.cli.SubCommand;
import de.weltraumschaf.caythe.frontend.CayTheSourceParser;
import de.weltraumschaf.caythe.frontend.Parsers;
import de.weltraumschaf.caythe.frontend.TransformToIntermediateVisitor;
import de.weltraumschaf.caythe.intermediate.experimental.ast.AstNode;

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
        final Path file = Paths.get(options.getFile());
        final InputStream src = Files.newInputStream(file);
        final CayTheSourceParser parser = parsers.newSourceParser(src);
        final CayTheSourceParser.UnitContext parseTree = parser.unit();

        if (options.isTree()) {
            final TransformToIntermediateVisitor visitor = new TransformToIntermediateVisitor();
            final AstNode ast = visitor.visit(parseTree);
            final DotGenerator generator = new DotGenerator();
            ast.accept(generator);
            ctx.getIo().print(generator.getGraph());
        } else {
            final TreeWalkingInterpreter visitor = new TreeWalkingInterpreter();
            visitor.debug(options.isDebug());
            visitor.visit(parseTree);
        }
    }
}

package de.weltraumschaf.caythe.cli.run;

import de.weltraumschaf.caythe.backend.TreeWalkingInterpreter;
import de.weltraumschaf.caythe.backend.experimental.types.ObjectType;
import de.weltraumschaf.caythe.cli.CliContext;
import de.weltraumschaf.caythe.cli.SubCommand;
import de.weltraumschaf.caythe.frontend.CayTheSourceParser;
import de.weltraumschaf.caythe.frontend.Parsers;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class RunSubCommand implements SubCommand {
    private Parsers parsers = new Parsers();
    private TreeWalkingInterpreter visitor = new TreeWalkingInterpreter();
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
        visitor.debug(options.isDebug());
        final ObjectType result = visitor.visit(parser.unit());
    }
}

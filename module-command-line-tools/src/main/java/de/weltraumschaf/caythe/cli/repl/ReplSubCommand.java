package de.weltraumschaf.caythe.cli.repl;

import de.weltraumschaf.caythe.cli.CliContext;
import de.weltraumschaf.caythe.cli.SubCommand;

public final class ReplSubCommand implements SubCommand {
    private final CliContext ctx;

    public ReplSubCommand(final CliContext ctx) {
        super();
        this.ctx = ctx;
    }

    @Override
    public void execute() throws Exception {
        final Repl repl = new Repl(ctx.getIo(), ctx.getVersion());
        repl.debug(ctx.getOptions().getRepl().isDebug());
        repl.start();
    }
}

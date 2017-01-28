package de.weltraumschaf.caythe.cli.run;

import de.weltraumschaf.caythe.cli.CliContext;
import de.weltraumschaf.caythe.cli.SubCommand;

public final class RunSubCommand implements SubCommand {
    private final CliContext ctx;

    public RunSubCommand(final CliContext ctx) {
        super();
        this.ctx = ctx;
    }

    @Override
    public void execute() throws Exception {
        ctx.getIo().errorln("Command run is not implemented yet!");
    }
}

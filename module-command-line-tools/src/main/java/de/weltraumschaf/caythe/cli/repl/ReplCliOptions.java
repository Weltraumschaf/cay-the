package de.weltraumschaf.caythe.cli.repl;

import com.beust.jcommander.Parameters;
import de.weltraumschaf.caythe.cli.CommonCliOptions;
import de.weltraumschaf.caythe.cli.SubCommandName;
import de.weltraumschaf.caythe.cli.create.CreateCliOptions;
import de.weltraumschaf.caythe.cli.helper.ExampleBuilder;
import de.weltraumschaf.caythe.cli.helper.UsageBuilder;

@Parameters(commandDescription = "Starts a REPL.")
@SuppressWarnings( {"unused", "FieldCanBeLocal"})
public final class ReplCliOptions {

    public static final String EXAMPLE = ExampleBuilder.crete(SubCommandName.CREATE)
        .text("Start the repl:").nl()
        .command("TODO").nl()
        .toString();

    @SuppressWarnings("StringBufferReplaceableByString")
    public static String usage() {
        return new StringBuilder()
            .append(SubCommandName.REPL)
            .append(' ')
            .append(UsageBuilder.generate(ReplCliOptions.class))
            .append(' ')
            .append(CommonCliOptions.usage())
            .toString();
    }
}

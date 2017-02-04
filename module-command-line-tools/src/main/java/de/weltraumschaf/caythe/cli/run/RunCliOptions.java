package de.weltraumschaf.caythe.cli.run;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import de.weltraumschaf.caythe.cli.CommonCliOptions;
import de.weltraumschaf.caythe.cli.SubCommandName;
import de.weltraumschaf.caythe.cli.helper.ExampleBuilder;
import de.weltraumschaf.caythe.cli.helper.UsageBuilder;

@Parameters(commandDescription = "Interprets the given module.")
@SuppressWarnings( {"unused", "FieldCanBeLocal"})
public final class RunCliOptions extends CommonCliOptions {

    public static final String EXAMPLE = ExampleBuilder.crete(SubCommandName.RUN)
        .text("Run a module:").nl()
        .command("TODO").nl()
        .toString();

    @Parameter(names = {"-f", "--file"}, description = "The file to run", required = true)
    private String file = "";

    @Parameter(names = {"-t", "--tree"}, description = "Prints the abstract syntax tree as dot graph syntax.")
    private boolean tree;

    String getFile() {
        return file;
    }

    boolean isTree() {
        return tree;
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    public static String usage() {
        return new StringBuilder()
            .append(SubCommandName.RUN)
            .append(' ')
            .append(UsageBuilder.generate(RunCliOptions.class))
            .append(' ')
            .append(CommonCliOptions.usage())
            .toString();
    }
}

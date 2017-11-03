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

    @Parameter(names = {"-m", "--module"}, description = "The module to run", required = true)
    private String module = "";

    String getModule() {
        return module;
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

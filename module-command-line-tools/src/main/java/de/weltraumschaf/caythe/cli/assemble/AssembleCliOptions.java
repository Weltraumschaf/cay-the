package de.weltraumschaf.caythe.cli.assemble;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import de.weltraumschaf.caythe.cli.CommonCliOptions;
import de.weltraumschaf.caythe.cli.SubCommandName;
import de.weltraumschaf.caythe.cli.helper.ExampleBuilder;
import de.weltraumschaf.caythe.cli.helper.UsageBuilder;

/**
 *
 */
@Parameters(commandDescription = "Assembles a file to byte code (*.ctasm -> *.cto).")
@SuppressWarnings( {"unused", "FieldCanBeLocal"})
public final class AssembleCliOptions extends CommonCliOptions {
    public static final String EXAMPLE = ExampleBuilder.crete(SubCommandName.ASSEMBLE)
        .text("Assemble a file (will create myfile.cto):").nl()
        .command("-f myfile.ctasm").nl()
        .toString();

    @Parameter(names = {"-f", "--file"}, description = "File to assemble.", required = true)
    private String file = "";

    public String getFile() {
        return file;
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    public static String usage() {
        return new StringBuilder()
            .append(SubCommandName.ASSEMBLE)
            .append(' ')
            .append(UsageBuilder.generate(AssembleCliOptions.class))
            .append(' ')
            .append(CommonCliOptions.usage())
            .toString();
    }
}

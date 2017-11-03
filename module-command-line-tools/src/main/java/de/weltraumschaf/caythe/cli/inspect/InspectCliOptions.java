package de.weltraumschaf.caythe.cli.inspect;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import de.weltraumschaf.caythe.cli.CommonCliOptions;
import de.weltraumschaf.caythe.cli.SubCommandName;
import de.weltraumschaf.caythe.cli.helper.ExampleBuilder;
import de.weltraumschaf.caythe.cli.helper.UsageBuilder;

/**
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
@Parameters(commandDescription = "Inspects the given module.")
@SuppressWarnings( {"unused", "FieldCanBeLocal"})
public final class InspectCliOptions extends CommonCliOptions {

    public static final String EXAMPLE = ExampleBuilder.crete(SubCommandName.INSPECT)
        .text("Inspect a module:").nl()
        .command("TODO").nl()
        .toString();

    @Parameter(names = {"-m", "--module"}, description = "Path to the module to inspect.", required = true)
    private String module = "";

    @Parameter(names = {"-i", "--inspect"}, description = "Inspects the given module and print the information to STDOUT.")
    private boolean inspect;

    @Parameter(
        names = {"-t", "--ast-trees"},
        description = "Writes the abstract syntax trees of each parsed file into a separate GraphViz file in the current working directory.")
    private boolean astTree;

    @Parameter(
        names = {"-p", "--parse-trees"},
        description = "Writes the parse trees of each parsed file into a separate PostScript file in the current working directory.")
    private boolean parseTree;

    String getModule() {
        return module;
    }

    boolean isInspect() {
        return inspect;
    }

    boolean isAstTree() {
        return astTree;
    }

    boolean isParseTree() {
        return parseTree;
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    public static String usage() {
        return new StringBuilder()
            .append(SubCommandName.INSPECT)
            .append(' ')
            .append(UsageBuilder.generate(InspectCliOptions.class))
            .append(' ')
            .append(CommonCliOptions.usage())
            .toString();
    }
}

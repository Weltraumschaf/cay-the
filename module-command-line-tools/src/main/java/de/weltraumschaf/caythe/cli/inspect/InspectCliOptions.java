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

    @Parameter(names = {"-m", "--module"}, description = "The module to run", required = true)
    private String module = "";

    @Parameter(names = {"-i", "--inspect"}, description = "Inspects the given module and print the information to STDOUT")
    private boolean inspect;

    @Parameter(names = {"-t", "--tree"}, description = "Prints the abstract syntax tree as dot graph syntax.")
    private boolean tree;

    @Parameter(
        names = {"-p", "--parse-tree"},
        description = "Writes the parse trees of each parsed file into a PNG file in the current working directory.")
    private boolean parseTree;

    String getModule() {
        return module;
    }

    boolean isInspect() {
        return inspect;
    }

    boolean isTree() {
        return tree;
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

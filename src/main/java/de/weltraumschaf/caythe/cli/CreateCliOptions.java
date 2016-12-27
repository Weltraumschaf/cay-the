package de.weltraumschaf.caythe.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * CliOptions for the create sub command.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
@Parameters(commandDescription = "Creates a module scaffold.")
@SuppressWarnings( {"CanBeFinal", "unused", "FieldCanBeLocal"})
final class CreateCliOptions extends CommonCliOptions {


    static final String EXAMPLE = ExampleBuilder.crete(SubCommandName.CREATE)
        .text("Create a new empty module scaffold:").nl()
        .command("...").nl()
        .toString();

    @Parameter(names = {"-m", "--module"}, description = "The name of the module. This is used as the directory name.", required = true)
    private String name = "";
    @Parameter(names = {"-g", "--group"}, description = "")
    private String group = "";
    @Parameter(names = {"-a", "--artifact"}, description = "")
    private String artifact = "";
    @Parameter(names = {"-n", "--namespace"}, description = "")
    private String namespace = "";

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public String getArtifact() {
        return artifact;
    }

    public String getNamespace() {
        return namespace;
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    public static String usage() {
        return new StringBuilder()
            .append(SubCommandName.CREATE)
            .append(' ')
            .append("-m|--module <module name> [-g|--group <group>] [-a|--artifact] <artifact> [-n|--namespace <namespace>]")
            .append(' ')
            .append(CommonCliOptions.usage())
            .toString();
    }
}

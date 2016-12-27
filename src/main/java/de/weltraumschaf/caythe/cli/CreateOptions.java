package de.weltraumschaf.caythe.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * Options for the create sub command.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
@Parameters(commandDescription = "Creates a module skeleton.")
final class CreateOptions extends CommonOptions {

    @SuppressWarnings( {"CanBeFinal", "unused"})
    @Parameter(names = {"-m", "--module"}, description = "The name of the module. This is used as the directory name.", required = true)
    private String name = "";
    @SuppressWarnings( {"CanBeFinal", "unused"})
    @Parameter(names = {"-g", "--group"}, description = "")
    private String group = "";
    @SuppressWarnings( {"CanBeFinal", "unused"})
    @Parameter(names = {"-a", "--artifact"}, description = "")
    private String artifact = "";
    @SuppressWarnings( {"CanBeFinal", "unused"})
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
            .append(CommonOptions.usage())
            .toString();
    }
}

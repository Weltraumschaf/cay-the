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

    @Parameter(names = {"-d", "--directory"}, description = "Where to store the scaffold. If the directory does not exists it will be created.", required = true)
    private String directory = "";
    @Parameter(names = {"-g", "--group"}, description = "The group directory of the module.")
    private String group = "";
    @Parameter(names = {"-a", "--artifact"}, description = "The artifact directory of the module.")
    private String artifact = "";
    @Parameter(names = {"-n", "--namespace"}, description = "The namespace of the module.")
    private String namespace = "";

    public String getDirectory() {
        return directory;
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
            .append(UsageBuilder.generate(CreateCliOptions.class))
            .append(' ')
            .append(CommonCliOptions.usage())
            .toString();
    }
}

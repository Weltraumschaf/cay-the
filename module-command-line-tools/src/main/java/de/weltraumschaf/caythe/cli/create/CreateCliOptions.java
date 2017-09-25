package de.weltraumschaf.caythe.cli.create;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import de.weltraumschaf.caythe.cli.CommonCliOptions;
import de.weltraumschaf.caythe.cli.SubCommandName;
import de.weltraumschaf.caythe.cli.helper.UsageBuilder;
import de.weltraumschaf.caythe.cli.helper.ExampleBuilder;

/**
 * CliOptions for the create sub command.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
@Parameters(commandDescription = "Creates a module scaffold.")
@SuppressWarnings( {"unused", "FieldCanBeLocal"})
public final class CreateCliOptions extends CommonCliOptions {


    public static final String EXAMPLE = ExampleBuilder.crete(SubCommandName.CREATE)
        .text("Create a new empty module scaffold:").nl()
        .command("...").nl()
        .toString();

    @Parameter(
        required = true,
        names = {"-d", "--directory"},
        description = "Where to store the scaffold. If the directory does not exists it will be created.")
    private String directory = "";
    @Parameter(
        required = true,
        names = {"-g", "--group"},
        description = "The group identifier of the module. Allowed identifiers contain [a-z] optionally separated by dot and dash, e.g. 'foo.bar.baz'.")
    private String group = "";
    @Parameter(
        required = true,
        names = {"-a", "--artifact"},
        description = "The artifact identifier of the module. Allowed identifiers contain [a-z] optionally separated by dot and dash, e.g. 'foo.bar.baz'.")
    private String artifact = "";
    @Parameter(
        required = true,
        names = {"-n", "--namespace"},
        description = "The namespace of the module. Allowed namespaces contain [a-z] optionally separated by dot and dash, e.g. 'foo.bar.baz'.")
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

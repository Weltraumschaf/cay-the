package de.weltraumschaf.caythe.cli.create;

import de.weltraumschaf.caythe.cli.CliContext;
import de.weltraumschaf.caythe.cli.SubCommand;
import org.stringtemplate.v4.ST;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class CreateSubCommand implements SubCommand {

    private static final String MANIFEST_TEMPLATE
        = "group      <group>\n"
        + "artifact   <artifact>\n"
        + "version    1.0.0-SNAPSHOT\n"
        + "\n"
        + "namespace  <namespace>\n";
    private final CliContext ctx;

    public CreateSubCommand(final CliContext ctx) {
        super();
        this.ctx = ctx;
    }

    @Override
    public void execute() throws Exception {
        final CreateCliOptions options = ctx.getOptions().getCreate();
        final Path directory = Paths.get(options.getDirectory());
        ctx.getIo().println(String.format("Create module in %s ...", directory));

        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }

        final ST manifest = new ST(MANIFEST_TEMPLATE);
        manifest.add("group", options.getGroup());
        manifest.add("artifact", options.getArtifact());
        manifest.add("namespace", options.getNamespace());

        Files.write(directory.resolve("Manifest.mf"), manifest.render().getBytes());

        ctx.getIo().println("Done :-)");
    }
}

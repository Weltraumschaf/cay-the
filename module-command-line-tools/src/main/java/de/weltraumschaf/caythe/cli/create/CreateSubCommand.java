package de.weltraumschaf.caythe.cli.create;

import de.weltraumschaf.caythe.cli.CliContext;
import de.weltraumschaf.caythe.cli.SubCommand;
import org.stringtemplate.v4.ST;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class CreateSubCommand implements SubCommand {

    private static final Collection<Character> ALLOWED = Arrays.asList(
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '.', '-', '_'
    );

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
        manifest.add("group", validateIdentifier(options.getGroup(), "group"));
        manifest.add("artifact", validateIdentifier(options.getArtifact(), "artifact"));
        manifest.add("namespace", validateIdentifier(options.getNamespace(), "namespace"));

        Files.write(directory.resolve("Module.mf"), manifest.render().getBytes());

        ctx.getIo().println("Done :-)");
    }

    String validateIdentifier(final String identifier, final String name) {
        if (null == identifier) {
            throw new IllegalArgumentException(String.format("Identifier for %s must not be null!", name));
        }

        final String trimmedIdentifier = identifier.trim();

        if (trimmedIdentifier.isEmpty()) {
            throw new IllegalArgumentException(String.format("Identifier for %s must not be blank or empty!", name));
        }

        boolean currentCharIsDot = false;

        for (int i = 0; i < trimmedIdentifier.length(); ++i) {
            final char c = trimmedIdentifier.charAt(i);

            if (isNotAllowed(c)) {
                throw new IllegalArgumentException(
                    String.format("Identifier for %s must not contain character '%s'!", name, c));
            }

            if (currentCharIsDot && '.' == c) {
                throw new IllegalArgumentException(
                    String.format("Identifier for %s must not two consecutive dots!", name));
            }

            currentCharIsDot = '.' == c;
        }

        return trimmedIdentifier;
    }

    private boolean isNotAllowed(final Character c) {
        return !ALLOWED.contains(c);
    }
}

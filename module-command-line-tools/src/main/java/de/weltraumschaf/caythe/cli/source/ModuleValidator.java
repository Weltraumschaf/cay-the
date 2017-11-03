package de.weltraumschaf.caythe.cli.source;

import de.weltraumschaf.caythe.intermediate.model.Module;
import de.weltraumschaf.commons.validate.Validate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Validates the given model semantically.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class ModuleValidator {

    /**
     * Validates the given module path.
     * <ul>
     *     <li>Path must exist.</li>
     *     <li>Path must be a directory.</li>
     *     <li>Path must be readable.</li>
     * </ul>
     * @param module must not be {@code null}
     */
    public void validate(final Path module) throws IOException {
        Validate.notNull(module, "module");

        if (!Files.exists(module)) {
            throw new FileNotFoundException(String.format("Path %s does not exist!", module));
        }

        if (!Files.isDirectory(module)) {
            throw new IOException(String.format("Path %s is not a directory!", module));
        }

        if (!Files.isReadable(module)) {
            throw new IOException(String.format("Directory %s is not readable!", module));
        }
    }

    public void validate(final Module module) {
        /*
            TODO module.manifest validation:
            - missing: group, artifact, namespace, version.
            - version must not be 0.0.0
            - imports
                - no duplicates
                - versions > 0.0.0
         */
    }
}

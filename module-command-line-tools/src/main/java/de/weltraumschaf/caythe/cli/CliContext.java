package de.weltraumschaf.caythe.cli;

import de.weltraumschaf.commons.application.IO;
import de.weltraumschaf.commons.application.Version;

/**
 * Implementations are a container for useful objects in the CLI context.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public interface CliContext {
    /**
     * Get the CLI I/O.
     *
     * @return never {@code null}
     */
    IO getIo();

    /**
     * Get the CLI options.
     *
     * @return never {@code null}
     */
    CliOptions getOptions();

    /**
     * Get the application version.
     *
     * @return never {@code null}
     */
    Version getVersion();
}

package de.weltraumschaf.caythe.cli;

import de.weltraumschaf.commons.application.IO;
import de.weltraumschaf.commons.application.Version;

/**
 *
 */
public interface CliContext {
    IO getIo();

    CliOptions getOptions();

    Version getVersion();
}

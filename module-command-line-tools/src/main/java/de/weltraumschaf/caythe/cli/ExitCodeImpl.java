package de.weltraumschaf.caythe.cli;

import de.weltraumschaf.commons.system.ExitCode;
import lombok.Getter;

/**
 * Exit codes for application.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
enum ExitCodeImpl implements ExitCode {
    /**
     * Any unspecified error.
     */
    FATAL(-1),
    /**
     * No errors.
     */
    OK(0),
    /**
     * Indicates an invalid command line argument.
     */
    BAD_ARGUMENT(1);

    /**
     * Exit code number returned as exit code to JVM.
     */
    @Getter
    private final int code;

    /**
     * Dedicated constructor.
     *
     * @param code exit code number
     */
    ExitCodeImpl(final int code) {
        this.code = code;
    }

}

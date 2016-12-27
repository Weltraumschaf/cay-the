package de.weltraumschaf.caythe;

/**
 * Provides some basic stuff like constants and such.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class CayThe {

    /**
     * Default encoding used to read and write files, unless other is specified.
     */
    public static final String DEFAULT_ENCODING = "utf-8";
    /**
     * Default platform depending new line character used for all I/O.
     */
    public static final String DEFAULT_NEWLINE = String.format("%n");
    /**
     * The base package name of the whole application.
     */
    public static final String BASE_PACKAGE = "de.weltraumschaf.caythe";
    /**
     * The {@link #BASE_PACKAGE} converted to a resource (absolute) directory file name.
     */
    public static final String BASE_PACKAGE_DIR = "/" + BASE_PACKAGE.replaceAll("\\.", "/");
    /**
     * The command line application name (name of the "binary").
     */
    public static final String COMMAND_NAME = "caythe";

    /**
     * Private for pure static class.
     */
    private CayThe() {
        super();
    }

}

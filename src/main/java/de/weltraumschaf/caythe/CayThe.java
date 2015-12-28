package de.weltraumschaf.caythe;

/**
 * Provides some basic stuff like constants and such.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class CayThe {

    public static final String DEFAULT_ENCODING = "utf-8";
    public static final String DEFAULT_NEWLINE = String.format("%n");

    public static final String BASE_PACKAGE = "de.weltraumschaf.caythe";
    public static final String BASE_PACKAGE_DIR = "/" + BASE_PACKAGE.replaceAll("\\.", "/");
    public static final String CMD_NAME = "cay-the";

    public static final String ENV_DEBUG = "CAYTHE_DEBUG";

}

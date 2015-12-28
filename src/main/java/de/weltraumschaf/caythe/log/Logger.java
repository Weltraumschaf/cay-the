package de.weltraumschaf.caythe.log;

/**
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public interface Logger {

    void log(final String msg, final Object... args);

}

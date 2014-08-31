/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
package de.weltraumschaf.caythe;

import de.weltraumschaf.commons.validate.Validate;
import java.io.IOException;
import java.nio.file.Path;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;

/**
 * Represents the source file of a unit.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class SourceFile {

    /**
     * Default file extension.
     */
    private static final String FILE_EXTENSION = ".ct";
    /**
     * OS independent directory separator.
     */
    private static final String DIR_SEP = Constants.DIR_SEP.toString();
    /**
     * Where to find the source.
     */
    private final Path source;
    /**
     * Encoding to read the source.
     */
    private final String encoding;

    /**
     * Dedicated constructor.
     *
     * @param source must not be {@code null}
     * @param encoding must not be {@code nul} or empty
     */
    public SourceFile(Path source, final String encoding) {
        super();
        this.source = Validate.notNull(source, "source");
        this.encoding = Validate.notEmpty(encoding, "encoding");
    }

    /**
     * Creates a stream for the ANTL parser.
     *
     * @return never {@code null}, always new instance
     * @throws IOException if file can't be opened
     */
    public CharStream newStream() throws IOException {
        return new ANTLRFileStream(source.toString(), encoding);
    }

    /**
     * Converts a full qualified unit name into a file name.
     *
     * <p>
     * Example:
     * </p>
     * <pre>
     * String               = String.ct
     * foo.String           = foo/String.ct
     * foo.bar.String       = foo/bar/String.ct
     * foo.bar.baz.String   = foo/bar/baz/String.ct
     * </pre>
     *
     * @param name must not be {@code null}
     * @return never {@code null}
     */
    public static String convertToFilename(final FullQualifiedName name) {
        return Validate.notNull(name, "name")
                .getFullQualifiedName()
                .replace(FullQualifiedName.SEPARATOR, DIR_SEP) + FILE_EXTENSION;
    }
}

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

import de.weltraumschaf.commons.guava.Lists;
import de.weltraumschaf.commons.validate.Validate;
import java.nio.file.FileVisitResult;
import static java.nio.file.FileVisitResult.CONTINUE;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Find all Cay-The source code files in given directory.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class FileFinder extends SimpleFileVisitor<Path> {

    /**
     * Log facility.
     */
    private static final Logger LOG = Logger.getLogger(FileFinder.class);

    /**
     * File extension of Cay-The source code files.
     */
    private final String fileExtension;

    /**
     * Found files.
     */
    private final List<Path> foundFiles = Lists.newArrayList();

    /**
     * Dedicated constructor.
     *
     * @param fileExtension must not be {@code null} or empty
     */
    public FileFinder(final String fileExtension) {
        super();
        this.fileExtension = Validate.notEmpty(fileExtension, "fileExtension");
    }

    @Override
    public FileVisitResult visitFile(final Path file, final BasicFileAttributes attr) {
        if (attr.isRegularFile() && isSourceFile(file)) {
            LOG.trace("Found file: " + file);
            foundFiles.add(file);
        }

        return CONTINUE;
    }

    /**
     * Returns the list of found files.
     * <p>
     * Always empty until you invoke {@link #visitFile(java.nio.file.Path, java.nio.file.attribute.BasicFileAttributes)}
     * the first time. But if no files were found it still returns an empty list.
     * </p>
     *
     * @return never {@code null}
     */
    public List<Path> getFoundFiles() {
        return Collections.unmodifiableList(foundFiles);
    }

    /**
     * Whether a given file is a cay-the source file.
     *
     * @param file may be {@code nill}
     * @return {@code true} if it is a Cay-The source file, else {@code false}
     */
    boolean isSourceFile(final Path file) {
        if (null == file) {
            return false;
        }

        return file.toString().toLowerCase().endsWith(fileExtension);
    }

}

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

import de.weltraumschaf.commons.guava.Sets;
import de.weltraumschaf.commons.validate.Validate;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.SortedSet;

/**
 * Imports source files for given {@link FullQualifiedName ful qualified named} unit.
 *
 * <p>
 * First the unit is tried to find in {@link #baseDir}. If not found it will be looked up
 * in {@link #libDirs} in natural order of the directories.
 * </p>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class SourceImporter {

    /**
     * The applications base source directory.
     */
    private final Path baseDir;
    /**
     * Additional library source directory.
     */
    private final SortedSet<Path> libDirs = Sets.newTreeSet();
    /**
     * Encoding of the source files.
     */
    private final String encoding;

    /**
     * Dedicated constructor.
     *
     * @param baseDir must not be {@code null}
     * @param encoding must not be {@code null} or empty
     */
    SourceImporter(final Path baseDir, final String encoding) {
        super();
        this.baseDir = Validate.notNull(baseDir, "basePath");
        this.encoding = Validate.notEmpty(encoding, "encoding");
    }

    Path getBaseDir() {
        return baseDir;
    }

    String getEncoding() {
        return encoding;
    }

    /**
     * Ad an library location.
     *
     * @param dir must not be {@code null}
     */
    void addLibDir(final Path dir) {
        libDirs.add(Validate.notNull(dir, "dir"));
    }

    /**
     * Tries to find the source file for given full qualified name.
     * <p>
     * If the source can;t be looked up either in the {@link #baseDir} nor in one of the
     * {@link #libDirs library directories} an {@link IllegalArgumentException} will be thrown.
     * </p>
     *
     * @param unit must not be {@code null}
     * @return never {@code null}
     */
    public SourceFile importUnit(final FullQualifiedName unit) {
        final String fileName = SourceFile.convertToFilename(Validate.notNull(unit, "unit"));
        SourceFile file = lookupInBaseDir(fileName);

        if (null != file) {
            return file;
        }

        file = lookupInLibDirs(fileName);

        if (null != file) {
            return file;
        }

        throw new IllegalArgumentException(String.format(
                "Can't import unit '%s' neither in base dir '%s' nor in any lib dir %s!",
                unit.getFullQualifiedName(), baseDir, libDirs.toString()));
    }

    @Override
    public String toString() {
        return "SourceImporter{" + "basePath=" + baseDir + ", libDirs=" + libDirs + '}';
    }

    private SourceFile lookupInBaseDir(final String fileName) {
        return lookupInDir(fileName, baseDir);
    }

    private SourceFile lookupInLibDirs(final String fileName) {
        SourceFile file = null;

        for (final Path dir : libDirs) {
            file = lookupInDir(fileName, dir);

            if (null != file) {
                break;
            }
        }

        return file;
    }

    private SourceFile lookupInDir(final String fileName, final Path dir) {
        final Path file = dir.resolve(fileName);

        if (Files.exists(file)) {
            return new SourceFile(file, encoding);
        }

        return null;
    }
}

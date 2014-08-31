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
import java.nio.file.Path;
import java.util.Set;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class SourceImporter {

    private final Path basePath;
    private final Set<Path> libDirs = Sets.newHashSet();

    public SourceImporter(final Path basePath) {
        super();
        this.basePath = Validate.notNull(basePath, "basePath");
    }

    @Override
    public String toString() {
        return "SourceImporter{" + "basePath=" + basePath + ", libDirs=" + libDirs + '}';
    }

    public SourceFile importUnit(final String fullQulaifiedName) {
        return null;
    }
}

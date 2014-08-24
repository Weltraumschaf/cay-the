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

import de.weltraumschaf.caythe.ast.CompilationUnit;
import de.weltraumschaf.caythe.parser.CaytheParser;
import de.weltraumschaf.caythe.parser.Parsers;
import de.weltraumschaf.commons.guava.Sets;
import de.weltraumschaf.commons.validate.Validate;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.apache.log4j.Logger;

/**
 * Processes the given list of Cay-The source files.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class SourceProcessor {

    /**
     * Log facility.
     */
    private static final Logger LOG = Logger.getLogger(SourceProcessor.class);
    /**
     * Contains the files to process in an unmodifiable list.
     */
    private final List<Path> sourceFiles;
    /**
     * Contains the file names of already parsed source files.
     */
    private final Set<String> alreadyParsed = Sets.newHashSet();
    private final Set<CompilationUnit> units = Sets.newHashSet();
    /**
     * Source file encoding.
     */
    private final String encoding;

    /**
     * Dedicated constructor.
     *
     * @param sourceFiles must not be {@code null}
     * @param encoding must not be {@code null}
     */
    public SourceProcessor(final List<Path> sourceFiles, final String encoding) {
        super();
        this.sourceFiles = Collections.unmodifiableList(Validate.notNull(sourceFiles, "sourceFiles"));
        this.encoding = Validate.notEmpty(encoding, "encoding");
    }

    Set<CompilationUnit> getUnits() {
        return Collections.unmodifiableSet(units);
    }

    void process() throws SyntaxException {
        for (final Path sourceFile : sourceFiles) {
            if (alreadyParsed(sourceFile)) {
                continue;
            }

            try {
                parseSource(sourceFile);
            } catch (final IOException ex) {
                throw new SyntaxException(String.format("Can't read source file '%s'!", sourceFile.toString()), ex);
            }
        }
    }

    private boolean alreadyParsed(final Path sourceFile) {
        return alreadyParsed.contains(sourceFile.toString());
    }

    private void parseSource(final Path sourceFile) throws IOException {
        final String fileName = sourceFile.toString();
        LOG.debug(String.format("Parse file '%s' ...", fileName));
        final CaytheParser parser = Parsers.caythe(sourceFile, encoding);

        if (LOG.isDebugEnabled()) {
            parser.setErrorHandler(new BailErrorStrategy());
        }


        final CompilationUnit unit = null;

        units.add(unit);
        alreadyParsed.add(fileName);
    }
}

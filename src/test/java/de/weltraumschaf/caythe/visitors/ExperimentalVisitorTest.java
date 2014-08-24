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

package de.weltraumschaf.caythe.visitors;

import de.weltraumschaf.caythe.Constants;
import de.weltraumschaf.caythe.ast.CompilationUnit;
import de.weltraumschaf.caythe.parser.Parsers;
import de.weltraumschaf.caythe.parser.TestParser;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.junit.Test;

/**
 * Tests for {@link ExperimentalVisitor}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ExperimentalVisitorTest {

    @Test
    public void testSomeMethod() throws IOException, URISyntaxException {
        final Path source = new File(getClass().getResource("/de/weltraumschaf/caythe/Test.ct").toURI()).toPath();
        final TestParser parser = Parsers.test(source, Constants.DEFAULT_ENCODING.toString());

        final ParseTreeVisitor<CompilationUnit> visitor = Visitors.experimental(source);
        visitor.visit(parser.compilationUnit());
    }

}

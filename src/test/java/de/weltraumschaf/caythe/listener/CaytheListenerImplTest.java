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
package de.weltraumschaf.caythe.listener;

import de.weltraumschaf.caythe.Constants;
import de.weltraumschaf.caythe.parser.Parsers;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

/**
 * Tests for {@link ExperimentalVisitor}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CaytheListenerImplTest {

    private static final String SOURCE_FIXTURE = "/de/weltraumschaf/caythe/Test.ct";

    @Test
    public void testSomeMethod() throws IOException, URISyntaxException {
//        final Path source = new File(getClass().getResource(SOURCE_FIXTURE).toURI()).toPath();
//        final CaytheParser parser = Parsers.caythe(source, Constants.DEFAULT_ENCODING.toString());
//
//        final ParseTreeWalker walker = new ParseTreeWalker();
//        final CaytheListenerImpl listener = new CaytheListenerImpl(source);
//        walker.walk(listener, parser.compilationUnit());
//        System.out.println("Annotations: " + listener.annotations);
//        System.out.println("Classes: " + listener.classes);
//        System.out.println("Interfaces: " + listener.interfaces);
//        System.out.println("Imports: " + listener.imports);
    }

}

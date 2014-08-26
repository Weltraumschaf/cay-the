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
import de.weltraumschaf.caythe.ast.CompilationUnit;
import de.weltraumschaf.caythe.ast.Method;
import de.weltraumschaf.caythe.parser.CaytheParser;
import de.weltraumschaf.caythe.parser.Parsers;
import de.weltraumschaf.caythe.ast.Visibility;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Arrays;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link ExperimentalVisitor}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CaytheListenerImplTest {

    private static final String SOURCE_FIXTURE = "/de/weltraumschaf/caythe/Test.ct";

    @Test
    public void testSomeMethod() throws IOException, URISyntaxException {
        final Path source = new File(getClass().getResource(SOURCE_FIXTURE).toURI()).toPath();
        final CaytheParser parser = Parsers.caythe(source, Constants.DEFAULT_ENCODING.toString());

        final ParseTreeWalker walker = new ParseTreeWalker();
        final CaytheListenerImpl listener = new CaytheListenerImpl(source);
        walker.walk(listener, parser.compilationUnit());

        assertThat(listener.imports, containsInAnyOrder(
            "foo.bar.Foo",
            "foo.bar.Bar",
            "foo.bar.Baz"));
        assertThat(listener.annotations.values(), containsInAnyOrder(
            newAnnotation(source, "AnnoFoo", Visibility.PUBLIC),
            newAnnotation(source, "AnnoBar", Visibility.PACKAGE),
            newAnnotation(source, "AnnoBaz", Visibility.PRIVATE)
        ));
        assertThat(listener.classes.values(), containsInAnyOrder(
            newClass(source, "Foo", Visibility.PUBLIC),
            newClass(source, "Bar", Visibility.PACKAGE),
            newClass(source, "Baz", Visibility.PRIVATE)
        ));
        assertThat(listener.interfaces.values(), containsInAnyOrder(
            newInterface(source, "IfFoo", Visibility.PUBLIC)
                .addMethod(new Method("foo").setVisiblity(Visibility.PUBLIC))
                .addMethod(new Method("bar", Arrays.asList("String")).setVisiblity(Visibility.PUBLIC))
                .addMethod(new Method("baz", Arrays.asList("String", "Integer")).setVisiblity(Visibility.PUBLIC)),
            newInterface(source, "IfBar", Visibility.PACKAGE),
            newInterface(source, "IfBaz", Visibility.PRIVATE)
        ));
    }

    private static CompilationUnit newAnnotation(final Path file, final String name, final Visibility visibility) {
        return newUnit(file, name, visibility).setType(CompilationUnit.Type.ANNOTATION);
    }

    private static CompilationUnit newClass(final Path file, final String name, final Visibility visibility) {
        return newUnit(file, name, visibility).setType(CompilationUnit.Type.CLASS);
    }

    private static CompilationUnit newInterface(final Path file, final String name, final Visibility visibility) {
        return newUnit(file, name, visibility).setType(CompilationUnit.Type.INTERFACE);
    }

    private static CompilationUnit newUnit(final Path file, final String name, final Visibility visibility) {
        return new CompilationUnit(file, "", name).setVisiblity(visibility);
    }
}

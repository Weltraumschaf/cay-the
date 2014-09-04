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
import de.weltraumschaf.caythe.SourceFile;
import de.weltraumschaf.caythe.ast.CompilationUnit;
import de.weltraumschaf.caythe.ast.Const;
import de.weltraumschaf.caythe.ast.Method;
import de.weltraumschaf.caythe.ast.Property;
import de.weltraumschaf.caythe.parser.CaytheParser;
import de.weltraumschaf.caythe.parser.Parsers;
import de.weltraumschaf.caythe.ast.Visibility;
import de.weltraumschaf.caythe.parser.CollectingErrorListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Arrays;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Before;

/**
 * Tests for {@link ExperimentalVisitor}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CaytheListenerImplTest {

    private static final String TEST_SOURCE = "/de/weltraumschaf/caythe/Test.ct";
    private static final String EXAMPLEAPP_SOURCE = "/de/weltraumschaf/caythe/ExampleApp.ct";
    private final CollectingErrorListener errors = new CollectingErrorListener();

    private static CompilationUnit newAnnotation(final SourceFile source, final String name, final Visibility visibility) {
        return newUnit(source, name, visibility).setType(CompilationUnit.Type.ANNOTATION);
    }

    private static CompilationUnit newClass(final SourceFile source, final String name, final Visibility visibility) {
        return newUnit(source, name, visibility).setType(CompilationUnit.Type.CLASS);
    }

    private static CompilationUnit newInterface(final SourceFile source, final String name, final Visibility visibility) {
        return newUnit(source, name, visibility).setType(CompilationUnit.Type.INTERFACE);
    }

    @SuppressWarnings("deprecation")
    private static CompilationUnit newUnit(final SourceFile source, final String name, final Visibility visibility) {
        return new CompilationUnit(source, "", name).setVisiblity(visibility);
    }

    private SourceFile source(final String resource) throws URISyntaxException {
        final Path source = new File(getClass().getResource(resource).toURI()).toPath();
        return new SourceFile(source, Constants.DEFAULT_ENCODING.toString());
    }

    private CaytheListenerImpl sut(final SourceFile source) throws IOException {
        final CaytheParser parser = Parsers.caythe(source, errors);
        final ParseTreeWalker walker = new ParseTreeWalker();
        final CaytheListenerImpl sut = new CaytheListenerImpl(source);
        walker.walk(sut, parser.compilationUnit());
        return sut;
    }

    @Test
    public void importsAndTypeDecls() throws IOException, URISyntaxException {
        final SourceFile source = source(TEST_SOURCE);
        final CaytheListenerImpl sut = sut(source);

        assertThat(errors.toString(), errors.hasErrors(), is(equalTo(true)));
        assertThat(sut.imports, containsInAnyOrder(
                "foo.bar.Foo",
                "foo.bar.Bar",
                "foo.bar.Baz"));
        assertThat(sut.annotations.values(), containsInAnyOrder(
                newAnnotation(source, "AnnoFoo", Visibility.PUBLIC),
                newAnnotation(source, "AnnoBar", Visibility.PACKAGE),
                newAnnotation(source, "AnnoBaz", Visibility.PRIVATE)
        ));
        assertThat(sut.classes.values(), containsInAnyOrder(
                newClass(source, "Foo", Visibility.PUBLIC)
                .addConstant(new Const("foo", "String", "\"FOO\"", Visibility.PUBLIC))
                .addConstant(new Const("bar", "Integer", "42", Visibility.PACKAGE))
                .addConstant(new Const("baz", "Float", "3.14", Visibility.PRIVATE))
                .addProperty(new Property("foo", "String", "\"foobar\"", Property.Config.READ))
                .addProperty(new Property("bar", "String", "", Property.Config.WRITE))
                .addProperty(new Property("baz", "Integer", "42", Property.Config.READWRITE)),
                newClass(source, "Bar", Visibility.PACKAGE),
                newClass(source, "Baz", Visibility.PRIVATE)
        ));
        assertThat(sut.interfaces.values(), containsInAnyOrder(
                newInterface(source, "IfFoo", Visibility.PUBLIC)
                .addConstant(new Const("foo", "String", "\"FOO\"", Visibility.PUBLIC))
                .addConstant(new Const("bar", "Integer", "42", Visibility.PUBLIC))
                .addConstant(new Const("baz", "Float", "3.14", Visibility.PUBLIC))
                .addMethod(new Method("foo").setVisiblity(Visibility.PUBLIC))
                .addMethod(new Method("bar", Arrays.asList("String")).setVisiblity(Visibility.PUBLIC))
                .addMethod(new Method("baz", Arrays.asList("String", "Integer")).setVisiblity(Visibility.PUBLIC)),
                newInterface(source, "IfBar", Visibility.PACKAGE),
                newInterface(source, "IfBaz", Visibility.PRIVATE)
        ));
    }

    @Test
    public void exampleApp() throws IOException, URISyntaxException {
        final SourceFile source = source(EXAMPLEAPP_SOURCE);
        final CaytheListenerImpl sut = sut(source);

        assertThat(errors.toString(), errors.hasErrors(), is(equalTo(true)));
    }
}

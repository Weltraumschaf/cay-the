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
import de.weltraumschaf.caythe.ast.Method;
import de.weltraumschaf.caythe.parser.Parsers;
import de.weltraumschaf.caythe.parser.TestBaseListener;
import de.weltraumschaf.caythe.parser.TestParser;
import de.weltraumschaf.commons.guava.Lists;
import de.weltraumschaf.commons.guava.Maps;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
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

        final ParseTreeWalker walker = new ParseTreeWalker();
        final TestListenerImpl listener = new TestListenerImpl(source);
        walker.walk(listener, parser.compilationUnit());
        System.out.println("Annotations: " + listener.annotations);
        System.out.println("Classes: " + listener.classes);
        System.out.println("Interfaces: " + listener.interfaces);
        System.out.println("Imports: " + listener.imports);
    }

    private static final class TestListenerImpl extends TestBaseListener {

        private final Map<String, CompilationUnit> annotations = Maps.newHashMap();
        private final Map<String, CompilationUnit> classes = Maps.newHashMap();
        private final Map<String, CompilationUnit> interfaces = Maps.newHashMap();
        private final List<String> imports = Lists.newArrayList();
        private final Stack<CompilationUnit> currentUnit = new Stack<>();
        private final Path source;

        public TestListenerImpl(Path source) {
            this.source = source;
        }

        @Override
        public void visitErrorNode(ErrorNode node) {
            System.err.println("Error: " + node.getSymbol());
            super.visitErrorNode(node);
        }

        @Override
        public void enterImportDeclaration(final TestParser.ImportDeclarationContext ctx) {
            imports.add(ctx.getChild(1).getText());
        }

        @Override
        public void enterAnnotationDeclaration(TestParser.AnnotationDeclarationContext ctx) {
            final CompilationUnit annotation = createUnit(ctx);
            annotation.setType(CompilationUnit.Type.ANNOTATION);
            annotations.put(annotation.getFullQualifiedName(), annotation);
            currentUnit.push(annotation);
        }

        @Override
        public void exitAnnotationDeclaration(TestParser.AnnotationDeclarationContext ctx) {
            currentUnit.pop();
        }

        @Override
        public void enterClassDeclaration(TestParser.ClassDeclarationContext ctx) {
            final CompilationUnit clazz = createUnit(ctx);
            clazz.setType(CompilationUnit.Type.CLASS);
            classes.put(clazz.getFullQualifiedName(), clazz);
            currentUnit.push(clazz);
        }

        @Override
        public void exitClassDeclaration(TestParser.ClassDeclarationContext ctx) {
            currentUnit.pop();
        }

        @Override
        public void enterInterfaceDeclaration(TestParser.InterfaceDeclarationContext ctx) {
            final CompilationUnit iface = createUnit(ctx);
            iface.setType(CompilationUnit.Type.INTERFACE);
            interfaces.put(iface.getFullQualifiedName(), iface);
            currentUnit.push(iface);
        }

        @Override
        public void enterConstDeclaration(TestParser.ConstDeclarationContext ctx) {

        }

        @Override
        public void enterInterfaceMethodDeclaration(TestParser.InterfaceMethodDeclarationContext ctx) {
            final CompilationUnit iface = currentUnit.peek();

            for (int i = 0;i < ctx.getChildCount(); ++i) {
                final String foo = ctx.getChild(i).getText();
                System.out.println(foo);
            }

            final boolean hasReturnType = ctx.getChildCount() == 3;

            final String returnType = hasReturnType
                    ? ctx.getChild(0).getText()
                    : "";

            final String name = hasReturnType
                    ? ctx.getChild(1).getText()
                    : ctx.getChild(0).getText();

            final Method method = new Method(name, returnType);
            iface.addMethod(method);
        }

        @Override
        public void exitInterfaceDeclaration(TestParser.InterfaceDeclarationContext ctx) {
            currentUnit.pop();
        }

        private CompilationUnit createUnit(final ParserRuleContext ctx) {
            final Token first = ctx.getStart();
            final String name;
            final CompilationUnit.Visiblity visibility;

            switch (first.getText()) {
                case "public":
                    name = ctx.getChild(2).getText();
                    visibility = CompilationUnit.Visiblity.PUBLIC;
                    break;
                case "package":
                    name = ctx.getChild(2).getText();
                    visibility = CompilationUnit.Visiblity.PACKAGE;
                    break;
                default:
                    name = ctx.getChild(1).getText();
                    visibility = CompilationUnit.Visiblity.PRIVATE;
                    break;
            }

            final CompilationUnit unit = new CompilationUnit(source, "", name);
            unit.setVisiblity(visibility);
            return unit;
        }

    }

}

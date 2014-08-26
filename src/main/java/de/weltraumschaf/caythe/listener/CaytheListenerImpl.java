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

import de.weltraumschaf.caythe.ast.CompilationUnit;
import de.weltraumschaf.caythe.ast.Method;
import de.weltraumschaf.caythe.ast.Visibility;
import de.weltraumschaf.caythe.parser.CaytheBaseListener;
import de.weltraumschaf.caythe.parser.CaytheParser;
import de.weltraumschaf.commons.guava.Lists;
import de.weltraumschaf.commons.guava.Maps;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class CaytheListenerImpl extends CaytheBaseListener {

    final Map<String, CompilationUnit> annotations = Maps.newHashMap();
    final Map<String, CompilationUnit> classes = Maps.newHashMap();
    final Map<String, CompilationUnit> interfaces = Maps.newHashMap();
    final List<String> imports = Lists.newArrayList();
    final Stack<CompilationUnit> currentUnit = new Stack<>();
    final Path source;

    public CaytheListenerImpl(Path source) {
        super();
        this.source = source;
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        System.err.println("Error: " + node.getSymbol());
        super.visitErrorNode(node);
    }

    @Override
    public void enterImportDeclaration(final CaytheParser.ImportDeclarationContext ctx) {
        imports.add(ctx.getChild(1).getText());
    }

    @Override
    public void enterAnnotationDeclaration(CaytheParser.AnnotationDeclarationContext ctx) {
        final CompilationUnit annotation = createUnit(ctx);
        annotation.setType(CompilationUnit.Type.ANNOTATION);
        annotations.put(annotation.getFullQualifiedName(), annotation);
        currentUnit.push(annotation);
    }

    @Override
    public void exitAnnotationDeclaration(CaytheParser.AnnotationDeclarationContext ctx) {
        currentUnit.pop();
    }

    @Override
    public void enterClassDeclaration(CaytheParser.ClassDeclarationContext ctx) {
        final CompilationUnit clazz = createUnit(ctx);
        clazz.setType(CompilationUnit.Type.CLASS);
        classes.put(clazz.getFullQualifiedName(), clazz);
        currentUnit.push(clazz);
    }

    @Override
    public void exitClassDeclaration(CaytheParser.ClassDeclarationContext ctx) {
        currentUnit.pop();
    }

    @Override
    public void enterInterfaceDeclaration(CaytheParser.InterfaceDeclarationContext ctx) {
        final CompilationUnit iface = createUnit(ctx);
        iface.setType(CompilationUnit.Type.INTERFACE);
        interfaces.put(iface.getFullQualifiedName(), iface);
        currentUnit.push(iface);
    }

    @Override
    public void enterConstDeclaration(CaytheParser.ConstDeclarationContext ctx) {

    }

    @Override
    public void enterInterfaceMethodDeclaration(CaytheParser.InterfaceMethodDeclarationContext ctx) {
        int methodNamePosition = 0;

        for (int i = 0; i < ctx.getChildCount(); i++) {
            final String text = ctx.getChild(i).getText();

            if ("(".equals(text)) {
                methodNamePosition = i - 1;
                break;
            }
        }

        final List<String> returnTypes = Lists.newArrayList();

        for (int i = 0; i < methodNamePosition; i++) {
            final String text = ctx.getChild(i).getText();

            if (",".equals(text)) {
                continue;
            }

            returnTypes.add(text);
        }

        final String name = ctx.getChild(methodNamePosition).getText();
        final Method method = new Method(name, returnTypes);
        method.setVisiblity(Visibility.PUBLIC);
        final CompilationUnit iface = currentUnit.peek();
        iface.addMethod(method);
    }

    @Override
    public void exitInterfaceDeclaration(CaytheParser.InterfaceDeclarationContext ctx) {
        currentUnit.pop();
    }

    private CompilationUnit createUnit(final ParserRuleContext ctx) {
        final Token first = ctx.getStart();
        final String name;
        final Visibility visibility;

        switch (first.getText()) {
            case "public":
                name = ctx.getChild(2).getText();
                visibility = Visibility.PUBLIC;
                break;
            case "package":
                name = ctx.getChild(2).getText();
                visibility = Visibility.PACKAGE;
                break;
            default:
                name = ctx.getChild(1).getText();
                visibility = Visibility.PRIVATE;
                break;
        }

        final CompilationUnit unit = new CompilationUnit(source, "", name);
        unit.setVisiblity(visibility);
        return unit;
    }

}

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
import de.weltraumschaf.caythe.ast.Visiblity;
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
        final CompilationUnit iface = currentUnit.peek();
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
    public void exitInterfaceDeclaration(CaytheParser.InterfaceDeclarationContext ctx) {
        currentUnit.pop();
    }

    private CompilationUnit createUnit(final ParserRuleContext ctx) {
        final Token first = ctx.getStart();
        final String name;
        final Visiblity visibility;

        switch (first.getText()) {
            case "public":
                name = ctx.getChild(2).getText();
                visibility = Visiblity.PUBLIC;
                break;
            case "package":
                name = ctx.getChild(2).getText();
                visibility = Visiblity.PACKAGE;
                break;
            default:
                name = ctx.getChild(1).getText();
                visibility = Visiblity.PRIVATE;
                break;
        }

        final CompilationUnit unit = new CompilationUnit(source, "", name);
        unit.setVisiblity(visibility);
        return unit;
    }

}

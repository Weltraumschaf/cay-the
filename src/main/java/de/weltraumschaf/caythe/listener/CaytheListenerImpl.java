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

import de.weltraumschaf.caythe.SourceFile;
import de.weltraumschaf.caythe.ast.CompilationUnit;
import de.weltraumschaf.caythe.ast.Const;
import de.weltraumschaf.caythe.ast.Method;
import de.weltraumschaf.caythe.ast.Property;
import de.weltraumschaf.caythe.ast.Visibility;
import de.weltraumschaf.caythe.parser.CaytheParser;
import de.weltraumschaf.caythe.parser.CaytheParserBaseListener;
import de.weltraumschaf.commons.guava.Lists;
import de.weltraumschaf.commons.guava.Maps;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.antlr.v4.runtime.tree.ErrorNode;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
@SuppressWarnings("deprecation")
public final class CaytheListenerImpl extends CaytheParserBaseListener {

    final Map<String, CompilationUnit> annotations = Maps.newHashMap();
    final Map<String, CompilationUnit> classes = Maps.newHashMap();
    final Map<String, CompilationUnit> interfaces = Maps.newHashMap();
    final List<String> imports = Lists.newArrayList();
    final Stack<CompilationUnit> currentUnit = new Stack<>();
    final SourceFile source;

    public CaytheListenerImpl(SourceFile source) {
        super();
        this.source = source;
    }

    @Override
    public void enterImportDeclaration(final CaytheParser.ImportDeclarationContext ctx) {
        String importName = ctx.qualifiedName().getText();

        if (null != ctx.importWildcard()) {
            importName += ctx.importWildcard().getText();
        }

        imports.add(importName);
    }

    @Override
    public void enterAnnotationDeclaration(CaytheParser.AnnotationDeclarationContext ctx) {
        final CompilationUnit annotation = new CompilationUnit(source, "", ctx.IDENTIFIER().getText());
        final Visibility visibility;

        if (null == ctx.modifier()) {
            visibility = Visibility.PRIVATE;
        } else {
            visibility = TokenConverter.convertVisibility(ctx.modifier().getText());
        }

        annotation.setVisiblity(visibility);
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
        final CompilationUnit clazz = new CompilationUnit(source, "", ctx.IDENTIFIER().getText());
        final Visibility visibility;

        if (null == ctx.modifier()) {
            visibility = Visibility.PRIVATE;
        } else {
            visibility = TokenConverter.convertVisibility(ctx.modifier().getText());
        }

        // TODO Implement implementations.
        ctx.classImplemenations();

        clazz.setVisiblity(visibility);
        clazz.setType(CompilationUnit.Type.CLASS);
        classes.put(clazz.getFullQualifiedName(), clazz);
        currentUnit.push(clazz);
    }

    @Override
    public void enterClassConstDeclaration(CaytheParser.ClassConstDeclarationContext ctx) {
        final CompilationUnit unit = currentUnit.peek();
        final Visibility visibility;

        if (null == ctx.modifier()) {
            visibility = Visibility.PRIVATE;
        } else {
            visibility = TokenConverter.convertVisibility(ctx.modifier().getText());
        }

        final String type = ctx.type().getText();
        final String name = ctx.IDENTIFIER().getText();
        final String value = ctx.value().getText();

        unit.addConstant(new Const(name, type, value, visibility));
    }

    @Override
    public void enterClassPropertyDeclaration(CaytheParser.ClassPropertyDeclarationContext ctx) {
        final CompilationUnit unit = currentUnit.peek();

        if (unit.getType() != CompilationUnit.Type.CLASS) {
            throw new RuntimeException("Only classes can have properties!");
        }

        final Property.Config config;

        if (null == ctx.classPropertyConfig()) {
            config = Property.Config.READ;
        } else {
            config = TokenConverter.convertPropertyConfig(ctx.classPropertyConfig().getText());
        }

        final String type = ctx.type().getText();
        final String name = ctx.IDENTIFIER().getText();
        final String value = ctx.value() != null ? ctx.value().getText() : "";

        unit.addProperty(new Property(name, type, value, config));
    }

    @Override
    public void exitClassDeclaration(CaytheParser.ClassDeclarationContext ctx) {
        currentUnit.pop();
    }

    @Override
    public void enterInterfaceDeclaration(CaytheParser.InterfaceDeclarationContext ctx) {
        final CompilationUnit iface = new CompilationUnit(source, "", ctx.IDENTIFIER().getText());
        final Visibility visibility;

        if (null == ctx.modifier()) {
            visibility = Visibility.PRIVATE;
        } else {
            visibility = TokenConverter.convertVisibility(ctx.modifier().getText());
        }

        iface.setVisiblity(visibility);
        iface.setType(CompilationUnit.Type.INTERFACE);
        interfaces.put(iface.getFullQualifiedName(), iface);

        currentUnit.push(iface);
    }

    @Override
    public void enterInterfaceConstDeclaration(CaytheParser.InterfaceConstDeclarationContext ctx) {
        final String type = ctx.type().getText();
        final String name = ctx.IDENTIFIER().getText();
        final String value = ctx.value().getText();

        currentUnit.peek().addConstant(new Const(name, type, value, Visibility.PUBLIC));
    }

    @Override
    public void enterInterfaceMethodDeclaration(CaytheParser.InterfaceMethodDeclarationContext ctx) {
        final List<String> returnTypes = Lists.newArrayList();

        for (CaytheParser.TypeContext type : ctx.type()) {
            returnTypes.add(type.getText());
        }

        // TODO Formal parameters.
        final CaytheParser.FormalParameterListContext formalParameterList = ctx.formalParameterList();

        final String name = ctx.IDENTIFIER().getText();
        final Method method = new Method(name, returnTypes);
        method.setVisiblity(Visibility.PUBLIC);
        currentUnit.peek().addMethod(method);
    }

    @Override
    public void exitInterfaceDeclaration(CaytheParser.InterfaceDeclarationContext ctx) {
        currentUnit.pop();
    }

}

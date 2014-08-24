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
package de.weltraumschaf.caythe.ast;

import de.weltraumschaf.caythe.Constants;
import de.weltraumschaf.commons.guava.Objects;
import de.weltraumschaf.commons.guava.Sets;
import de.weltraumschaf.commons.validate.Validate;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class CompilationUnit {

    private static final String FILE_EXTENSION = Constants.FILE_EXTENSION.toString();
    private static final String DIR_SEP = Constants.DIR_SEP.toString();
    private static final String DOT = ".";

    private final Path file;
    private final String packageName;
    private final String name;
    private final Set<String> imports = Sets.newHashSet();
    private final Set<CompilationUnit> implementedInterfaces = Sets.newHashSet();
    private final Set<CompilationUnit> properties = Sets.newHashSet();
    private final Set<CompilationUnit> delegates = Sets.newHashSet();
    private final Set<Method> methods = Sets.newHashSet();
    private Type type = Type.UNKNOWN;
    private boolean isPublic;

    public CompilationUnit(final Path file) {
        super();
        this.file = Validate.notNull(file, "file");
        this.packageName = extractPackageName(file.toString());
        this.name = extractName(file.toString());
    }

    public Path getFile() {
        return file;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getName() {
        return name;
    }

    public String getFullQualifiedName() {
        return getPackageName() + DOT + getName();
    }

    public Type getType() {
        return type;
    }

    public void setType(final Type type) {
        this.type = type;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic() {
        this.isPublic = true;
    }

    public Set<String> getImports() {
        return Collections.unmodifiableSet(imports);
    }

    public void addImport(final String fullQualifiedName) {
        imports.add(fullQualifiedName);
    }

    public Set<CompilationUnit> getImplementedInterfaces() {
        return Collections.unmodifiableSet(implementedInterfaces);
    }

    public void addImplementedInterface(final CompilationUnit implementedInterface) {
        if (implementedInterface.getType() != Type.INTERFACE) {
            throw new IllegalArgumentException("Must be of type interface!");
        }

        implementedInterfaces.add(implementedInterface);
    }

    public Set<CompilationUnit> getProperties() {
        return Collections.unmodifiableSet(properties);
    }

    public void addProperty(final CompilationUnit property) {
        if (property.getType() != Type.CLASS && property.getType() != Type.INTERFACE) {
            throw new IllegalArgumentException("Must be of type class or interface!");
        }

        properties.add(property);
    }

    public Set<CompilationUnit> getDelegates() {
        return Collections.unmodifiableSet(delegates);
    }

    public void addDelegate(final CompilationUnit delegate) {
        if (delegate.getType() != Type.CLASS && delegate.getType() != Type.INTERFACE) {
            throw new IllegalArgumentException("Must be of type class or interface!");
        }

        delegates.add(delegate);
    }

    public Set<Method> getMethods() {
        return Collections.unmodifiableSet(methods);
    }

    public void getMethods(final Method method) {
        methods.add(method);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("file", file)
                .add("packageName", packageName)
                .add("name", name)
                .add("imports", imports)
                .add("implementedInterfaces", implementedInterfaces)
                .add("properties", properties)
                .add("delegates", delegates)
                .add("methods", methods)
                .add("type", type)
                .add("isPublic", isPublic)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                file,
                packageName,
                name,
                imports,
                implementedInterfaces,
                properties,
                delegates,
                methods,
                type,
                isPublic
        );
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof CompilationUnit)) {
            return false;
        }

        final CompilationUnit other = (CompilationUnit) obj;
        return Objects.equal(file, other.file)
                && Objects.equal(packageName, other.packageName)
                && Objects.equal(name, other.name)
                && Objects.equal(imports, other.imports)
                && Objects.equal(implementedInterfaces, other.implementedInterfaces)
                && Objects.equal(properties, other.properties)
                && Objects.equal(delegates, other.delegates)
                && Objects.equal(methods, other.methods)
                && Objects.equal(type, other.type)
                && Objects.equal(isPublic, other.isPublic);
    }

    private static String nullAwareTrim(final String fileName) {
        return null == fileName ? "" : fileName.trim();
    }

    static String extractPackageName(final String fileName) {
        final String trimmed = nullAwareTrim(fileName);
        return trimmed.isEmpty() ? "" : doExtractPackageName(trimmed);
    }

    static String extractName(final String fileName) {
        final String trimmed = nullAwareTrim(fileName);
        return trimmed.isEmpty() ? "" : doExtractName(trimmed);
    }

    private static String doExtractPackageName(final String fileName) {
        final String raw = removeFileExtension(fileName);
        final int lastDirSep = raw.lastIndexOf(DIR_SEP);

        if (lastDirSep > 0) {
            return raw.substring(0, lastDirSep).replaceAll(DIR_SEP, DOT);
        }

        return "";
    }

    private static String doExtractName(final String fileName) {
        final String raw = removeFileExtension(fileName);
        final int lastDirSep = raw.lastIndexOf(DIR_SEP);

        if (lastDirSep > 0) {
            return raw.substring(lastDirSep + 1);
        }

        return raw;
    }

    static String removeFileExtension(final String fileName) {
        final int lastDot = fileName.lastIndexOf(DOT);

        if (lastDot > 0 && FILE_EXTENSION.equals(fileName.substring(lastDot))) {
            return fileName.substring(0, lastDot);
        }

        return fileName;
    }

    enum Type {

        ANNOTATION, CLASS, INTERFACE, UNKNOWN;
    }
}

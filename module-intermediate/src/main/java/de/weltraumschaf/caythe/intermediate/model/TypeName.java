package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.commons.validate.Validate;

import java.nio.file.Path;
import java.util.Objects;

/**
 *
 */
public final class TypeName {
    public static final TypeName NONE = new TypeName("none", "NONE");
    private final String namespace;
    private final String basename;

    public TypeName(final String namespace, final String basename) {
        super();
        this.namespace = Validate.notEmpty(namespace, "namespace");
        this.basename = Validate.notNull(basename, "basename");
    }

    public String getNamespace() {
        return namespace;
    }

    public String getBasename() {
        return basename;
    }

    public String getFullQualifiedName() {
        return namespace + "." + basename;
    }

    public static TypeName fromFileName(final Path filename) {
        final String withoutExtension = removeFileExtension(filename.toString());
        final String withDots = withoutExtension.replaceAll("/", ".");
        final int pos = withDots.lastIndexOf('.');
        return new TypeName(withDots.substring(0, pos), withDots.substring(pos + 1));
    }

    static String removeFileExtension(final String in) {
        final int pos = in.lastIndexOf('.');
        return in.substring(0, pos);
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof TypeName)) {
            return false;
        }

        final TypeName typeName = (TypeName) o;
        return Objects.equals(namespace, typeName.namespace) &&
            Objects.equals(basename, typeName.basename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, basename);
    }

    @Override
    public String toString() {
        return "TypeName{" +
            "namespace='" + namespace + '\'' +
            ", basename='" + basename + '\'' +
            '}';
    }
}

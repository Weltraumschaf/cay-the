package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.commons.validate.Validate;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Describes a the name of a {@link Type}.
 * <p>
 * A full qualified type name consists of a namespace (the package) and the basename which is the filename without
 * file extension. Eg. the file {@literal foo/bar/baz/Snafu.ct} results in the full qualified type name
 * {@literal foo.bar.baz.Type} with the namespace {@literal foo.bar.baz} and the basename {@literal Type}.
 * </p>
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
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

    /**
     * The name namespace of the type.
     *
     * @return never {@code null} or empty
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * The name basename of the type.
     *
     * @return never {@code null} or empty
     */
    public String getBasename() {
        return basename;
    }

    /**
     * The name full qualified name of the type.
     * <p>
     * This is the combination of {@literal namespace + "." + basename}.
     * </p>
     *
     * @return never {@code null} or empty
     */
    public String getFullQualifiedName() {
        return namespace + "." + basename;
    }

    public static TypeName fromFileName(final Path filename) {
        final String withoutExtension = removeFileExtension(filename.toString());
        final String withoutLeadingSlash = removeLeadingSlash(withoutExtension);
        final String withDots = replaceDirectorySeparator(withoutLeadingSlash);
        final int lastDot = withDots.lastIndexOf('.');

        return new TypeName(withDots.substring(0, lastDot), withDots.substring(lastDot + 1));
    }

    public static TypeName fromFullQualifiedName(final String fqn) {
        final int lastDot = fqn.lastIndexOf('.');
        return new TypeName(fqn.substring(0, lastDot), fqn.substring(lastDot + 1));
    }

    static String replaceDirectorySeparator(final String in) {
        return in.replaceAll("[/\\\\]+", ".");
    }

    static String removeFileExtension(final String in) {
        return in.substring(0, in.lastIndexOf('.'));
    }

    static String removeLeadingSlash(final String in) {
        return in.charAt(0) == File.separatorChar
            ? in.substring(1)
            : in;
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
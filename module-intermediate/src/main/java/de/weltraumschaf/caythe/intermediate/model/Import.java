package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.commons.validate.Validate;

import java.util.Objects;

/**
 * Describes the imports of a {@link Type}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class Import {
    private final TypeName name;
    private final String alias;

    public Import(final TypeName name) {
        this(name, "");
    }

    public Import(final TypeName name, final String alias) {
        super();
        this.name = Validate.notNull(name, "name");
        this.alias = Validate.notNull(alias, "alias");
    }

    public TypeName getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public boolean hasAlias() {
        return !alias.isEmpty();
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Import)) {
            return false;
        }

        final Import anImport = (Import) o;
        return Objects.equals(name, anImport.name) &&
            Objects.equals(alias, anImport.alias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, alias);
    }

    @Override
    public String toString() {
        return "Import{" +
            "name=" + name +
            ", alias='" + alias + '\'' +
            '}';
    }
}

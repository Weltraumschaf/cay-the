package de.weltraumschaf.caythe.intermediate;

import de.weltraumschaf.commons.validate.Validate;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

/**
 * The information from a module manifest.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class Manifest {
    public static final Manifest NULL = new Manifest(Coordinate.NULL, "unknown", Collections.emptyList());
    private final Coordinate coordinate;
    private final String namespace;
    private final Collection<Coordinate> imports;

    public Manifest(final Coordinate coordinate, final String namespace, final Collection<Coordinate> imports) {
        super();
        this.coordinate = Validate.notNull(coordinate, "coordinate");
        this.namespace = Validate.notEmpty(namespace, "namespace");
        this.imports = new HashSet<>(Validate.notNull(imports, "imports"));
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Manifest)) {
            return false;
        }

        final Manifest manifest = (Manifest) o;
        return Objects.equals(coordinate, manifest.coordinate) &&
            Objects.equals(namespace, manifest.namespace) &&
            Objects.equals(imports, manifest.imports);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public String getNamespace() {
        return namespace;
    }

    public Collection<Coordinate> getImports() {
        return Collections.unmodifiableCollection(imports);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate, namespace, imports);
    }

    @Override
    public String toString() {
        return "Manifest{" +
            "coordinate=" + coordinate +
            ", namespace='" + namespace + '\'' +
            ", imports=" + imports +
            '}';
    }
}

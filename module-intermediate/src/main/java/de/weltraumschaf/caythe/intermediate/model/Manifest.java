package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.commons.validate.Validate;
import lombok.Getter;

import java.util.*;

/**
 * The information from a module manifest.
 * <p>
 * This type is immutable by design.
 * </p>
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class Manifest implements IntermediateModel {
    public static final Manifest NONE = new Manifest(Coordinate.NONE, "unknown", Collections.emptyList());
    @Getter
    private final Coordinate coordinate;
    @Getter
    private final String namespace;
    @Getter
    private final Collection<Coordinate> imports;

    /**
     * Dedicated constructor.
     *
     * @param coordinate must not be {@code null}
     * @param namespace must not be {@code null}
     * @param imports must not be {@code null}, defensive copied
     */
    public Manifest(final Coordinate coordinate, final String namespace, final Collection<Coordinate> imports) {
        super();
        this.coordinate = Validate.notNull(coordinate, "coordinate");
        this.namespace = Validate.notEmpty(namespace, "namespace");
        this.imports = new ArrayList<>(Validate.notNull(imports, "imports"));
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

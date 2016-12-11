package de.weltraumschaf.caythe.intermediate;

import de.weltraumschaf.commons.validate.Validate;

import java.util.Collection;
import java.util.HashSet;

/**
 * The information from a module manifest.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class Manifest {
    private final Coordinate coordinate;
    private final String namespace;
    private final Collection<Coordinate> imports;

    public Manifest(final Coordinate coordinate, final String namespace, final Collection<Coordinate> imports) {
        super();
        this.coordinate = Validate.notNull(coordinate, "coordinate");
        this.namespace = Validate.notEmpty(namespace, "namespace");
        this.imports = new HashSet<>(Validate.notNull(imports, "imports"));
    }
}

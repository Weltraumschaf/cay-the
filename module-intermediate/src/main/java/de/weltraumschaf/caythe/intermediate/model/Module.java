package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.caythe.intermediate.ast.AstNode;
import de.weltraumschaf.commons.validate.Validate;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * Represents a module.
 * <p>
 * A module is the top level artifact.
 * </p>
 */
public final class Module {
    public static final Module NONE = new Module(Manifest.NONE, Collections.emptyList(), Collections.emptyList());

    private final Manifest manifest;
    private final Collection<AstNode> units;
    private final Collection<Path> resources;

    public Module(final Manifest manifest, final Collection<AstNode> units, final Collection<Path> resources) {
        super();
        this.manifest = Validate.notNull(manifest, "manifest");
        this.units = Validate.notNull(units, "units");
        this.resources = Validate.notNull(resources, "resources");
    }

    public Manifest getManifest() {
        return manifest;
    }

    public Collection<AstNode> getUnits() {
        return units;
    }

    public Collection<Path> getResources() {
        return resources;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Module)) {
            return false;
        }

        final Module module = (Module) o;
        return Objects.equals(manifest, module.manifest)
            && Objects.equals(units, module.units)
            && Objects.equals(resources, module.resources);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manifest, units, resources);
    }

    @Override
    public String toString() {
        return "Module{" +
            "manifest=" + manifest +
            ", units=" + units +
            ", resources=" + resources +
            '}';
    }
}

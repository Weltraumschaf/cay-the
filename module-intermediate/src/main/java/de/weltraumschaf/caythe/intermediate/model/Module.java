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
    private final Collection<AstNode> types;
    private final Collection<Path> resources;

    public Module(final Manifest manifest, final Collection<AstNode> types, final Collection<Path> resources) {
        super();
        this.manifest = Validate.notNull(manifest, "manifest");
        this.types = Validate.notNull(types, "types");
        this.resources = Validate.notNull(resources, "resources");
    }

    public Manifest getManifest() {
        return manifest;
    }

    public Collection<AstNode> getTypes() {
        return types;
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
            && Objects.equals(types, module.types)
            && Objects.equals(resources, module.resources);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manifest, types, resources);
    }

    @Override
    public String toString() {
        return "Module{" +
            "manifest=" + manifest +
            ", types=" + types +
            ", resources=" + resources +
            '}';
    }
}

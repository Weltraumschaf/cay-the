package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.commons.validate.Validate;

import java.util.Objects;

/**
 * Represents a module.
 * <p>
 * A module is the top level artifact.
 * </p>
 */
public final class Module {
    public static final Module NULL = new Module(Manifest.NULL);

    private final Manifest manifest;

    public Module(final Manifest manifest) {
        super();
        this.manifest = Validate.notNull(manifest, "manifest");
    }

    public Manifest getManifest() {
        return manifest;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Module)) {
            return false;
        }

        final Module module = (Module) o;
        return Objects.equals(manifest, module.manifest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manifest);
    }

    @Override
    public String toString() {
        return "Module{" + "manifest=" + manifest + '}';
    }
}

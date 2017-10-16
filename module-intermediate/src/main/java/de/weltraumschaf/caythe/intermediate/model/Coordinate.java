package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.commons.validate.Validate;

import java.util.Objects;

/**
 * A module coordinate.
 * <p>
 * This type is immutable by design: All setters return new objects.
 * </p>
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class Coordinate {
    public static final Coordinate NONE = new Coordinate("unknown", "unknown", Version.NONE);
    private final String group;
    private final String artifact;
    private final Version version;

    public Coordinate(final String group, final String artifact, final Version version) {
        super();
        this.group = Validate.notEmpty(group, "group");
        this.artifact = Validate.notEmpty(artifact, "artifact");
        this.version = Validate.notNull(version, "version");
    }

    public String getGroup() {
        return group;
    }

    public String getArtifact() {
        return artifact;
    }

    public Version getVersion() {
        return version;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Coordinate)) {
            return false;
        }

        final Coordinate that = (Coordinate) o;
        return Objects.equals(group, that.group) &&
            Objects.equals(artifact, that.artifact) &&
            Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, artifact, version);
    }

    @Override
    public String toString() {
        return "Coordinate{" +
            "group='" + group + '\'' +
            ", artifact='" + artifact + '\'' +
            ", version=" + version +
            '}';
    }
}

package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.commons.validate.Validate;
import lombok.Getter;
import lombok.ToString;

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
@ToString
public final class Coordinate implements IntermediateModel {
    public static final Coordinate NONE = new Coordinate("unknown", "unknown", Version.NONE);
    @Getter
    private final String group;
    @Getter
    private final String artifact;
    @Getter
    private final Version version;

    public Coordinate(final String group, final String artifact, final Version version) {
        super();
        this.group = Validate.notEmpty(group, "group");
        this.artifact = Validate.notEmpty(artifact, "artifact");
        this.version = Validate.notNull(version, "version");
    }

    public String toLiteral() {
        return String.format("%s:%s:%s", group, artifact, version.toLiteral());
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

}

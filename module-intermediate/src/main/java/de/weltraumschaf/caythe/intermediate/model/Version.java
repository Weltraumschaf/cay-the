package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.commons.validate.Validate;
import lombok.Getter;

import java.util.Objects;

/**
 * Describes a semantic version.
 * <p>
 * This type is immutable by design: All setters return new objects.
 * </p>
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class Version implements IntermediateModel {
    public static final Version NONE = new Version(0, 0, 0);
    @Getter
    private final int major;
    @Getter
    private final int minor;
    @Getter
    private final int patch;
    @Getter
    private final String identifiers;

    public Version(final int major, final int minor, final int patch) {
        this(major, minor, patch, "");
    }

    public Version(final int major, final int minor, final int patch, final String identifiers) {
        super();
        this.major = Validate.greaterThanOrEqual(major, 0, "major");
        this.minor = Validate.greaterThanOrEqual(minor, 0, "minor");
        this.patch = Validate.greaterThanOrEqual(patch, 0, "patch");
        this.identifiers = Validate.notNull(identifiers, "identifiers");
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Version)) {
            return false;
        }

        final Version version = (Version) o;
        return major == version.major &&
            minor == version.minor &&
            patch == version.patch &&
            Objects.equals(identifiers, version.identifiers);
    }

    public String toLiteral() {
        return String.format("%d.%d.%d", major, minor, patch) + generateLiteralSuffix();
    }

    private String generateLiteralSuffix() {
        return identifiers.isEmpty() ? "" : '-' + identifiers;
    }

    @Override
    public int hashCode() {
        return Objects.hash(major, minor, patch, identifiers);
    }

    @Override
    public String toString() {
        return "Version{" +
            "major=" + major +
            ", minor=" + minor +
            ", patch=" + patch +
            ", identifiers=" + identifiers +
            '}';
    }
}

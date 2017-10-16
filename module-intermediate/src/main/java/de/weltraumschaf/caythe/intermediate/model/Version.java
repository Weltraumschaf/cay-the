package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.commons.validate.Validate;

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
public final class Version {
    public static final Version NONE = new Version(0, 0, 0);
    private final int major;
    private final int minor;
    private final int patch;
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

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getPatch() {
        return patch;
    }

    public String getIdentifiers() {
        return identifiers;
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

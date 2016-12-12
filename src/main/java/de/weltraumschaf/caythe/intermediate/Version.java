package de.weltraumschaf.caythe.intermediate;

import de.weltraumschaf.commons.validate.Validate;

import java.util.Objects;

/**
 * Describes a semantic version.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class Version {
    private final int major;
    private final int minor;
    private final int patch;

    public Version(final int major, final int minor, final int patch) {
        super();
        this.major = Validate.greaterThanOrEqual(major, 0, "major");
        this.minor = Validate.greaterThanOrEqual(minor, 0, "minor");
        this.patch = Validate.greaterThanOrEqual(patch, 0, "patch");
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Version)) {
            return false;
        }

        final Version version = (Version) o;
        return major == version.major &&
            minor == version.minor &&
            patch == version.patch;
    }

    @Override
    public int hashCode() {
        return Objects.hash(major, minor, patch);
    }

    @Override
    public String toString() {
        return "Version{" +
            "major=" + major +
            ", minor=" + minor +
            ", patch=" + patch +
            '}';
    }
}

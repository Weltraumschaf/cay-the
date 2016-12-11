package de.weltraumschaf.caythe.intermediate;

import de.weltraumschaf.commons.validate.Validate;

/**
 * A module coordinate.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class Coordinate {
    private final String group;
    private final String artifact;
    private final Version version;

    public Coordinate(final String group, final String artifact, final Version version) {
        super();
        this.group = Validate.notEmpty(group, "group");
        this.artifact = Validate.notEmpty(artifact, "artifact");
        this.version = Validate.notNull(version, "version");
    }
}

package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.commons.validate.Validate;

/**
 * Method of a {@link Type}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class Method {
    private final String name;
    private final Visibility  visibility ;

    public Method(final String name, final Visibility visibility) {
        super();
        this.name = Validate.notEmpty(name, "name");
        this.visibility = Validate.notNull(visibility, "visibility");
    }
}

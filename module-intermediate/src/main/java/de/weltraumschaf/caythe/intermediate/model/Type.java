package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.commons.validate.Validate;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The information from a module type.
 * <p>
 * This type is immutable by design.
 * </p>
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class Type {
    private final String name;
    private final Facet facet;
    private final Visibility  visibility ;
    private final Collection<Property> properties = new ArrayList<>();
    private final Method constructor;
    private final Collection<Method> methods = new ArrayList<>();
    private final Collection<Delegate> delegates = new ArrayList<>();

    public Type(final String name, final Facet facet, final Visibility visibility, final Method constructor) {
        super();
        this.name = Validate.notEmpty(name, "name");
        this.facet = Validate.notNull(facet, "facet");
        this.visibility = Validate.notNull(visibility, "visibility");
        this.constructor = Validate.notNull(constructor, "constructor");
    }
}

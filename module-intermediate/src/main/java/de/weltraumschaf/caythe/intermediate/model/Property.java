package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.caythe.StringUtil;
import de.weltraumschaf.caythe.intermediate.ast.NoOperation;
import de.weltraumschaf.commons.validate.Validate;

import java.util.Collections;
import java.util.Objects;

/**
 * Property of a {@link Type}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class Property {

    private final Visibility visibility;
    private final String type;
    private final String name;
    private final Method getter;
    private final Method setter;

    public Property(final String name, final Visibility visibility, final String type) {
        this(name, visibility, type, Method.NONE, Method.NONE);
    }

    public Property(final String name, final Visibility visibility, final String type, final Method getter, final Method setter) {
        super();
        this.visibility = Validate.notNull(visibility, "visibility");
        this.type = Validate.notEmpty(type, "type");
        this.name = Validate.notEmpty(name, "name");
        this.getter = getter;
        this.setter = setter;
    }

    public static Method defaultGetter(final String name, final Visibility visibility, final String type) {
        return new Method(name, visibility, type, Collections.emptyList(), new NoOperation());
    }

    public static Method defaultSetter(final String name, final Visibility visibility, final String type) {
        final Argument arg = new Argument("new" + StringUtil.upperCaseFirst(name), type);
        return new Method(name, visibility, "", Collections.singleton(arg), new NoOperation());
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Method getGetter() {
        return getter;
    }

    public Method getSetter() {
        return setter;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Property)) {
            return false;
        }

        final Property property = (Property) o;
        return visibility == property.visibility &&
            Objects.equals(type, property.type) &&
            Objects.equals(name, property.name) &&
            Objects.equals(getter, property.getter) &&
            Objects.equals(setter, property.setter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(visibility, type, name, getter, setter);
    }

    @Override
    public String toString() {
        return "Property{" +
            "visibility=" + visibility +
            ", type='" + type + '\'' +
            ", name='" + name + '\'' +
            ", getter=" + getter +
            ", setter=" + setter +
            '}';
    }
}

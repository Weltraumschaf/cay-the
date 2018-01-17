package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.caythe.StringUtil;
import de.weltraumschaf.caythe.intermediate.Equivalence;
import de.weltraumschaf.caythe.intermediate.Notification;
import de.weltraumschaf.caythe.intermediate.ast.AstNode;
import de.weltraumschaf.caythe.intermediate.ast.NoOperation;
import de.weltraumschaf.commons.validate.Validate;
import lombok.Getter;

import java.util.Collections;
import java.util.Objects;

/**
 * Property of a {@link Type}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class Property implements Equivalence<Property> {
    @Getter
    private final Visibility visibility;
    @Getter
    private final TypeName type;
    @Getter
    private final String name;
    @Getter
    private final Method getter;
    @Getter
    private final Method setter;

    public Property(final String name, final Visibility visibility, final TypeName type) {
        this(name, visibility, type, Method.NONE, Method.NONE);
    }

    public Property(final String name, final Visibility visibility, final TypeName type, final Method getter, final Method setter) {
        super();
        this.visibility = Validate.notNull(visibility, "visibility");
        this.type = Validate.notNull(type, "type");
        this.name = Validate.notEmpty(name, "name");
        this.getter = getter;
        this.setter = setter;
    }

    public static Method customGetter(final String name, final Visibility visibility, final TypeName returnType, final AstNode body) {
        return new Method(name, visibility, returnType, Collections.emptyList(), body);
    }

    public static Method defaultGetter(final String name, final Visibility visibility, final TypeName returnType) {
        return customGetter(name, visibility, returnType, new NoOperation());
    }

    public static Method customSetter(final String name, final Visibility visibility, final TypeName argumentType, final AstNode body) {
        final Argument arg = new Argument("new" + StringUtil.upperCaseFirst(name), argumentType);
        return new Method(name, visibility, TypeName.NONE, Collections.singleton(arg), body);
    }

    public static Method defaultSetter(final String name, final Visibility visibility, final TypeName argumentType) {
        return customSetter(name, visibility, argumentType, new NoOperation());
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public TypeName getType() {
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

    @Override
    public void probeEquivalence(final Property other, final Notification result) {
        if (isNotEqual(visibility, other.visibility)) {
            result.error(
                difference(
                    "Visibility",
                    "This has visibility '%s' but other has visibility '%s'"),
                visibility, other.visibility);
        }

        if (isNotEqual(type, other.type)) {
            result.error(
                difference(
                    "Type",
                    "This has type '%s' but other has type '%s'"),
                type.getFullQualifiedName(), other.type.getFullQualifiedName());
        }

        if (isNotEqual(name, other.name)) {
            result.error(
                difference(
                    "Name",
                    "This has name '%s' but other has name '%s'"),
                name, other.name);
        }

        getter.probeEquivalence(other.getter, result);
        setter.probeEquivalence(other.setter, result);
    }

}

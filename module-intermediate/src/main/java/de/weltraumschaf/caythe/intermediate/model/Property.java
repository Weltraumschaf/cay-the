package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.caythe.StringUtil;
import de.weltraumschaf.caythe.intermediate.equivalence.Equivalence;
import de.weltraumschaf.caythe.intermediate.equivalence.Notification;
import de.weltraumschaf.caythe.intermediate.model.ast.AstNode;
import de.weltraumschaf.caythe.intermediate.model.ast.NoOperation;
import de.weltraumschaf.commons.validate.Validate;
import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.Objects;

/**
 * Property of a {@link Type}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
@ToString
public final class Property implements IntermediateModel, Equivalence<Property> {
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

    @Override
    public ModelDescription describe() {
        return new ModelDescription(this);
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
    public void probeEquivalence(final Property other, final Notification result) {
        // TODO Write tests for this method.
        if (isNotEqual(visibility, other.visibility)) {
            result.error(
                difference(
                    "Visibility",
                    "This has visibility%n%s%nbut other has visibility%n%s%n"),
                visibility, other.visibility);
        }

        if (isNotEqual(type, other.type)) {
            result.error(
                difference(
                    "Type",
                    "This has type %n%s%nbut other has type%n%s%n"),
                type.getFullQualifiedName(), other.type.getFullQualifiedName());
        }

        if (isNotEqual(name, other.name)) {
            result.error(
                difference(
                    "Name",
                    "This has name %n%s%n but other has name %n%s%n"),
                name, other.name);
        }

        getter.probeEquivalence(other.getter, result);
        setter.probeEquivalence(other.setter, result);
    }

}

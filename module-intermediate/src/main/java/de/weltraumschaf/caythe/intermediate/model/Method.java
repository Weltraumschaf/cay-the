package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.caythe.intermediate.equivalence.Equivalence;
import de.weltraumschaf.caythe.intermediate.equivalence.Notification;
import de.weltraumschaf.caythe.intermediate.model.ast.AstNode;
import de.weltraumschaf.caythe.intermediate.model.ast.NoOperation;
import de.weltraumschaf.commons.validate.Validate;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

/**
 * Method of a {@link Type}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
@ToString
public final class Method implements IntermediateModel, Equivalence<Method> {
    public static final Method NONE = new Method("NONE", Visibility.PRIVATE, TypeName.NONE, Collections.emptyList(), new NoOperation());
    @Getter
    private final String name;
    @Getter
    private final Visibility visibility;
    @Getter
    private final TypeName returnType;
    @Getter
    private final List<Argument> arguments;
    @Getter
    private final AstNode body;

    public Method(final String name, final Visibility visibility, final TypeName returnType, final Collection<Argument> arguments, final AstNode body) {
        super();
        this.name = Validate.notEmpty(name, "name");
        this.visibility = Validate.notNull(visibility, "visibility");
        this.returnType = returnType;
        this.arguments = new ArrayList<>(Validate.notNull(arguments, "arguments"));
        this.body = Validate.notNull(body, "body");
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Method)) {
            return false;
        }

        final Method method = (Method) o;
        return Objects.equals(name, method.name) &&
            visibility == method.visibility &&
            Objects.equals(returnType, method.returnType) &&
            Objects.equals(arguments, method.arguments) &&
            Objects.equals(body, method.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, visibility, returnType, arguments, body);
    }

    @Override
    public void probeEquivalence(final Method other, final Notification result) {
        // TODO Write tests for this method.
        if (isNotEqual(visibility, other.visibility)) {
            result.error(
                difference(
                    "Visibility",
                    "This has visibility%n%s%nbut other has visibility%n%s%n"),
                visibility, other.visibility);
        }

        if (isNotEqual(name, other.name)) {
            result.error(
                difference(
                    "Name",
                    "This has name%n%s%nbut other has name%n%s%n"),
                name, other.name);
        }

        returnType.probeEquivalence(other.returnType, result);

        probeEquivalences(Argument.class, arguments, other.arguments, result);

        body.probeEquivalence(other.body, result);
    }

    @Override
    public ModelDescription describe() {
        return new ModelDescription(this);
    }
}

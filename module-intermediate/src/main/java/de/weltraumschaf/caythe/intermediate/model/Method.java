package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.caythe.intermediate.ast.AstNode;
import de.weltraumschaf.caythe.intermediate.ast.NoOperation;
import de.weltraumschaf.commons.validate.Validate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * Method of a {@link Type}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class Method {
    public static final Method NONE = new Method("NONE", Visibility.PRIVATE, TypeName.NONE, Collections.emptyList(), new NoOperation());
    private final String name;
    private final Visibility  visibility;
    private final TypeName returnType;
    private final Collection<Argument> arguments;
    private final AstNode body;

    public Method(final String name, final Visibility visibility, final TypeName returnType, final Collection<Argument> arguments, final AstNode body) {
        super();
        this.name = Validate.notEmpty(name, "name");
        this.visibility = Validate.notNull(visibility, "visibility");
        this.returnType = returnType;
        this.arguments = new ArrayList<>(Validate.notNull(arguments, "arguments"));
        this.body = Validate.notNull(body, "body");
    }

    public String getName() {
        return name;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public TypeName getReturnType() {
        return returnType;
    }

    public Collection<Argument> getArguments() {
        return Collections.unmodifiableCollection(arguments);
    }

    public AstNode getBody() {
        return body;
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
    public String toString() {
        return "Method{" +
            "name='" + name + '\'' +
            ", visibility=" + visibility +
            ", returnType=" + returnType +
            ", arguments=" + arguments +
            ", body=" + body +
            '}';
    }
}

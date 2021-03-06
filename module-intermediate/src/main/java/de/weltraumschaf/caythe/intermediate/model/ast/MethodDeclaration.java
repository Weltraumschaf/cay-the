package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.equivalence.Notification;
import de.weltraumschaf.caythe.intermediate.model.Position;
import de.weltraumschaf.commons.validate.Validate;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a method deceleration.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
@ToString
public final class MethodDeclaration extends BaseNode {
    @Getter
    private final List<Identifier> arguments;
    @Getter
    private final List<AstNode> body;

    public MethodDeclaration(final List<Identifier> arguments, final List<AstNode> body, final Position sourcePosition) {
        super(sourcePosition);
        this.arguments = Validate.notNull(arguments, "arguments");
        this.body = Validate.notNull(body, "body");
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String serialize() {
        final String serializedArguments = serialize(arguments.stream().map(a -> (AstNode) a).collect(Collectors.toList()));
        final String serializedBody = serialize(body);
        return serialize(String.format("(%s) (%s)", serializedArguments, serializedBody));
    }

    @Override
    public String getNodeName() {
        return "fn-decl";
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof MethodDeclaration)) {
            return false;
        }

        final MethodDeclaration that = (MethodDeclaration) o;
        return Objects.equals(arguments, that.arguments)
            && Objects.equals(body, that.body)
            && Objects.equals(getSourcePosition(), that.getSourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(arguments, body, getSourcePosition());
    }

    @Override
    public void probeEquivalence(final AstNode other, final Notification result) {
        // TODO Write tests for this method.
        probeEquivalenceFor(MethodDeclaration.class, other, result, otherMethodDecl-> {
            probeEquivalences(Identifier.class, arguments, otherMethodDecl.arguments, result);
            probeEquivalences(AstNode.class, body, otherMethodDecl.body, result);
        });
    }
}

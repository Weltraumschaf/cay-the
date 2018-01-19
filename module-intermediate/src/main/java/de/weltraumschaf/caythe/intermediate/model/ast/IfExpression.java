package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.equivalence.Notification;
import de.weltraumschaf.caythe.intermediate.model.Position;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * Represents an if expression.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
@ToString
public final class IfExpression extends BaseNode {
    @Getter
    private final AstNode condition;
    @Getter
    private final AstNode consequence;
    @Getter
    private final AstNode alternative;

    public IfExpression(final AstNode condition, final AstNode consequence, final AstNode alternative, final Position sourcePosition) {
        super(sourcePosition);
        this.condition = condition;
        this.consequence = consequence;
        this.alternative = alternative;
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String serialize() {
        return serialize(serialize(condition, consequence, alternative));
    }

    @Override
    public String getNodeName() {
        return "if";
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof IfExpression)) {
            return false;
        }

        final IfExpression that = (IfExpression) o;
        return Objects.equals(condition, that.condition)
            && Objects.equals(consequence, that.consequence)
            && Objects.equals(alternative, that.alternative)
            && Objects.equals(getSourcePosition(), that.getSourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition, consequence, alternative, getSourcePosition());
    }

    @Override
    public void probeEquivalence(final AstNode other, final Notification result) {
        // TODO Write tests for this method.
        probeEquivalenceFor(IfExpression.class, other, result, otherLoop-> {
            condition.probeEquivalence(otherLoop.condition, result);
            consequence.probeEquivalence(otherLoop.consequence, result);
            alternative.probeEquivalence(otherLoop.alternative, result);
        });
    }
}

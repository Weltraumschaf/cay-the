package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;

import java.util.Objects;

public final class IfExpression extends BaseNode {
    private final AstNode condition;
    private final AstNode consequence;
    private final AstNode alternative;

    public IfExpression(final AstNode condition, final AstNode consequence, final AstNode alternative, final Position sourcePosition) {
        super(sourcePosition);
        this.condition = condition;
        this.consequence = consequence;
        this.alternative = alternative;
    }

    public AstNode getCondition() {
        return condition;
    }

    public AstNode getConsequence() {
        return consequence;
    }

    public AstNode getAlternative() {
        return alternative;
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
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
            && Objects.equals(sourcePosition(), that.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition, consequence, alternative, sourcePosition());
    }

    @Override
    public String toString() {
        return "IfExpression{" +
            "condition=" + condition +
            ", consequence=" + consequence +
            ", alternative=" + alternative +
            ", sourcePosition=" + sourcePosition() +
            '}';
    }

    @Override
    public String serialize() {
        return serialize("if", serialize(condition, consequence, alternative));
    }
}

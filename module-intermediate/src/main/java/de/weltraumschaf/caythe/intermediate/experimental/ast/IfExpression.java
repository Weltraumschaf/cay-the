package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.AstVisitor;

import java.util.Objects;

public final class IfExpression implements AstNode {
    private final AstNode condition;
    private final AstNode consequence;
    private final AstNode alternative;

    public IfExpression(final AstNode condition, final AstNode consequence, final AstNode alternative) {
        super();
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
        return Objects.equals(condition, that.condition) &&
            Objects.equals(consequence, that.consequence) &&
            Objects.equals(alternative, that.alternative);
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition, consequence, alternative);
    }

    @Override
    public String toString() {
        return "IfExpression{" +
            "condition=" + condition +
            ", consequence=" + consequence +
            ", alternative=" + alternative +
            '}';
    }
}

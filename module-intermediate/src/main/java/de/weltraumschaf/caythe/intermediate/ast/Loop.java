package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;

import java.util.Objects;

public final class Loop extends BaseNode {
    private final AstNode init;
    private final AstNode condition;
    private final AstNode post;
    private final AstNode statements;

    public Loop(final AstNode condition, final AstNode statements, final Position sourcePosition) {
        this(new NoOperation(), condition, new NoOperation(), statements, sourcePosition);
    }

    public Loop(final AstNode init, final AstNode condition, final AstNode post, final AstNode statements, final Position sourcePosition) {
        super(sourcePosition);
        this.init = init;
        this.condition = condition;
        this.statements = statements;
        this.post = post;
    }

    public AstNode statements() {
        return statements;
    }

    public AstNode condition() {
        return condition;
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String serialize() {
        return serialize(getNodeName(), serialize(init, condition, post) + " " + serialize(statements));
    }

    @Override
    public String getNodeName() {
        return "loop";
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Loop)) {
            return false;
        }

        final Loop that = (Loop) o;
        return Objects.equals(init, that.init)
            && Objects.equals(condition, that.condition)
            && Objects.equals(post, that.post)
            && Objects.equals(statements, that.statements)
            && Objects.equals(sourcePosition(), that.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(init, condition, post, statements, sourcePosition());
    }

    @Override
    public String toString() {
        return "Loop{" +
            "init=" + init +
            ", condition=" + condition +
            ", post=" + post +
            ", statements=" + statements +
            ", sourcePosition=" + sourcePosition() +
            '}';
    }

}

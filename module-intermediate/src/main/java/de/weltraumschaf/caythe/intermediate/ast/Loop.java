package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Notification;
import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.commons.validate.Validate;

import java.util.Objects;

/**
 * Represents a loop.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
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
        this.init = Validate.notNull(init, "init");
        this.condition = Validate.notNull(condition, "condition");
        this.statements = Validate.notNull(statements, "statements");
        this.post = Validate.notNull(post, "post");
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
        return serialize(serialize(init, condition, post) + " " + serialize(statements));
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

    @Override
    public void probeEquivalence(final AstNode other, final Notification result) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }
}

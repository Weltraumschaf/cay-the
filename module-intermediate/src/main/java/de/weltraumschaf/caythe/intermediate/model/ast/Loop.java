package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.equivalence.Notification;
import de.weltraumschaf.caythe.intermediate.model.Position;
import de.weltraumschaf.commons.validate.Validate;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * Represents a loop.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
@ToString(callSuper = true)
public final class Loop extends BaseNode {
    @Getter
    private final AstNode init;
    @Getter
    private final AstNode condition;
    @Getter
    private final AstNode post;
    @Getter
    private final AstNode statements;

    public Loop(final AstNode condition, final AstNode statements, final Position sourcePosition) {
        this(new NoOperation(), condition, new NoOperation(), statements, sourcePosition);
    }

    public Loop(final AstNode init, final AstNode condition, final AstNode post, final AstNode statements, final Position sourcePosition) {
        super(sourcePosition);
        this.init = Validate.notNull(init, "init");
        this.condition = Validate.notNull(condition, "getCondition");
        this.statements = Validate.notNull(statements, "getStatements");
        this.post = Validate.notNull(post, "post");
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
            && Objects.equals(getSourcePosition(), that.getSourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(init, condition, post, statements, getSourcePosition());
    }

    @Override
    public void probeEquivalence(final AstNode other, final Notification result) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }
}

package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Notification;
import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.commons.validate.Validate;
import lombok.Getter;

import java.util.Objects;

/**
 * Represents a return statement.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class Return extends BaseNode {
    @Getter
    private final AstNode result;

    public Return(final AstNode result, final Position sourcePosition) {
        super(sourcePosition);
        this.result = Validate.notNull(result, "result");
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String serialize() {
        return serialize(serialize(result));
    }

    @Override
    public String getNodeName() {
        return "return";
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Return)) {
            return false;
        }

        final Return that = (Return) o;
        return Objects.equals(result, that.result)
            && Objects.equals(getSourcePosition(), that.getSourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, getSourcePosition());
    }

    @Override
    public String toString() {
        return "Return{" +
            "result=" + result +
            "getSourcePosition=" + getSourcePosition() +
            '}';
    }

    @Override
    public void probeEquivalence(final AstNode other, final Notification result) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }
}

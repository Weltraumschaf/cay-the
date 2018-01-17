package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.equivalence.Notification;
import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.caythe.intermediate.model.TypeName;
import de.weltraumschaf.commons.validate.Validate;
import lombok.Getter;

import java.util.Objects;

/**
 * Represents a constant definition.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class Const extends BaseNode {
    @Getter
    private final TypeName type;
    @Getter
    private final BinaryOperation assignment;

    public Const(final TypeName type, final BinaryOperation assignment, final Position sourcePosition) {
        super(sourcePosition);
        this.type = Validate.notNull(type, "type");
        this.assignment = Validate.notNull(assignment, "assignment");
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String serialize() {
        return serialize(serialize(assignment));
    }

    @Override
    public String getNodeName() {
        return "const";
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Const)) {
            return false;
        }

        final Const that = (Const) o;
        return Objects.equals(type, that.type)
            && Objects.equals(assignment, that.assignment)
            && Objects.equals(getSourcePosition(), that.getSourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, assignment, getSourcePosition());
    }

    @Override
    public String toString() {
        return "Const{" +
            "type='" + type + '\'' +
            ", assignment=" + assignment +
            ", getSourcePosition=" + getSourcePosition() +
            '}';
    }

    @Override
    public void probeEquivalence(final AstNode other, final Notification result) {
        // TODO Write tests for this method.
        probeEquivalenceFor(Const.class, other, result, otherConst -> {
            type.probeEquivalence(otherConst.type, result);
            assignment.probeEquivalence(otherConst.assignment, result);
        });
    }
}

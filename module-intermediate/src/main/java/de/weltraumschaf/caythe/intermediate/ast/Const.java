package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.caythe.intermediate.model.TypeName;

import java.util.Objects;

public final class Const extends BaseNode {
    private final TypeName type;
    private final BinaryOperation assignment;

    public Const(final TypeName type, final BinaryOperation assignment, final Position sourcePosition) {
        super(sourcePosition);
        this.type = type;
        this.assignment = assignment;
    }

    public BinaryOperation getAssignment() {
        return assignment;
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String serialize() {
        return serialize("const", serialize(assignment));
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Const)) {
            return false;
        }

        final Const that = (Const) o;
        return Objects.equals(type, that.type)
            && Objects.equals(assignment, that.assignment)
            && Objects.equals(sourcePosition(), that.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, assignment, sourcePosition());
    }

    @Override
    public String toString() {
        return "Const{" +
            "type='" + type + '\'' +
            ", assignment=" + assignment +
            ", sourcePosition=" + sourcePosition() +
            '}';
    }

}

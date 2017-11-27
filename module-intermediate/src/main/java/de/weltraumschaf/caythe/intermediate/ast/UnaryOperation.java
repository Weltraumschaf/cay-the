package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.commons.validate.Validate;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class UnaryOperation extends BaseNode {
    private final Operator operator;
    private final AstNode operand;

    public UnaryOperation(final Operator operator, final AstNode operand, final Position sourcePosition) {
        super(sourcePosition);
        this.operator = Validate.notNull(operator, "operator");
        this.operand = Validate.notNull(operand, "operand");
    }

    public Operator getOperator() {
        return operator;
    }

    public AstNode getOperand() {
        return operand;
    }

    @Override

    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }
    @Override
    public String serialize() {
        return serialize(serialize(operand));
    }

    @Override
    public String getNodeName() {
        return operator.literal;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof UnaryOperation)) {
            return false;
        }

        final UnaryOperation that = (UnaryOperation) o;
        return operator == that.operator
            && Objects.equals(operand, that.operand)
            && Objects.equals(sourcePosition(), that.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(operator, operand, sourcePosition());
    }

    @Override
    public String toString() {
        return "UnaryOperation{" +
            "operator=" + operator +
            ", operand=" + operand +
            ", sourcePosition=" + sourcePosition() +
            '}';
    }


    public static enum Operator {
        NEG("-"),
        NOT("!");

        private static final Map<String, Operator> LOOKUP = Collections.unmodifiableMap(
            Arrays.stream(values())
                .collect(Collectors.toMap(Operator::literal, Function.identity())));
        private final String literal;

        Operator(final String literal) {
            this.literal = literal;
        }

        @Override
        public String toString() {
            return literal();
        }

        public String literal() {
            return literal;
        }

        public static Operator forLiteral(final String literal) {
            if (LOOKUP.containsKey(literal)) {
                return LOOKUP.get(literal);
            }

            throw new IllegalArgumentException(String.format("No such operator: %s!", literal));
        }
    }
}

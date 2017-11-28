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

/**
 * Represents a binary operation.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class BinaryOperation extends BaseNode {
    private final Operator operator;
    private final AstNode leftOperand;
    private final AstNode rightOperand;

    public BinaryOperation(final Operator operator, final AstNode leftOperand, final AstNode rightOperand, final Position sourcePosition) {
        super(sourcePosition);
        this.operator = Validate.notNull(operator, "operator");
        this.leftOperand = Validate.notNull(leftOperand, "leftOperand");
        this.rightOperand = Validate.notNull(rightOperand, "rightOperand");
    }

    public Operator getOperator() {
        return operator;
    }

    public AstNode getLeftOperand() {
        return leftOperand;
    }

    public AstNode getRightOperand() {
        return rightOperand;
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String serialize() {
        return serialize(serialize(leftOperand, rightOperand));
    }

    @Override
    public String getNodeName() {
        return operator.literal;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof BinaryOperation)) {
            return false;
        }

        final BinaryOperation that = (BinaryOperation) o;
        return operator == that.operator &&
            Objects.equals(leftOperand, that.leftOperand) &&
            Objects.equals(rightOperand, that.rightOperand) &&
            Objects.equals(sourcePosition(), that.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(operator, leftOperand, rightOperand, sourcePosition());
    }

    @Override
    public String toString() {
        return "BinaryOperation{" +
            "operator=" + operator +
            ", leftOperand=" + leftOperand +
            ", rightOperand=" + rightOperand +
            ", sourcePosition=" + sourcePosition() +
            '}';
    }


    public enum Operator {
        ASSIGN("="),
        ADDITION("+"),
        SUBTRACTION("-"),
        MULTIPLICATION("*"),
        DIVISION("/"),
        MODULO("%"),
        POWER("^"),

        AND("&&"),
        OR("||"),

        LESS_THAN("<"),
        LESS_THAN_EQUAL("<="),
        GREATER_THAN(">"),
        GREATER_THAN_EQUAL(">="),
        EQUAL("=="),
        NOT_EQUAL("!=");

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

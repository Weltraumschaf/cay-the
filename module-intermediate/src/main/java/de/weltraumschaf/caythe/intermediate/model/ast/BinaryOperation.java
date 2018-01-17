package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.equivalence.Notification;
import de.weltraumschaf.caythe.intermediate.model.IntermediateModel;
import de.weltraumschaf.caythe.intermediate.model.Position;
import de.weltraumschaf.commons.validate.Validate;
import lombok.Getter;
import lombok.ToString;

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
@ToString(callSuper = true)
public final class BinaryOperation extends BaseNode {
    @Getter
    private final Operator operator;
    @Getter
    private final AstNode leftOperand;
    @Getter
    private final AstNode rightOperand;

    public BinaryOperation(final Operator operator, final AstNode leftOperand, final AstNode rightOperand, final Position sourcePosition) {
        super(sourcePosition);
        this.operator = Validate.notNull(operator, "operator");
        this.leftOperand = Validate.notNull(leftOperand, "leftOperand");
        this.rightOperand = Validate.notNull(rightOperand, "rightOperand");
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
            Objects.equals(getSourcePosition(), that.getSourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(operator, leftOperand, rightOperand, getSourcePosition());
    }

    @Override
    public void probeEquivalence(final AstNode other, final Notification result) {
        // TODO Write tests for this method.
        probeEquivalenceFor(BinaryOperation.class, other, result, otherBinOp -> {
            if (isNotEqual(operator, otherBinOp.operator)) {
                result.error(
                    difference(
                        "Operator",
                        "This has operator %s but other has operator %d"),
                    operator, otherBinOp.operator
                );
            }

            leftOperand.probeEquivalence(otherBinOp.leftOperand, result);
            rightOperand.probeEquivalence(otherBinOp.rightOperand, result);
        });
    }

    /**
     * All supported binary operators.
     */
    public enum Operator implements IntermediateModel {
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

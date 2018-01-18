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
 * Represents an unary operation.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
@ToString(callSuper = true)
public final class UnaryOperation extends BaseNode {
    @Getter
    private final Operator operator;
    @Getter
    private final AstNode operand;

    public UnaryOperation(final Operator operator, final AstNode operand, final Position sourcePosition) {
        super(sourcePosition);
        this.operator = Validate.notNull(operator, "operator");
        this.operand = Validate.notNull(operand, "operand");
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
            && Objects.equals(getSourcePosition(), that.getSourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(operator, operand, getSourcePosition());
    }

    @Override
    public void probeEquivalence(final AstNode other, final Notification result) {
        // TODO Write tests for this method.
        probeEquivalenceFor(UnaryOperation.class, other, result, otherUnOp -> {
            if (isNotEqual(operator, otherUnOp.operator)) {
                result.error(
                    difference(
                        "Operator",
                        "This has operator %s but other has operator %s"),
                    operator, otherUnOp.operator
                );
            }

            operand.probeEquivalence(otherUnOp.operand, result);
        });
    }

    /**
     * All supported unary operators.
     */
    public enum Operator implements IntermediateModel {
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

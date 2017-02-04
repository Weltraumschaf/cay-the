package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.Visitor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class BinaryOperation implements AstNode {
    private final Operator operator;
    private final AstNode leftOperand;
    private final AstNode rightOperand;

    public BinaryOperation(final Operator operator, final AstNode leftOperand, final AstNode rightOperand) {
        super();
        this.operator = operator;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
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
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof BinaryOperation)) {
            return false;
        }

        final BinaryOperation that = (BinaryOperation) o;
        return operator == that.operator &&
            Objects.equals(leftOperand, that.leftOperand) &&
            Objects.equals(rightOperand, that.rightOperand);
    }

    @Override
    public String toString() {
        return "BinaryOperation{" +
            "operator=" + operator +
            ", leftOperand=" + leftOperand +
            ", rightOperand=" + rightOperand +
            '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(operator, leftOperand, rightOperand);
    }

    public enum Operator {
        ASSIGN("="),
        ADD("+"),
        SUB("-"),
        MUL("*"),
        DIV("/"),
        MOD("%"),
        POW("^"),

        AND("&&"),
        OR("||"),

        LT("<"),
        LTE("<="),
        GT(">"),
        GTE(">="),
        EQ("=="),
        NEQ("!=");

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

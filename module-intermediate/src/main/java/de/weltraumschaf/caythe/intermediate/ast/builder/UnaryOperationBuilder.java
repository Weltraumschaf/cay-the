package de.weltraumschaf.caythe.intermediate.ast.builder;

import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.caythe.intermediate.ast.AstNode;
import de.weltraumschaf.caythe.intermediate.ast.UnaryOperation;

/**
 *
 */
public final class UnaryOperationBuilder {

    private UnaryOperationBuilder() {
        super();
        throw new UnsupportedOperationException("Do not call by reflection!");
    }

    public static UnaryOperation not(final AstNode operand, final int line, final int column) {
        return new UnaryOperation(UnaryOperation.Operator.NOT, operand, new Position(line, column));
    }

    public static UnaryOperation negate(final AstNode operand, final int line, final int column) {
        return new UnaryOperation(UnaryOperation.Operator.NEG, operand, new Position(line, column));
    }
}

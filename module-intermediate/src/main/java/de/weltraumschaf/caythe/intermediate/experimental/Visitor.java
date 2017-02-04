package de.weltraumschaf.caythe.intermediate.experimental;

import de.weltraumschaf.caythe.intermediate.experimental.ast.*;

public interface Visitor {
    void visit(BinaryOperation node);

    void visit(BooleanLiteral node);

    void visit(Break node);

    void visit(Const node);

    void visit(Continue node);

    void visit(FloatLiteral node);

    void visit(FunctionCall node);

    void visit(FunctionLiteral node);

    void visit(HashLiteral node);

    void visit(Identifier node);

    void visit(IfExpression node);

    void visit(IntegerLiteral node);

    void visit(Let node);

    void visit(Loop node);

    void visit(NoOperation node);

    void visit(NullLiteral node);

    void visit(Return node);

    void visit(Statement node);

    void visit(StringLiteral node);

    void visit(Subscript node);

    void visit(UnaryOperation node);

    void visit(Unit node);
}

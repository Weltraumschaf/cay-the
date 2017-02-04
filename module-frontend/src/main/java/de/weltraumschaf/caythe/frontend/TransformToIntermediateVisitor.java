package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.caythe.intermediate.experimental.ast.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;

import static de.weltraumschaf.caythe.frontend.EvaluationError.newError;
import static de.weltraumschaf.caythe.frontend.EvaluationError.newUnsupportedOperatorError;

public final class TransformToIntermediateVisitor extends CayTheSourceBaseVisitor<AstNode> {

    @Override
    protected AstNode defaultResult() {
        return NoOperation.INSTANCE;
    }

    @Override
    public AstNode visitUnit(CayTheSourceParser.UnitContext ctx) {
        final Collection<AstNode> statements = new ArrayList<>();

        for (final CayTheSourceParser.StatementContext statement : ctx.statement()) {
            final AstNode node = visit(statement);

            if (Statement.EMPTY.equals(node)) {
                continue;
            }

            statements.add(node);
        }

        return new Unit(statements);
    }

    @Override
    public AstNode visitStatement(final CayTheSourceParser.StatementContext ctx) {
        final Collection<AstNode> statements = new ArrayList<>();

        for (final ParseTree child : ctx.children) {
            final AstNode node = visit(child);

            if (NoOperation.INSTANCE.equals(node)) {
                continue;
            }

            statements.add(node);
        }

        return new Statement(statements);
    }

    @Override
    public AstNode visitLetStatement(final CayTheSourceParser.LetStatementContext ctx) {
        final AstNode assignment;
        if (null == ctx.assignStatement()) {
            // This is let w/o assignment: let a;
            assignment = new BinaryOperation(
                BinaryOperation.Operator.ASSIGN,
                new Identifier(ctx.IDENTIFIER().getText()),
                defaultResult());
        } else {
            // This is let w/ assignment: let a = 1 + 2;
            final CayTheSourceParser.AssignExpressionContext assignmentExpression = ctx.assignStatement().assignExpression();
            assignment = new BinaryOperation(
                BinaryOperation.Operator.ASSIGN,
                new Identifier(assignmentExpression.IDENTIFIER().getText()),
                visit(assignmentExpression.expression()));
        }

        return new Let(assignment);
    }

    @Override
    public AstNode visitConstStatement(final CayTheSourceParser.ConstStatementContext ctx) {
        final CayTheSourceParser.AssignExpressionContext assignment = ctx.assignStatement().assignExpression();
        return new Const(new BinaryOperation(
            BinaryOperation.Operator.ASSIGN,
            new Identifier(assignment.identifier.getText()),
            visit(assignment.expression())));
    }

    @Override
    public AstNode visitAssignStatement(final CayTheSourceParser.AssignStatementContext ctx) {
        final CayTheSourceParser.AssignExpressionContext assignment = ctx.assignExpression();
        final Identifier identifier = new Identifier(assignment.identifier.getText());
        return new BinaryOperation(BinaryOperation.Operator.ASSIGN, identifier, visit(assignment.value));
    }

    @Override
    public AstNode visitReturnStatement(final CayTheSourceParser.ReturnStatementContext ctx) {
        return new Return(visit(ctx.value));
    }

    @Override
    public AstNode visitBreakStatement(final CayTheSourceParser.BreakStatementContext ctx) {
        return Break.BREAK;
    }

    @Override
    public AstNode visitContinueStatement(final CayTheSourceParser.ContinueStatementContext ctx) {
        return Continue.CONTINUE;
    }

    @Override
    public AstNode visitExpressionStatement(final CayTheSourceParser.ExpressionStatementContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public AstNode visitIfExpression(final CayTheSourceParser.IfExpressionContext ctx) {
        return new IfExpression(visit(ctx.condition), visit(ctx.consequence), visit(ctx.alternative));
    }

    @Override
    public AstNode visitEndlessLoopExpression(final CayTheSourceParser.EndlessLoopExpressionContext ctx) {
        final Collection<AstNode> statements = new ArrayList<>();

        for (final CayTheSourceParser.StatementContext statement : ctx.body.statement()) {
            final AstNode node = visit(statement);

            if (NoOperation.INSTANCE.equals(node)) {
                continue;
            }

            statements.add(node);
        }

        return new Loop(statements);
    }

    @Override
    public AstNode visitCallExpression(final CayTheSourceParser.CallExpressionContext ctx) {
        final Identifier identifier = new Identifier(ctx.identifier.getText());
        final List<AstNode> arguments = new ArrayList<>();
        final List<CayTheSourceParser.ExpressionContext> argumentExpressions
            = ctx.arguments == null ?
            Collections.emptyList() :
            ctx.arguments.expression();

        for (final CayTheSourceParser.ExpressionContext expresssion : argumentExpressions) {
            arguments.add(visit(expresssion));
        }

        return new FunctionCall(identifier, arguments);
    }

    @Override
    public AstNode visitSubscriptExpression(final CayTheSourceParser.SubscriptExpressionContext ctx) {
        return new Subscript(visit(ctx.identifier), visit(ctx.index));
    }

    @Override
    public AstNode visitEqualOperation(final CayTheSourceParser.EqualOperationContext ctx) {
        final Set<BinaryOperation.Operator> allowed = EnumSet.of(
            BinaryOperation.Operator.EQ,
            BinaryOperation.Operator.NEQ);
        final BinaryOperation.Operator operator = BinaryOperation.Operator.forLiteral(ctx.operator.getText());

        if (allowed.contains(operator)) {
            return new BinaryOperation(operator, visit(ctx.firstOperand), visit(ctx.secondOperand));
        } else {
            throw newUnsupportedOperatorError(ctx.operator);
        }
    }

    @Override
    public AstNode visitRelationOperation(final CayTheSourceParser.RelationOperationContext ctx) {
        final Set<BinaryOperation.Operator> allowed = EnumSet.of(
            BinaryOperation.Operator.LT,
            BinaryOperation.Operator.LTE,
            BinaryOperation.Operator.GT,
            BinaryOperation.Operator.GTE);
        final BinaryOperation.Operator operator = BinaryOperation.Operator.forLiteral(ctx.operator.getText());

        if (allowed.contains(operator)) {
            return new BinaryOperation(operator, visit(ctx.firstOperand), visit(ctx.secondOperand));
        } else {
            throw newUnsupportedOperatorError(ctx.operator);
        }
    }

    @Override
    public AstNode visitPowerOperation(final CayTheSourceParser.PowerOperationContext ctx) {
        return new BinaryOperation(BinaryOperation.Operator.POW, visit(ctx.firstOperand), visit(ctx.secondOperand));
    }

    @Override
    public AstNode visitAdditiveOperation(final CayTheSourceParser.AdditiveOperationContext ctx) {
        final Set<BinaryOperation.Operator> allowed = EnumSet.of(
            BinaryOperation.Operator.ADD,
            BinaryOperation.Operator.SUB);
        final BinaryOperation.Operator operator = BinaryOperation.Operator.forLiteral(ctx.operator.getText());

        if (allowed.contains(operator)) {
            return new BinaryOperation(operator, visit(ctx.firstOperand), visit(ctx.secondOperand));
        } else {
            throw newUnsupportedOperatorError(ctx.operator);
        }
    }

    @Override
    public AstNode visitMultiplicativeOperation(final CayTheSourceParser.MultiplicativeOperationContext ctx) {
        final Set<BinaryOperation.Operator> allowed = EnumSet.of(
            BinaryOperation.Operator.MUL,
            BinaryOperation.Operator.DIV,
            BinaryOperation.Operator.MOD);
        final BinaryOperation.Operator operator = BinaryOperation.Operator.forLiteral(ctx.operator.getText());

        if (allowed.contains(operator)) {
            return new BinaryOperation(operator, visit(ctx.firstOperand), visit(ctx.secondOperand));
        } else {
            throw newUnsupportedOperatorError(ctx.operator);
        }
    }

    @Override
    public AstNode visitLogicalOrOperation(final CayTheSourceParser.LogicalOrOperationContext ctx) {
        return new BinaryOperation(BinaryOperation.Operator.AND, visit(ctx.firstOperand), visit(ctx.secondOperand));
    }

    @Override
    public AstNode visitLogicalAndOperation(final CayTheSourceParser.LogicalAndOperationContext ctx) {
        return new BinaryOperation(BinaryOperation.Operator.OR, visit(ctx.firstOperand), visit(ctx.secondOperand));
    }

    @Override
    public AstNode visitNegationOperation(final CayTheSourceParser.NegationOperationContext ctx) {
        final Set<UnaryOperation.Operator> allowed = EnumSet.of(
            UnaryOperation.Operator.NEG,
            UnaryOperation.Operator.NOT);
        final UnaryOperation.Operator operator = UnaryOperation.Operator.forLiteral(ctx.operator.getText());

        if (allowed.contains(operator)) {
            return new UnaryOperation(null, visit(ctx.operand));
        } else {
            throw newUnsupportedOperatorError(ctx.operator);
        }
    }

    @Override
    public AstNode visitNullLiteral(final CayTheSourceParser.NullLiteralContext ctx) {
        return NullLiteral.NULL;
    }

    @Override
    public AstNode visitBooleanLiteral(final CayTheSourceParser.BooleanLiteralContext ctx) {
        return "true".equals(ctx.BOOLEAN().getText())
            ? BooleanLiteral.TRUE
            : BooleanLiteral.FALSE;
    }

    @Override
    public AstNode visitFloatLiteral(final CayTheSourceParser.FloatLiteralContext ctx) {
        return new FloatLiteral(Double.parseDouble((ctx.FLOAT().getText())));
    }

    @Override
    public AstNode visitIntegerLiteral(final CayTheSourceParser.IntegerLiteralContext ctx) {
        return new IntegerLiteral(Long.parseLong(ctx.INTEGER().getText()));
    }

    @Override
    public AstNode visitStringLiteral(final CayTheSourceParser.StringLiteralContext ctx) {
        final String text = ctx.STRING().getText();
        // We must remove the quotes here.
        return new StringLiteral(text.substring(1, text.length() - 1));
    }

    @Override
    public AstNode visitIdentifierLiteral(final CayTheSourceParser.IdentifierLiteralContext ctx) {
        return new Identifier(ctx.IDENTIFIER().getText());
    }

    @Override
    public AstNode visitFunctionLiteral(final CayTheSourceParser.FunctionLiteralContext ctx) {
        final List<AstNode> arguments = new ArrayList<>();

        if (ctx.arguments != null) {
            for (final TerminalNode identifier : ctx.arguments.IDENTIFIER()) {
                arguments.add(new Identifier(identifier.getText()));
            }
        }

        final List<AstNode> body = new ArrayList<>();

        for (final CayTheSourceParser.StatementContext statement : ctx.body.statement()) {
            final AstNode node = visit(statement);

            if (NoOperation.INSTANCE.equals(node)) {
                continue;
            }

            body.add(node);
        }

        return new FunctionLiteral(arguments, body);
    }

    @Override
    public AstNode visitArrayLiteral(final CayTheSourceParser.ArrayLiteralContext ctx) {
        final List<AstNode> values = new ArrayList<>();

        for (final CayTheSourceParser.ExpressionContext expression : ctx.values.expression()) {
            values.add(visit(expression));
        }

        return new ArrayLiteral(values);
    }

    @Override
    public AstNode visitHashLiteral(final CayTheSourceParser.HashLiteralContext ctx) {
        final Map<AstNode, AstNode> values = new HashMap<>();

        if (ctx.values == null) {
            return new HashLiteral(values);
        }

        for (final CayTheSourceParser.HashPairContext pair : ctx.values.hashPair()) {
            final AstNode key = visit(pair.key);

            if (values.containsKey(key)) {
                throw newError(pair.key.getStart(), "Duplicate key given '%s'!", key);
            }

            if (pair.value == null) {
                throw newError(pair.key.getStart(), "Missing value for key '%s'!", key);
            }

            values.put(key, visit(pair.value));
        }

        return new HashLiteral(values);
    }

}

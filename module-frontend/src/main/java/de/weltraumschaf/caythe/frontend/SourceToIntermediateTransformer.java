package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.caythe.intermediate.ast.*;
import de.weltraumschaf.caythe.intermediate.model.Facet;
import de.weltraumschaf.caythe.intermediate.model.Type;
import de.weltraumschaf.caythe.intermediate.model.TypeName;
import de.weltraumschaf.caythe.intermediate.model.Visibility;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;

import static de.weltraumschaf.caythe.frontend.SyntaxError.newError;
import static de.weltraumschaf.caythe.frontend.SyntaxError.newUnsupportedOperatorError;
import static de.weltraumschaf.caythe.intermediate.ast.builder.LiteralBuilder.nil;

/**
 * Default implementation which converts the parsed tree from a source file into intermediate model.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class SourceToIntermediateTransformer extends CayTheSourceBaseVisitor<Type> {

    private final Type.TypeBuilder builder = new Type.TypeBuilder();

    public SourceToIntermediateTransformer(final TypeName name) {
        super();
        builder.setName(name);
    }

    private Position cretePosition(final Token t) {
        return new Position(t.getLine(), t.getCharPositionInLine());
    }

    @Override
    protected Type defaultResult() {
        return builder.create();
    }

    @Override
    public Type visitTypeDeclaration(final CayTheSourceParser.TypeDeclarationContext ctx) {
        builder.setFacet(Facet.valueOf(ctx.facet.getText().toUpperCase()));
        builder.setVisibility(Visibility.valueOf(ctx.visibility.getText().toUpperCase()));
        return defaultResult();
    }

//    @Override
//    public Type visitType(CayTheSourceParser.TypeContext ctx) {
//        final Collection<AstNode> statements = new ArrayList<>();
//
//        for (final CayTheSourceParser.StatementContext statement : ctx.statement()) {
//            final AstNode node = visit(statement);
//
//            if (Statement.isEmpty(node)) {
//                continue;
//            }
//
//            statements.add(node);
//        }
//
//        return defaultResult();
//    }

//    @Override
//    public Type visitStatement(final CayTheSourceParser.StatementContext ctx) {
//        final Collection<AstNode> statements = new ArrayList<>();
//        final AstNode child;
//
//        if (ctx.children.isEmpty()) {
//            child = new NoOperation(cretePosition(ctx.getStart()));
//        } else if (ctx.children.size() == 1) {
//            child = visit(ctx.children.get(0));
//        } else {
//            throw newError(ctx.getStart(), "Multiple statements not allowed!");
//        }
//
//        return defaultResult();
//    }

//    @Override
//    public Type visitLetStatement(final CayTheSourceParser.LetStatementContext ctx) {
//        final BinaryOperation assignment;
//        final Identifier identifier;
//
//        if (null == ctx.assignStatement()) {
//            // This is let w/o assignment: let a;
//            identifier = new Identifier(ctx.IDENTIFIER().getText(), cretePosition(ctx.IDENTIFIER().getSymbol()));
//            assignment = new BinaryOperation(
//                BinaryOperation.Operator.ASSIGN,
//                identifier,
//                NilLiteral.NIL,
//                cretePosition(ctx.KW_LET().getSymbol()));
//        } else {
//            // This is let w/ assignment: let a = 1 + 2;
//            final CayTheSourceParser.AssignExpressionContext assignmentExpression
//                = ctx.assignStatement().assignExpression();
//            identifier = new Identifier(
//                assignmentExpression.IDENTIFIER().getText(),
//                cretePosition(assignmentExpression.IDENTIFIER().getSymbol()));
//            assignment = new BinaryOperation(
//                BinaryOperation.Operator.ASSIGN,
//                identifier,
//                visit(assignmentExpression.expression()),
//                cretePosition(ctx.KW_LET().getSymbol()));
//        }
//
//        return new Let(assignment, cretePosition(ctx.getStart()));
//    }

//    @Override
//    public Type visitConstStatement(final CayTheSourceParser.ConstStatementContext ctx) {
//        final CayTheSourceParser.AssignExpressionContext assignment = ctx.assignStatement().assignExpression();
//        final Identifier identifier = new Identifier(
//            assignment.identifier.getText(),
//            cretePosition(assignment.identifier));
//        return new Const(
//            new BinaryOperation(
//                BinaryOperation.Operator.ASSIGN,
//                identifier,
//                visit(assignment.expression()),
//                cretePosition(ctx.assignStatement().getStart())),
//            cretePosition(ctx.getStart())
//        );
//    }

//    @Override
//    public Type visitAssignStatement(final CayTheSourceParser.AssignStatementContext ctx) {
//        final CayTheSourceParser.AssignExpressionContext assignment = ctx.assignExpression();
//        final Identifier identifier = new Identifier(
//            assignment.identifier.getText(),
//            cretePosition(assignment.identifier));
//        return new BinaryOperation(
//            BinaryOperation.Operator.ASSIGN,
//            identifier,
//            visit(assignment.value),
//            cretePosition(assignment.OP_ASSIGN().getSymbol()));
//    }

//    @Override
//    public Type visitReturnStatement(final CayTheSourceParser.ReturnStatementContext ctx) {
//        final AstNode value;
//
//        if (ctx.value == null) {
//            value = NilLiteral.NIL;
//        } else {
//            value = visit(ctx.value);
//        }
//
//        return new Return(value, cretePosition(ctx.KW_RETURN().getSymbol()));
//    }
//
//    @Override
//    public Type visitBreakStatement(final CayTheSourceParser.BreakStatementContext ctx) {
//        return Break.BREAK;
//    }
//
//    @Override
//    public Type visitContinueStatement(final CayTheSourceParser.ContinueStatementContext ctx) {
//        return Continue.CONTINUE;
//    }
//
//    @Override
//    public Type visitExpressionStatement(final CayTheSourceParser.ExpressionStatementContext ctx) {
//        return visit(ctx.expression());
//    }
//
//    @Override
//    public Type visitIfExpression(final CayTheSourceParser.IfExpressionContext ctx) {
//        final AstNode condition = visit(ctx.condition);
//        final AstNode consequence = visit(ctx.consequence.statements);
//        final AstNode alternative;
//
//        if (ctx.alternative == null) {
//            alternative = new NoOperation();
//        } else {
//            alternative = visit(ctx.alternative.statements);
//        }
//
//        return new IfExpression(condition, consequence, alternative, cretePosition(ctx.getStart()));
//    }
//
//    @Override
//    public Type visitEndlessLoopExpression(final CayTheSourceParser.EndlessLoopExpressionContext ctx) {
//        final Collection<AstNode> body = visitLoopBody(ctx.body.statement());
//        // In the endless loop version the condition is always true.
//        return new Loop(BooleanLiteral.TRUE, body, cretePosition(ctx.getStart()));
//    }
//
//    @Override
//    public Type visitConditionalLoopExpression(final CayTheSourceParser.ConditionalLoopExpressionContext ctx) {
//        final AstNode condition = visit(ctx.condition);
//        final Collection<AstNode> body = visitLoopBody(ctx.body.statement());
//        return new Loop(condition, body, cretePosition(ctx.getStart()));
//    }
//
//    @Override
//    public Type visitTraditionalLoopExpression(final CayTheSourceParser.TraditionalLoopExpressionContext ctx) {
//        final AstNode init = visit(ctx.init);
//        final AstNode condition = visit(ctx.condition);
//        final AstNode post = visit(ctx.post);
//        final Collection<AstNode> body = visitLoopBody(ctx.body.statement());
//        return new Loop(init, condition, post, body, cretePosition(ctx.getStart()));
//    }
//
//    private Collection<AstNode> visitLoopBody(final List<CayTheSourceParser.StatementContext> body) {
//        final Collection<AstNode> statements = new ArrayList<>();
//
//        for (final CayTheSourceParser.StatementContext statement : body) {
//            final AstNode node = visit(statement);
//
//            if (NoOperation.isNoop(node) || Statement.isEmpty(node)) {
//                continue;
//            }
//
//            statements.add(node);
//        }
//
//        return statements;
//    }

//    @Override
//    public Type visitCallExpression(final CayTheSourceParser.CallExpressionContext ctx) {
//        final Identifier identifier = new Identifier(ctx.identifier.getText(), cretePosition(ctx.identifier));
//        final List<AstNode> arguments = new ArrayList<>();
//        final List<CayTheSourceParser.ExpressionContext> argumentExpressions
//            = ctx.arguments == null ?
//            Collections.emptyList() :
//            ctx.arguments.expression();
//
//        for (final CayTheSourceParser.ExpressionContext expresssion : argumentExpressions) {
//            arguments.add(visit(expresssion));
//        }
//
//        return new FunctionCall(identifier, arguments, cretePosition(ctx.identifier));
//    }

//    @Override
//    public Type visitSubscriptExpression(final CayTheSourceParser.SubscriptExpressionContext ctx) {
//        return new Subscript(visit(ctx.identifier), visit(ctx.index), cretePosition(ctx.identifier.getStart()));
//    }
//
//    @Override
//    public Type visitEqualOperation(final CayTheSourceParser.EqualOperationContext ctx) {
//        final Set<BinaryOperation.Operator> allowed = EnumSet.of(
//            BinaryOperation.Operator.EQUAL,
//            BinaryOperation.Operator.NOT_EQUAL);
//        final BinaryOperation.Operator operator = BinaryOperation.Operator.forLiteral(ctx.operator.getText());
//
//        if (allowed.contains(operator)) {
//            return new BinaryOperation(
//                operator,
//                visit(ctx.firstOperand),
//                visit(ctx.secondOperand),
//                cretePosition(ctx.getStart()));
//        } else {
//            throw newUnsupportedOperatorError(ctx.operator);
//        }
//    }
//
//    @Override
//    public Type visitRelationOperation(final CayTheSourceParser.RelationOperationContext ctx) {
//        final Set<BinaryOperation.Operator> allowed = EnumSet.of(
//            BinaryOperation.Operator.LESS_THAN,
//            BinaryOperation.Operator.LESS_THAN_EQUAL,
//            BinaryOperation.Operator.GREATER_THAN,
//            BinaryOperation.Operator.GREATER_THAN_EQUAL);
//        final BinaryOperation.Operator operator = BinaryOperation.Operator.forLiteral(ctx.operator.getText());
//
//        if (allowed.contains(operator)) {
//            return new BinaryOperation(
//                operator,
//                visit(ctx.firstOperand),
//                visit(ctx.secondOperand),
//                cretePosition(ctx.getStart()));
//        } else {
//            throw newUnsupportedOperatorError(ctx.operator);
//        }
//    }
//
//    @Override
//    public Type visitPowerOperation(final CayTheSourceParser.PowerOperationContext ctx) {
//        return new BinaryOperation(
//            BinaryOperation.Operator.POWER,
//            visit(ctx.firstOperand),
//            visit(ctx.secondOperand),
//            cretePosition(ctx.getStart()));
//    }
//
//    @Override
//    public Type visitAdditiveOperation(final CayTheSourceParser.AdditiveOperationContext ctx) {
//        final Set<BinaryOperation.Operator> allowed = EnumSet.of(
//            BinaryOperation.Operator.ADDITION,
//            BinaryOperation.Operator.SUBTRACTION);
//        final BinaryOperation.Operator operator = BinaryOperation.Operator.forLiteral(ctx.operator.getText());
//
//        if (allowed.contains(operator)) {
//            return new BinaryOperation(
//                operator,
//                visit(ctx.firstOperand),
//                visit(ctx.secondOperand),
//                cretePosition(ctx.getStart()));
//        } else {
//            throw newUnsupportedOperatorError(ctx.operator);
//        }
//    }
//
//    @Override
//    public Type visitMultiplicativeOperation(final CayTheSourceParser.MultiplicativeOperationContext ctx) {
//        final Set<BinaryOperation.Operator> allowed = EnumSet.of(
//            BinaryOperation.Operator.MULTIPLICATION,
//            BinaryOperation.Operator.DIVISION,
//            BinaryOperation.Operator.MODULO);
//        final BinaryOperation.Operator operator = BinaryOperation.Operator.forLiteral(ctx.operator.getText());
//
//        if (allowed.contains(operator)) {
//            return new BinaryOperation(
//                operator,
//                visit(ctx.firstOperand),
//                visit(ctx.secondOperand),
//                cretePosition(ctx.getStart()));
//        } else {
//            throw newUnsupportedOperatorError(ctx.operator);
//        }
//    }
//
//    @Override
//    public Type visitLogicalOrOperation(final CayTheSourceParser.LogicalOrOperationContext ctx) {
//        return new BinaryOperation(
//            BinaryOperation.Operator.OR,
//            visit(ctx.firstOperand),
//            visit(ctx.secondOperand),
//            cretePosition(ctx.getStart()));
//    }
//
//    @Override
//    public Type visitLogicalAndOperation(final CayTheSourceParser.LogicalAndOperationContext ctx) {
//        return new BinaryOperation(
//            BinaryOperation.Operator.AND,
//            visit(ctx.firstOperand),
//            visit(ctx.secondOperand),
//            cretePosition(ctx.getStart()));
//    }
//
//    @Override
//    public Type visitNegationOperation(final CayTheSourceParser.NegationOperationContext ctx) {
//        final Set<UnaryOperation.Operator> allowed = EnumSet.of(
//            UnaryOperation.Operator.NEG,
//            UnaryOperation.Operator.NOT);
//        final UnaryOperation.Operator operator = UnaryOperation.Operator.forLiteral(ctx.operator.getText());
//
//        if (allowed.contains(operator)) {
//            return new UnaryOperation(operator, visit(ctx.operand), cretePosition(ctx.getStart()));
//        } else {
//            throw newUnsupportedOperatorError(ctx.operator);
//        }
//    }
//
//    @Override
//    public Type visitNestedExpression(final CayTheSourceParser.NestedExpressionContext ctx) {
//        return visit(ctx.expression());
//    }
//
//    @Override
//    public Type visitNilLiteral(final CayTheSourceParser.NilLiteralContext ctx) {
//        return NilLiteral.NIL;
//    }
//
//    @Override
//    public Type visitBooleanLiteral(final CayTheSourceParser.BooleanLiteralContext ctx) {
//        return "true".equals(ctx.BOOLEAN().getText())
//            ? BooleanLiteral.TRUE
//            : BooleanLiteral.FALSE;
//    }
//
//    @Override
//    public Type visitRealLiteral(final CayTheSourceParser.RealLiteralContext ctx) {
//        return new RealLiteral
//            (Double.parseDouble((ctx.REAL().getText())),
//                cretePosition(ctx.getStart()));
//    }
//
//    @Override
//    public Type visitIntegerLiteral(final CayTheSourceParser.IntegerLiteralContext ctx) {
//        return new IntegerLiteral(
//            Long.parseLong(ctx.INTEGER().getText()),
//            cretePosition(ctx.getStart()));
//    }
//
//    @Override
//    public Type visitStringLiteral(final CayTheSourceParser.StringLiteralContext ctx) {
//        final String text = ctx.STRING().getText();
//        // We must remove the quotes here.
//        return new StringLiteral(
//            text.substring(1, text.length() - 1),
//            cretePosition(ctx.getStart()));
//    }
//
//    @Override
//    public Type visitIdentifierLiteral(final CayTheSourceParser.IdentifierLiteralContext ctx) {
//        return new Identifier(
//            ctx.IDENTIFIER().getText(),
//            cretePosition(ctx.getStart()));
//    }

//    @Override
//    public Type visitFunctionLiteral(final CayTheSourceParser.FunctionLiteralContext ctx) {
//        final List<Identifier> arguments = new ArrayList<>();
//
//        if (ctx.arguments != null) {
//            for (final TerminalNode identifier : ctx.arguments.IDENTIFIER()) {
//                arguments.add(new Identifier(identifier.getText(), cretePosition(identifier.getSymbol())));
//            }
//        }
//
//        final List<AstNode> body = new ArrayList<>();
//
//        for (final CayTheSourceParser.StatementContext statement : ctx.body.statement()) {
//            final AstNode node = visit(statement);
//
//            if (NoOperation.isNoop(node)) {
//                continue;
//            }
//
//            if (Statement.isEmpty(node)) {
//                continue;
//            }
//
//            body.add(node);
//        }
//
//        return new FunctionLiteral(arguments, body, cretePosition(ctx.getStart()));
//    }

//    @Override
//    public Type visitArrayLiteral(final CayTheSourceParser.ArrayLiteralContext ctx) {
//        final List<AstNode> values = new ArrayList<>();
//
//        for (final CayTheSourceParser.ExpressionContext expression : ctx.values.expression()) {
//            values.add(visit(expression));
//        }
//
//        return new ArrayLiteral(values, cretePosition(ctx.getStart()));
//    }

//    @Override
//    public Type visitHashLiteral(final CayTheSourceParser.HashLiteralContext ctx) {
//        final Map<AstNode, AstNode> values = new HashMap<>();
//
//        if (ctx.values == null) {
//            return new HashLiteral(values, cretePosition(ctx.getStart()));
//        }
//
//        for (final CayTheSourceParser.HashPairContext pair : ctx.values.hashPair()) {
//            final AstNode key = visit(pair.key);
//
//            if (values.containsKey(key)) {
//                throw newError(pair.key.getStart(), "Duplicate key given '%s'!", key);
//            }
//
//            if (pair.value == null) {
//                throw newError(pair.key.getStart(), "Missing value for key '%s'!", key);
//            }
//
//            values.put(key, visit(pair.value));
//        }
//
//        return new HashLiteral(values, cretePosition(ctx.getStart()));
//    }

}

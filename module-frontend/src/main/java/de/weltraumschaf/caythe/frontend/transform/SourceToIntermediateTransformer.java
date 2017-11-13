package de.weltraumschaf.caythe.frontend.transform;

import de.weltraumschaf.caythe.frontend.CayTheSourceBaseVisitor;
import de.weltraumschaf.caythe.frontend.CayTheSourceParser;
import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.caythe.intermediate.ast.*;
import de.weltraumschaf.caythe.intermediate.model.*;
import de.weltraumschaf.commons.validate.Validate;
import org.antlr.v4.runtime.Token;

import java.nio.file.Path;
import java.util.*;

import static de.weltraumschaf.caythe.frontend.SyntaxError.newError;
import static de.weltraumschaf.caythe.frontend.SyntaxError.newUnsupportedOperatorError;

/**
 * Default implementation which converts the parsed tree from a source file into intermediate model.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class SourceToIntermediateTransformer extends CayTheSourceBaseVisitor<Type> {

    private final Deque<AstNode> currentNode = new ArrayDeque<>();
    private final Type.TypeBuilder builder = new Type.TypeBuilder();
    private final String file;

    public SourceToIntermediateTransformer(final Path file) {
        super();
        this.file = Validate.notNull(file, "file").toString();
        this.builder.setName(TypeName.fromFileName(file));
    }

    private Position cretePosition(final Token t) {
        return new Position(file, t.getLine(), t.getCharPositionInLine());
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

    @Override
    public Type visitImportDeclaration(final CayTheSourceParser.ImportDeclarationContext ctx) {
        final TypeName fqn = TypeName.fromFullQualifiedName(ctx.name.getText());
        final String alias = null == ctx.alias ? "" : ctx.alias.getText();
        builder.addImport(new Import(fqn, alias));
        return defaultResult();
    }

    @Override
    public Type visitDelegateDeclaration(final CayTheSourceParser.DelegateDeclarationContext ctx) {
        builder.addDelegate(new Delegate(ctx.delegate.getText()));
        return defaultResult();
    }

    @Override
    public Type visitPropertyDeclaration(final CayTheSourceParser.PropertyDeclarationContext ctx) {
        final Visibility visibility = Visibility.valueOf(ctx.visibility.getText().toUpperCase());
        final String type = ctx.propertyType.getText();
        final String name = ctx.propertyName.getText();
        Method getter = Method.NONE;
        Method setter = Method.NONE;

        if (null != ctx.propertyAccessor()) {
            if (null != ctx.propertyAccessor().propertyGetter()) {
                // TODO Implement non default getter.
                getter = Property.defaultGetter(name, visibility, type);
            }

            if (null != ctx.propertyAccessor().propertySetter()) {
                // TODO Implement non default setter.
                setter = Property.defaultSetter(name, visibility, type);
            }
        }

        builder.addProperty(new Property(name, visibility, type, getter, setter));
        return defaultResult();
    }

    @Override
    public Type visitStatementList(final CayTheSourceParser.StatementListContext ctx) {
        final Collection<AstNode> statements = new ArrayList<>();

        for (final CayTheSourceParser.StatementContext statement : ctx.statement()) {
            visit(statement);
            statements.add(currentNode.pop());
        }

        currentNode.push(new StatementList(statements, cretePosition(ctx.start)));
        return defaultResult();
    }

    @Override
    public Type visitLetStatement(final CayTheSourceParser.LetStatementContext ctx) {
        final BinaryOperation assignment;
        final String type = ctx.variableType.getText();
        final Identifier name;

        if (null == ctx.assignStatement()) {
            // This is let w/o assignment: let Type a
            name = new Identifier(ctx.variableName.getText(), cretePosition(ctx.variableName));
            assignment = new BinaryOperation(
                BinaryOperation.Operator.ASSIGN,
                name,
                new NilLiteral(cretePosition(ctx.getStart())),
                cretePosition(ctx.KW_LET().getSymbol()));
        } else {
            // This is let w/ assignment: let Integer a = 1 + 2
            final CayTheSourceParser.AssignExpressionContext assignmentExpression
                = ctx.assignStatement().assignExpression();
            name = new Identifier(
                assignmentExpression.identifier.getText(),
                cretePosition(assignmentExpression.identifier.getStart()));
            visit(assignmentExpression.value);
            assignment = new BinaryOperation(
                BinaryOperation.Operator.ASSIGN,
                name,
                currentNode.pop(),
                cretePosition(ctx.KW_LET().getSymbol()));
        }

        currentNode.push(new Let(type, assignment, cretePosition(ctx.getStart())));
        return defaultResult();
    }

    @Override
    public Type visitConstStatement(final CayTheSourceParser.ConstStatementContext ctx) {
        final String type = ctx.constantType.getText();
        final CayTheSourceParser.AssignExpressionContext assignment = ctx.assignStatement().assignExpression();
        final Identifier name = new Identifier(
            assignment.identifier.getText(),
            cretePosition(assignment.identifier.start));
        visit(assignment.expression());
        final Const constant = new Const(
            type,
            new BinaryOperation(
                BinaryOperation.Operator.ASSIGN,
                name,
                currentNode.pop(),
                cretePosition(ctx.assignStatement().getStart())),
            cretePosition(ctx.getStart())
        );
        currentNode.push(constant);
        return defaultResult();
    }

    @Override
    public Type visitAssignStatement(final CayTheSourceParser.AssignStatementContext ctx) {
        final CayTheSourceParser.AssignExpressionContext assignment = ctx.assignExpression();
        final Identifier identifier = new Identifier(
            assignment.identifier.getText(),
            cretePosition(assignment.identifier.getStart()));
        visit(assignment.value);
        final BinaryOperation operation = new BinaryOperation(
            BinaryOperation.Operator.ASSIGN,
            identifier,
            currentNode.pop(),
            cretePosition(assignment.OP_ASSIGN().getSymbol()));
        currentNode.push(operation);
        return defaultResult();
    }

    @Override
    public Type visitReturnStatement(final CayTheSourceParser.ReturnStatementContext ctx) {
        if (ctx.value == null) {
            currentNode.push(new NilLiteral(cretePosition(ctx.start)));
        } else {
            visit(ctx.value);
        }

        currentNode.push(new Return(currentNode.pop(), cretePosition(ctx.KW_RETURN().getSymbol())));
        return defaultResult();
    }

    @Override
    public Type visitBreakStatement(final CayTheSourceParser.BreakStatementContext ctx) {
        currentNode.push(new Break(cretePosition(ctx.start)));
        return defaultResult();
    }

    @Override
    public Type visitContinueStatement(final CayTheSourceParser.ContinueStatementContext ctx) {
        currentNode.push(new Continue(cretePosition(ctx.start)));
        return defaultResult();
    }

    @Override
    public Type visitExpressionStatement(final CayTheSourceParser.ExpressionStatementContext ctx) {
        visit(ctx.expression());
        return defaultResult();
    }

    @Override
    public Type visitIfExpression(final CayTheSourceParser.IfExpressionContext ctx) {
        visit(ctx.condition);
        final AstNode condition = currentNode.pop();
        visit(ctx.consequence.statements);
        final AstNode consequence = currentNode.pop();
        final AstNode alternative;

        if (ctx.alternative == null) {
            alternative = new NoOperation();
        } else {
            visit(ctx.alternative.statements);
            alternative = currentNode.pop();
        }

        currentNode.push(new IfExpression(condition, consequence, alternative, cretePosition(ctx.getStart())));
        return defaultResult();
    }

    @Override
    public Type visitEndlessLoopExpression(final CayTheSourceParser.EndlessLoopExpressionContext ctx) {
        visitLoopBody(ctx.body.statement());
        final AstNode body = currentNode.pop();
        // In the endless loop version the condition is always true.
        currentNode.push(new Loop(BooleanLiteral.TRUE, body, cretePosition(ctx.getStart())));
        return defaultResult();
    }

    @Override
    public Type visitConditionalLoopExpression(final CayTheSourceParser.ConditionalLoopExpressionContext ctx) {
        visit(ctx.condition);
        final AstNode condition = currentNode.pop();
        visitLoopBody(ctx.body.statement());
        final AstNode body = currentNode.pop();
        currentNode.push(new Loop(condition, body, cretePosition(ctx.getStart())));
        return defaultResult();
    }

    @Override
    public Type visitTraditionalLoopExpression(final CayTheSourceParser.TraditionalLoopExpressionContext ctx) {
        visit(ctx.init);
        final AstNode init = currentNode.pop();
        visit(ctx.condition);
        final AstNode condition = currentNode.pop();
        visit(ctx.post);
        final AstNode post = currentNode.pop();
        visitLoopBody(ctx.body.statement());
        final AstNode body = currentNode.pop();
        currentNode.push(new Loop(init, condition, post, body, cretePosition(ctx.getStart())));
        return defaultResult();
    }

    private void visitLoopBody(final List<CayTheSourceParser.StatementContext> body) {
        final Collection<AstNode> statements = new ArrayList<>();

        for (final CayTheSourceParser.StatementContext statement : body) {
            visit(statement);
            final AstNode node = currentNode.pop();

            if (NoOperation.isNoop(node) || Statement.isEmpty(node)) {
                continue;
            }

            statements.add(node);
        }

        currentNode.push(new StatementList(statements, cretePosition(body.get(0).getStart())));
    }

    @Override
    public Type visitMethodCallExpression(final CayTheSourceParser.MethodCallExpressionContext ctx) {
        final Identifier identifier = new Identifier(ctx.identifier.getText(), cretePosition(ctx.identifier.getStart()));
        final List<AstNode> parameters = new ArrayList<>();
        final List<CayTheSourceParser.MethodParameterContext> parametersExpressions
            = ctx.arguments == null ?
            Collections.emptyList() :
            ctx.arguments.methodParameter();

        for (final CayTheSourceParser.MethodParameterContext parameterExpression : parametersExpressions) {
            visit(parameterExpression);
            parameters.add(currentNode.pop());
        }

        currentNode.push(new FunctionCall(identifier, parameters, cretePosition(ctx.identifier.getStart())));
        return defaultResult();
    }

    @Override
    public Type visitMethodParameter(final CayTheSourceParser.MethodParameterContext ctx) {
        final Identifier name = new Identifier(ctx.parameterName.getText(), cretePosition(ctx.parameterName));
        visit(ctx.parameterValue);
        currentNode.push(new MethodParameter(name, currentNode.pop(), cretePosition(ctx.getStart())));
        return defaultResult();
    }

    @Override
    public Type visitSubscriptExpression(final CayTheSourceParser.SubscriptExpressionContext ctx) {
        visit(ctx.identifier);
        final AstNode identifier = currentNode.pop();
        visit(ctx.index);
        final AstNode index = currentNode.pop();
        currentNode.push(new Subscript(identifier, index, cretePosition(ctx.identifier.getStart())));
        return defaultResult();
    }

    @Override
    public Type visitEqualOperation(final CayTheSourceParser.EqualOperationContext ctx) {
        final Set<BinaryOperation.Operator> allowed = EnumSet.of(
            BinaryOperation.Operator.EQUAL,
            BinaryOperation.Operator.NOT_EQUAL);
        final BinaryOperation.Operator operator = BinaryOperation.Operator.forLiteral(ctx.operator.getText());
        final Position operatorPosition = cretePosition(ctx.operator);
        final CayTheSourceParser.ExpressionContext firstOperandExpression = ctx.firstOperand;
        final CayTheSourceParser.ExpressionContext secondOperandExpression = ctx.secondOperand;

        if (allowed.contains(operator)) {
            visitBinaryOperator(operator, operatorPosition, firstOperandExpression, secondOperandExpression);
        } else {
            throw newUnsupportedOperatorError(ctx.operator);
        }

        return defaultResult();
    }

    @Override
    public Type visitRelationOperation(final CayTheSourceParser.RelationOperationContext ctx) {
        final Set<BinaryOperation.Operator> allowed = EnumSet.of(
            BinaryOperation.Operator.LESS_THAN,
            BinaryOperation.Operator.LESS_THAN_EQUAL,
            BinaryOperation.Operator.GREATER_THAN,
            BinaryOperation.Operator.GREATER_THAN_EQUAL);
        final BinaryOperation.Operator operator = BinaryOperation.Operator.forLiteral(ctx.operator.getText());
        final Position operatorPosition = cretePosition(ctx.operator);
        final CayTheSourceParser.ExpressionContext firstOperandExpression = ctx.firstOperand;
        final CayTheSourceParser.ExpressionContext secondOperandExpression = ctx.secondOperand;

        if (allowed.contains(operator)) {
            visitBinaryOperator(operator, operatorPosition, firstOperandExpression, secondOperandExpression);
        } else {
            throw newUnsupportedOperatorError(ctx.operator);
        }

        return defaultResult();
    }

    @Override
    public Type visitPowerOperation(final CayTheSourceParser.PowerOperationContext ctx) {
        visit(ctx.firstOperand);
        final AstNode firstOperand = currentNode.pop();
        visit(ctx.secondOperand);
        final AstNode secondOperand = currentNode.pop();
        currentNode.push(new BinaryOperation(
            BinaryOperation.Operator.POWER,
            firstOperand,
            secondOperand,
            cretePosition(ctx.getStart())));
        return defaultResult();
    }

    @Override
    public Type visitAdditiveOperation(final CayTheSourceParser.AdditiveOperationContext ctx) {
        final Set<BinaryOperation.Operator> allowed = EnumSet.of(
            BinaryOperation.Operator.ADDITION,
            BinaryOperation.Operator.SUBTRACTION);
        final BinaryOperation.Operator operator = BinaryOperation.Operator.forLiteral(ctx.operator.getText());
        final Position operatorPosition = cretePosition(ctx.operator);
        final CayTheSourceParser.ExpressionContext firstOperandExpression = ctx.firstOperand;
        final CayTheSourceParser.ExpressionContext secondOperandExpression = ctx.secondOperand;

        if (allowed.contains(operator)) {
            visitBinaryOperator(operator, operatorPosition, firstOperandExpression, secondOperandExpression);
        } else {
            throw newUnsupportedOperatorError(ctx.operator);
        }

        return defaultResult();
    }

    @Override
    public Type visitMultiplicativeOperation(final CayTheSourceParser.MultiplicativeOperationContext ctx) {
        final Set<BinaryOperation.Operator> allowed = EnumSet.of(
            BinaryOperation.Operator.MULTIPLICATION,
            BinaryOperation.Operator.DIVISION,
            BinaryOperation.Operator.MODULO);
        final BinaryOperation.Operator operator = BinaryOperation.Operator.forLiteral(ctx.operator.getText());
        final Position operatorPosition = cretePosition(ctx.operator);
        final CayTheSourceParser.ExpressionContext firstOperandExpression = ctx.firstOperand;
        final CayTheSourceParser.ExpressionContext secondOperandExpression = ctx.secondOperand;

        if (allowed.contains(operator)) {
            visitBinaryOperator(operator, operatorPosition, firstOperandExpression, secondOperandExpression);
        } else {
            throw newUnsupportedOperatorError(ctx.operator);
        }

        return defaultResult();
    }

    private void visitBinaryOperator(final BinaryOperation.Operator op, final Position pos, final CayTheSourceParser.ExpressionContext first, final CayTheSourceParser.ExpressionContext second) {
        visit(first);
        final AstNode firstOperand = currentNode.pop();
        visit(second);
        final AstNode secondOperand = currentNode.pop();
        currentNode.push(new BinaryOperation(
            op,
            firstOperand,
            secondOperand,
            pos));
    }

    @Override
    public Type visitLogicalOrOperation(final CayTheSourceParser.LogicalOrOperationContext ctx) {
        visit(ctx.firstOperand);
        final AstNode firstOperand = currentNode.pop();
        visit(ctx.secondOperand);
        final AstNode secondOperand = currentNode.pop();
        currentNode.push(new BinaryOperation(
            BinaryOperation.Operator.OR,
            firstOperand,
            secondOperand,
            cretePosition(ctx.getStart())));
        return defaultResult();
    }

    @Override
    public Type visitLogicalAndOperation(final CayTheSourceParser.LogicalAndOperationContext ctx) {
        visit(ctx.firstOperand);
        final AstNode firstOperand = currentNode.pop();
        visit(ctx.secondOperand);
        final AstNode secondOperand = currentNode.pop();
        currentNode.push(new BinaryOperation(
            BinaryOperation.Operator.AND,
            firstOperand,
            secondOperand,
            cretePosition(ctx.getStart())));
        return defaultResult();
    }

    @Override
    public Type visitNegationOperation(final CayTheSourceParser.NegationOperationContext ctx) {
        final Set<UnaryOperation.Operator> allowed = EnumSet.of(
            UnaryOperation.Operator.NEG,
            UnaryOperation.Operator.NOT);
        final UnaryOperation.Operator operator = UnaryOperation.Operator.forLiteral(ctx.operator.getText());

        if (allowed.contains(operator)) {
            visit(ctx.operand);
            currentNode.push(new UnaryOperation(operator, currentNode.pop(), cretePosition(ctx.getStart())));
            return defaultResult();
        } else {
            throw newUnsupportedOperatorError(ctx.operator);
        }
    }

    @Override
    public Type visitNestedExpression(final CayTheSourceParser.NestedExpressionContext ctx) {
        visit(ctx.expression());
        return defaultResult();
    }

    @Override
    public Type visitNilLiteral(final CayTheSourceParser.NilLiteralContext ctx) {
        currentNode.push(new NilLiteral(cretePosition(ctx.start)));
        return defaultResult();
    }

    @Override
    public Type visitBooleanLiteral(final CayTheSourceParser.BooleanLiteralContext ctx) {
        currentNode.push("true".equals(ctx.BOOLEAN().getText())
            ? BooleanLiteral.TRUE
            : BooleanLiteral.FALSE);
        return defaultResult();
    }

    @Override
    public Type visitRealLiteral(final CayTheSourceParser.RealLiteralContext ctx) {
        currentNode.push(new RealLiteral
            (Double.parseDouble((ctx.REAL().getText())),
                cretePosition(ctx.getStart())));
        return defaultResult();
    }

    @Override
    public Type visitIntegerLiteral(final CayTheSourceParser.IntegerLiteralContext ctx) {
        currentNode.push(new IntegerLiteral(
            Long.parseLong(ctx.INTEGER().getText()),
            cretePosition(ctx.getStart())));
        return defaultResult();
    }

    @Override
    public Type visitStringLiteral(final CayTheSourceParser.StringLiteralContext ctx) {
        final String text = ctx.STRING().getText();
        // We must remove the quotes here.
        currentNode.push(new StringLiteral(
            text.substring(1, text.length() - 1),
            cretePosition(ctx.getStart())));
        return defaultResult();
    }

    @Override
    public Type visitIdentifierLiteral(final CayTheSourceParser.IdentifierLiteralContext ctx) {
        currentNode.push(new Identifier(
            ctx.IDENTIFIER().getText(),
            cretePosition(ctx.getStart())));
        return defaultResult();
    }

    @Override
    public Type visitArrayLiteral(final CayTheSourceParser.ArrayLiteralContext ctx) {
        final List<AstNode> values = new ArrayList<>();

        for (final CayTheSourceParser.ExpressionContext expression : ctx.values.expression()) {
            visit(expression);
            values.add(currentNode.pop());
        }

        currentNode.push(new ArrayLiteral(values, cretePosition(ctx.getStart())));
        return defaultResult();
    }

    @Override
    public Type visitHashLiteral(final CayTheSourceParser.HashLiteralContext ctx) {
        final Map<AstNode, AstNode> values = new HashMap<>();

        if (ctx.values == null) {
            currentNode.push(new HashLiteral(values, cretePosition(ctx.getStart())));
            return defaultResult();
        }

        for (final CayTheSourceParser.HashPairContext pair : ctx.values.hashPair()) {
            visit(pair.key);
            final AstNode key = currentNode.pop();

            if (values.containsKey(key)) {
                throw newError(pair.key.getStart(), "Duplicate key given '%s'!", key);
            }

            if (pair.value == null) {
                throw newError(pair.key.getStart(), "Missing value for key '%s'!", key);
            }

            visit(pair.value);
            values.put(key, currentNode.pop());
        }

        currentNode.push(new HashLiteral(values, cretePosition(ctx.getStart())));
        return defaultResult();
    }

}

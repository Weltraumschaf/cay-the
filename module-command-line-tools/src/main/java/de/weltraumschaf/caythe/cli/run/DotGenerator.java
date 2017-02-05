package de.weltraumschaf.caythe.cli.run;

import de.weltraumschaf.caythe.intermediate.experimental.AstVisitor;
import de.weltraumschaf.caythe.intermediate.experimental.ast.*;

import java.util.List;
import java.util.Map;
import java.util.Stack;

final class DotGenerator implements AstVisitor<Void> {
    private static final char NL = '\n';
    private static final String INDENTION = "    ";
    private static final String EDGE = " -- ";
    private final StringBuilder buffer = new StringBuilder();
    private final Stack<DotNode> currentNode = new Stack<>();
    private int id = 0;
    private String currentEdgeLabel = "";

    private int nextId() {
        return id++;
    }

    private String generateName(final AstNode node) {
        return node.getClass().getSimpleName() + "_" + nextId();
    }

    private void appendDotNode(final DotNode node) {
        buffer.append(INDENTION).append(node).append(NL);
    }

    private void appendDotEdge(final DotNode n1, final DotNode n2) {
        buffer.append(INDENTION).append(n1.name).append(EDGE).append(n2.name);

        if (!currentEdgeLabel.isEmpty()) {
            buffer.append(' ').append("[label=\"").append(currentEdgeLabel).append("\"]");
            currentEdgeLabel = "";
        }

        buffer.append(';').append(NL);
    }

    String getGraph() {
        return buffer.toString();
    }

    @Override
    public Void visit(final ArrayLiteral node) {
        final DotNode dotNode = new DotNode(generateName(node), "<array>");
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        currentNode.push(dotNode);

        for (final AstNode value : node.getValues()) {
            value.accept(this);
        }

        currentNode.pop();
        return null;
    }

    @Override
    public Void visit(final BinaryOperation node) {
        final DotNode dotNode = new DotNode(generateName(node), node.getOperator().literal());
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        currentNode.push(dotNode);
        node.getLeftOperand().accept(this);
        node.getRightOperand().accept(this);
        currentNode.pop();
        return null;
    }

    @Override
    public Void visit(final BooleanLiteral node) {
        final DotNode dotNode = new DotNode(generateName(node), String.valueOf(node.getValue()));
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        return null;
    }

    @Override
    public Void visit(final Break node) {
        final DotNode dotNode = new DotNode(generateName(node), "<break>");
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        return null;
    }

    @Override
    public Void visit(final Const node) {
        final DotNode dotNode = new DotNode(generateName(node), "<const>");
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        currentNode.push(dotNode);
        node.getAssignment().accept(this);
        currentNode.pop();
        return null;
    }

    @Override
    public Void visit(final Continue node) {
        final DotNode dotNode = new DotNode(generateName(node), "<continue>");
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        return null;
    }

    @Override
    public Void visit(final FloatLiteral node) {
        final DotNode dotNode = new DotNode(generateName(node), String.valueOf(node.getValue()));
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        return null;
    }

    @Override
    public Void visit(final FunctionCall node) {
        final DotNode dotNode = new DotNode(generateName(node), "<fn-call>");
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        currentNode.push(dotNode);
        node.getIdentifier().accept(this);

        for (final AstNode argument : node.getArguments()) {
            argument.accept(this);
        }

        currentNode.pop();
        return null;
    }

    @Override
    public Void visit(final FunctionLiteral node) {
        final DotNode fnDeclDotNode = new DotNode(generateName(node), "<fn-decl>");
        appendDotNode(fnDeclDotNode);
        appendDotEdge(currentNode.peek(), fnDeclDotNode);
        currentNode.push(fnDeclDotNode);
        final List<AstNode> arguments = node.getArguments();

        if (!arguments.isEmpty()) {
            for (final AstNode argument : arguments) {
                currentEdgeLabel = "argument";
                argument.accept(this);
            }
        }

        final List<AstNode> body = node.getBody();

        if (!body.isEmpty()) {
            for (final AstNode statement : body) {
                currentEdgeLabel = "body";
                statement.accept(this);
            }
        }

        currentNode.pop();
        return null;
    }

    @Override
    public Void visit(final HashLiteral node) {
        final DotNode hashDotNode = new DotNode(generateName(node), "<hash>");
        appendDotNode(hashDotNode);
        appendDotEdge(currentNode.peek(), hashDotNode);
        currentNode.push(hashDotNode);

        for (final Map.Entry<AstNode, AstNode> pair : node.getValues().entrySet()) {
            final DotNode pairDotNode = new DotNode(generateName(node), "<pair>");
            appendDotNode(pairDotNode);
            appendDotEdge(currentNode.peek(), pairDotNode);
            currentNode.push(pairDotNode);
            pair.getKey().accept(this);
            pair.getValue().accept(this);
            currentNode.pop();
        }

        currentNode.pop();
        return null;
    }

    @Override
    public Void visit(final Identifier node) {
        final DotNode dotNode = new DotNode(generateName(node), node.getName());
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        return null;
    }

    @Override
    public Void visit(final IfExpression node) {
        final DotNode ifDotNode = new DotNode(generateName(node), "<if>");
        appendDotNode(ifDotNode);
        appendDotEdge(currentNode.peek(), ifDotNode);
        currentNode.push(ifDotNode);
        currentEdgeLabel = "condition";
        node.getCondition().accept(this);
        currentEdgeLabel = "consequence";
        node.getConsequence().accept(this);
        currentEdgeLabel = "alternative";
        node.getAlternative().accept(this);
        currentEdgeLabel = "";
        currentNode.pop();
        return null;
    }

    @Override
    public Void visit(final IntegerLiteral node) {
        final DotNode dotNode = new DotNode(generateName(node), String.valueOf(node.getValue()));
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        return null;
    }

    @Override
    public Void visit(final Let node) {
        final DotNode dotNode = new DotNode(generateName(node), "<let>");
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        currentNode.push(dotNode);
        node.getAssignment().accept(this);
        currentNode.pop();
        return null;
    }

    @Override
    public Void visit(final Loop node) {
        final DotNode dotNode = new DotNode(generateName(node), "<loop>");
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        currentNode.push(dotNode);

        for (final AstNode statement : node.getStatements()) {
            statement.accept(this);
        }

        currentNode.pop();
        return null;
    }

    @Override
    public Void visit(final NoOperation node) {
        final DotNode dotNode = new DotNode(generateName(node), "<noop>");
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        return null;
    }

    @Override
    public Void visit(final NullLiteral node) {
        final DotNode dotNode = new DotNode(generateName(node), "<null>");
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        return null;
    }

    @Override
    public Void visit(final Return node) {
        final DotNode dotNode = new DotNode(generateName(node), "<return>");
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        currentNode.push(dotNode);
        node.getResult().accept(this);
        currentNode.pop();
        return null;
    }

    @Override
    public Void visit(final Statement node) {
        final DotNode dotNode = new DotNode(generateName(node), "<statement>");
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        currentNode.push(dotNode);

        for (final AstNode child : node.getChildren()) {
            child.accept(this);
        }

        currentNode.pop();
        return null;
    }

    @Override
    public Void visit(final StringLiteral node) {
        final DotNode dotNode = new DotNode(generateName(node), "'" + node.getValue() + "'");
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        return null;
    }

    @Override
    public Void visit(final Subscript node) {
        final DotNode dotNode = new DotNode(generateName(node), "[]");
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        currentNode.push(dotNode);
        node.getIdentifier().accept(this);
        node.getIndex().accept(this);
        currentNode.pop();
        return null;
    }

    @Override
    public Void visit(final UnaryOperation node) {
        final DotNode dotNode = new DotNode(generateName(node), node.getOperator().literal());
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        currentNode.push(dotNode);
        node.getOperand().accept(this);
        currentNode.pop();
        return null;
    }

    @Override
    public Void visit(final Unit node) {
        buffer.append("graph AST {").append(NL);
        final DotNode dotNode = new DotNode(generateName(node), "<unit>");
        appendDotNode(dotNode);
        currentNode.push(dotNode);

        for (final AstNode statement : node.getStatements()) {
            statement.accept(this);
        }

        buffer.append('}').append(NL);
        currentNode.pop();
        return null;
    }

    public static final class DotNode {
        private final String name;
        private final String label;

        DotNode(final String name, final String label) {
            super();
            this.name = name;
            this.label = label;
        }

        @Override
        public String toString() {
            return String.format("%s [label=\"%s\"];", name, label);
        }
    }
}

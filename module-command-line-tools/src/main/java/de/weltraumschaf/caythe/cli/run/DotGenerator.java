package de.weltraumschaf.caythe.cli.run;

import de.weltraumschaf.caythe.intermediate.model.ast.AstVisitor;
import de.weltraumschaf.caythe.intermediate.model.ast.*;

import java.util.List;
import java.util.Map;
import java.util.Stack;

final class DotGenerator implements AstVisitor<Object> {
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

    private String formatNode(final AstNode node) {
        return String.format("<%s>", node.getNodeName());
    }

    String getGraph() {
        return buffer.toString();
    }

    @Override
    public Object defaultReturn() {
        return new Object();
    }

    @Override
    public Object visit(final ArrayLiteral node) {
        final DotNode dotNode = new DotNode(generateName(node), formatNode(node));
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        currentNode.push(dotNode);

        for (final AstNode value : node.getValues()) {
            value.accept(this);
        }

        currentNode.pop();
        return defaultReturn();
    }

    @Override
    public Object visit(final BinaryOperation node) {
        final DotNode dotNode = new DotNode(generateName(node), formatNode(node));
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        currentNode.push(dotNode);
        node.getLeftOperand().accept(this);
        node.getRightOperand().accept(this);
        currentNode.pop();
        return defaultReturn();
    }

    @Override
    public Object visit(final BooleanLiteral node) {
        final DotNode dotNode = new DotNode(generateName(node), String.valueOf(node.getValue()));
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        return defaultReturn();
    }

    @Override
    public Object visit(final Break node) {
        final DotNode dotNode = new DotNode(generateName(node), formatNode(node));
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        return defaultReturn();
    }

    @Override
    public Object visit(final Const node) {
        final DotNode dotNode = new DotNode(generateName(node), formatNode(node));
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        currentNode.push(dotNode);
        node.getAssignment().accept(this);
        currentNode.pop();
        return defaultReturn();
    }

    @Override
    public Object visit(final Continue node) {
        final DotNode dotNode = new DotNode(generateName(node), formatNode(node));
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        return defaultReturn();
    }

    @Override
    public Object visit(final RealLiteral node) {
        final DotNode dotNode = new DotNode(generateName(node), String.valueOf(node.getValue()));
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        return defaultReturn();
    }

    @Override
    public Object visit(final MethodCall node) {
        final DotNode dotNode = new DotNode(generateName(node), formatNode(node));
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        currentNode.push(dotNode);
        node.getIdentifier().accept(this);

        for (final AstNode argument : node.getArguments()) {
            argument.accept(this);
        }

        currentNode.pop();
        return defaultReturn();
    }

    @Override
    public Object visit(final MethodDeclaration node) {
        final DotNode fnDeclDotNode = new DotNode(generateName(node), formatNode(node));
        appendDotNode(fnDeclDotNode);
        appendDotEdge(currentNode.peek(), fnDeclDotNode);
        currentNode.push(fnDeclDotNode);
        final List<Identifier> arguments = node.getArguments();

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
        return defaultReturn();
    }

    @Override
    public Object visit(final HashLiteral node) {
        final DotNode hashDotNode = new DotNode(generateName(node), formatNode(node));
        appendDotNode(hashDotNode);
        appendDotEdge(currentNode.peek(), hashDotNode);
        currentNode.push(hashDotNode);

        for (final Map.Entry<AstNode, AstNode> pair : node.getValues().entrySet()) {
            final DotNode pairDotNode = new DotNode(generateName(node), formatNode(node));
            appendDotNode(pairDotNode);
            appendDotEdge(currentNode.peek(), pairDotNode);
            currentNode.push(pairDotNode);
            pair.getKey().accept(this);
            pair.getValue().accept(this);
            currentNode.pop();
        }

        currentNode.pop();
        return defaultReturn();
    }

    @Override
    public Object visit(final Identifier node) {
        final DotNode dotNode = new DotNode(generateName(node), formatNode(node));
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        return defaultReturn();
    }

    @Override
    public Object visit(final IfExpression node) {
        final DotNode ifDotNode = new DotNode(generateName(node), formatNode(node));
        appendDotNode(ifDotNode);
        appendDotEdge(currentNode.peek(), ifDotNode);
        currentNode.push(ifDotNode);
        currentEdgeLabel = "getCondition";
        node.getCondition().accept(this);
        currentEdgeLabel = "consequence";
        node.getConsequence().accept(this);
        currentEdgeLabel = "alternative";
        node.getAlternative().accept(this);
        currentEdgeLabel = "";
        currentNode.pop();
        return defaultReturn();
    }

    @Override
    public Object visit(final IntegerLiteral node) {
        final DotNode dotNode = new DotNode(generateName(node), String.valueOf(node.getValue()));
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        return defaultReturn();
    }

    @Override
    public Object visit(final Let node) {
        final DotNode dotNode = new DotNode(generateName(node), formatNode(node));
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        currentNode.push(dotNode);
        node.getAssignment().accept(this);
        currentNode.pop();
        return defaultReturn();
    }

    @Override
    public Object visit(final Loop node) {
        final DotNode dotNode = new DotNode(generateName(node), formatNode(node));
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        currentNode.push(dotNode);

        // FIXME Fix loop getStatements.
//        for (final AstNode statement : node.getStatements()) {
//            statement.accept(this);
//        }

        currentNode.pop();
        return defaultReturn();
    }

    @Override
    public Object visit(final NoOperation node) {
        final DotNode dotNode = new DotNode(generateName(node), formatNode(node));
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        return defaultReturn();
    }

    @Override
    public Object visit(final NilLiteral node) {
        final DotNode dotNode = new DotNode(generateName(node), formatNode(node));
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        return defaultReturn();
    }

    @Override
    public Object visit(final Return node) {
        final DotNode dotNode = new DotNode(generateName(node), formatNode(node));
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        currentNode.push(dotNode);
        node.getValue().accept(this);
        currentNode.pop();
        return defaultReturn();
    }

    @Override
    public Object visit(final StringLiteral node) {
        final DotNode dotNode = new DotNode(generateName(node), "'" + node.getValue() + "'");
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        return defaultReturn();
    }

    @Override
    public Object visit(final Subscript node) {
        final DotNode dotNode = new DotNode(generateName(node), formatNode(node));
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        currentNode.push(dotNode);
        node.getIdentifier().accept(this);
        node.getIndex().accept(this);
        currentNode.pop();
        return defaultReturn();
    }

    @Override
    public Object visit(final UnaryOperation node) {
        final DotNode dotNode = new DotNode(generateName(node), formatNode(node));
        appendDotNode(dotNode);
        appendDotEdge(currentNode.peek(), dotNode);
        currentNode.push(dotNode);
        node.getOperand().accept(this);
        currentNode.pop();
        return defaultReturn();
    }

    @Override
    public Object visit(final Block node) {
        buffer.append("graph AST {").append(NL);
        final DotNode dotNode = new DotNode(generateName(node), formatNode(node));
        appendDotNode(dotNode);
        currentNode.push(dotNode);

        for (final AstNode statement : node.getChildren()) {
            statement.accept(this);
        }

        buffer.append('}').append(NL);
        currentNode.pop();
        return defaultReturn();
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

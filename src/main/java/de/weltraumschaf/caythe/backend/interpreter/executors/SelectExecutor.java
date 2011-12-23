package de.weltraumschaf.caythe.backend.interpreter.executors;

import de.weltraumschaf.caythe.backend.interpreter.Executor;
import de.weltraumschaf.caythe.intermediate.CodeNode;
import java.util.ArrayList;
import java.util.HashMap;

import static de.weltraumschaf.caythe.intermediate.codeimpl.CodeKeyImpl.*;
/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class SelectExecutor extends StatementExecutor {

    public SelectExecutor(Executor parent) {
        super(parent);
    }

    // Jump table cache: entry key is a SELECT node,
    //                   entry value is the jump table.
    // Jump table: entry key is a selection value,
    //             entry value is the branch statement.
    private static HashMap<CodeNode, HashMap<Object, CodeNode>> jumpCache =
        new HashMap<CodeNode, HashMap<Object, CodeNode>>();

    @Override
    public Object execute(CodeNode node) {
        // Is there already an entry for this SELECT node in the
        // jump table cache? If not, create a jump table entry.
        HashMap<Object, CodeNode> jumpTable = jumpCache.get(node);
        if (jumpTable == null) {
            jumpTable = createJumpTable(node);
            jumpCache.put(node, jumpTable);
        }

        // Get the SELECT node's children.
        ArrayList<CodeNode> selectChildren = node.getChildren();
        CodeNode exprNode = selectChildren.get(0);

        // Evaluate the SELECT expression.
        ExpressionExecutor expressionExecutor = new ExpressionExecutor(this);
        Object selectValue = expressionExecutor.execute(exprNode);

        // If there is a selection, execute the SELECT_BRANCH's statement.
        CodeNode statementNode = jumpTable.get(selectValue);
        if (statementNode != null) {
            StatementExecutor statementExecutor = new StatementExecutor(this);
            statementExecutor.execute(statementNode);
        }

        ++executionCount;  // count the SELECT statement itself
        return null;
    }

    private HashMap<Object, CodeNode> createJumpTable(CodeNode node) {
        HashMap<Object, CodeNode> jumpTable = new HashMap<Object, CodeNode>();

        // Loop over children that are SELECT_BRANCH nodes.
        ArrayList<CodeNode> selectChildren = node.getChildren();
        for (int i = 1; i < selectChildren.size(); ++i) {
            CodeNode branchNode = selectChildren.get(i);
            CodeNode constantsNode = branchNode.getChildren().get(0);
            CodeNode statementNode = branchNode.getChildren().get(1);

            // Loop over the constants children of the branch's CONSTANTS_NODE.
            ArrayList<CodeNode> constantsList = constantsNode.getChildren();
            for (CodeNode constantNode : constantsList) {

                // Create a jump table entry.
                Object value = constantNode.getAttribute(VALUE);
                jumpTable.put(value, statementNode);
            }
        }

        return jumpTable;
    }

}

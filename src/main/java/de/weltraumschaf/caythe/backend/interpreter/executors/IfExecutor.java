package de.weltraumschaf.caythe.backend.interpreter.executors;

import de.weltraumschaf.caythe.backend.interpreter.Executor;
import de.weltraumschaf.caythe.intermediate.CodeNode;
import java.util.ArrayList;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class IfExecutor extends StatementExecutor {

    public IfExecutor(Executor parent) {
        super(parent);
    }

    @Override
    public Object execute(CodeNode node) {
        // Get the IF node's children.
        ArrayList<CodeNode> children = node.getChildren();
        CodeNode exprNode = children.get(0);
        CodeNode thenStmtNode = children.get(1);
        CodeNode elseStmtNode = children.size() > 2 ? children.get(2) : null;

        ExpressionExecutor expressionExecutor = new ExpressionExecutor(this);
        StatementExecutor statementExecutor = new StatementExecutor(this);

        // Evaluate the expression to determine which statement to execute.
        boolean b = (Boolean) expressionExecutor.execute(exprNode);
        if (b) {
            statementExecutor.execute(thenStmtNode);
        }
        else if (elseStmtNode != null) {
            statementExecutor.execute(elseStmtNode);
        }

        ++executionCount;  // count the IF statement itself
        return null;
    }

}

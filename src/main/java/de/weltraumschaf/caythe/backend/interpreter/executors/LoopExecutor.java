package de.weltraumschaf.caythe.backend.interpreter.executors;

import de.weltraumschaf.caythe.backend.interpreter.Executor;
import de.weltraumschaf.caythe.intermediate.CodeNode;
import de.weltraumschaf.caythe.intermediate.codeimpl.CodeNodeTypeImpl;
import java.util.ArrayList;

import static de.weltraumschaf.caythe.intermediate.codeimpl.CodeNodeTypeImpl.*;
/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class LoopExecutor extends StatementExecutor {

    public LoopExecutor(Executor parent) {
        super(parent);
    }

    @Override
    public Object execute(CodeNode node) {
        boolean exitLoop = false;
        CodeNode exprNode = null;
        ArrayList<CodeNode> loopChildren = node.getChildren();

        ExpressionExecutor expressionExecutor = new ExpressionExecutor(this);
        StatementExecutor statementExecutor = new StatementExecutor(this);

        // Loop until the TEST expression value is true.
        while (!exitLoop) {
            ++executionCount;  // count the loop statement itself

            // Execute the children of the LOOP node.
            for (CodeNode child : loopChildren) {
                CodeNodeTypeImpl childType =
                                      (CodeNodeTypeImpl) child.getType();

                // TEST node?
                if (childType == TEST) {
                    if (exprNode == null) {
                        exprNode = child.getChildren().get(0);
                    }
                    exitLoop = (Boolean) expressionExecutor.execute(exprNode);
                }
                // Statement node.
                else {
                    statementExecutor.execute(child);
                }

                // Exit if the TEST expression value is true,
                if (exitLoop) {
                    break;
                }
            }
        }

        return null;
    }

}

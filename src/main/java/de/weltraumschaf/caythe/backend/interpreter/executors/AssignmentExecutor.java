package de.weltraumschaf.caythe.backend.interpreter.executors;

import de.weltraumschaf.caythe.backend.interpreter.Executor;
import de.weltraumschaf.caythe.intermediate.CodeNode;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;
import de.weltraumschaf.caythe.message.Message;
import java.util.ArrayList;

import static de.weltraumschaf.caythe.intermediate.codeimpl.CodeKeyImpl.*;
import static de.weltraumschaf.caythe.intermediate.symboltableimpl.SymbolTableKeyImpl.*;
import static de.weltraumschaf.caythe.message.MessageType.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class AssignmentExecutor extends StatementExecutor {

    public AssignmentExecutor(Executor parent) {
        super(parent);
    }

    @Override
    public Object execute(CodeNode node) {
        // The ASSIGN node's children are the target variable and the expression.
        ArrayList<CodeNode> children = node.getChildren();
        CodeNode variableNode   = children.get(0);
        CodeNode expressionNode = children.get(1);

        // Execute expresion and get its value.
        ExpressionExecutor expresionExecutor = new ExpressionExecutor(this);
        Object value = expresionExecutor.execute(expressionNode);
        SymbolTableEntry variableId = (SymbolTableEntry) variableNode.getAttribute(ID);
        variableId.setAttribute(DATA_VALUE, value);
        sendMessage(node, variableId.getName(), value);
        ++executionCount;
        return null;
    }

    private void sendMessage(CodeNode node, String varibleName, Object value) {
        Object lineNumber = node.getAttribute(LINE);

        if (null != lineNumber) {
            sendMessage(new Message(ASSIGN, new Object[] {
                lineNumber,
                varibleName,
                value
            }));
        }
    }
}

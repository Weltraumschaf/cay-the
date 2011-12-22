package de.weltraumschaf.caythe.backend.interpreter.executors;

import de.weltraumschaf.caythe.backend.interpreter.Executor;
import de.weltraumschaf.caythe.intermediate.CodeNode;
import de.weltraumschaf.caythe.intermediate.codeimpl.CodeNodeTypeImpl;
import de.weltraumschaf.caythe.message.Message;

import static de.weltraumschaf.caythe.message.MessageType.*;
import static de.weltraumschaf.caythe.backend.interpreter.RuntimeErrorCode.*;
import static de.weltraumschaf.caythe.intermediate.codeimpl.CodeKeyImpl.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class StatementExecutor extends Executor {

    public StatementExecutor(Executor parent) {
        super(parent);
    }

    public Object execute(CodeNode node) {
        CodeNodeTypeImpl nodeType = (CodeNodeTypeImpl) node.getType();
        // Send message about current source line
        sendSourceLineMessage(node);

        switch (nodeType) {
            case COMPOUND: {
                CompundExecutor compundExecutr = new CompundExecutor(this);
                return compundExecutr.execute(node);
            }
            case ASSIGN: {
                AssignmentExecutor assignementExecutor = new AssignmentExecutor(this);
                return assignementExecutor.execute(node);
            }
            case NO_OP: return null;
            default: {
                errorHandler.flag(node, UNIMPLEMENTED_FEATURE, this);
                return null;
            }
        }
    }

    private void sendSourceLineMessage(CodeNode node) {
        Object lineNumber = node.getAttribute(LINE);

        if (null != lineNumber) {
            sendMessage(new Message(SOURCE_LINE, lineNumber));
        }
    }

}

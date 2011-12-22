package de.weltraumschaf.caythe.backend.interpreter;

import de.weltraumschaf.caythe.backend.Backend;
import de.weltraumschaf.caythe.intermediate.CodeNode;
import de.weltraumschaf.caythe.message.Message;

import static de.weltraumschaf.caythe.intermediate.codeimpl.CodeKeyImpl.*;
import static de.weltraumschaf.caythe.message.MessageType.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class RuntimeErrorHandler {

    private static final int MAX_ERRORS = 5;
    private static int errorCount = 0;

    public void flag(CodeNode node, RuntimeErrorCode errorCode, Backend backend) {
        String lineNumber = null;

        // Look for the ancestor statement node with a line number attribute.
        while ((null != node) && (null == node.getAttribute(LINE))) {
            node = node.getParent();
        }

        backend.sendMessage(new Message(RUNTIME_ERROR, new Object[] {
            errorCode.toString(),
            (Integer) node.getAttribute(LINE)
        }));

        if (++errorCount > MAX_ERRORS) {
            System.out.println("*** Aborted after too many runtime errors!");
            System.exit(-1);
        }
    }

    public int getErrorCount() {
        return errorCount;
    }
}

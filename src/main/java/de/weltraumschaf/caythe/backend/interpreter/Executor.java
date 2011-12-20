package de.weltraumschaf.caythe.backend.interpreter;

import de.weltraumschaf.caythe.backend.Backend;
import de.weltraumschaf.caythe.intermediate.Code;
import de.weltraumschaf.caythe.intermediate.SymbolTableStack;
import de.weltraumschaf.caythe.message.Message;
import de.weltraumschaf.caythe.message.MessageType;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class Executor extends Backend {

    @Override
    public void process(Code intermediateCode, SymbolTableStack symbolTableStack) throws Exception {
        long startTime     = System.currentTimeMillis();
        float elapsedTime  = (System.currentTimeMillis() - startTime) / 1000f;
        int executionCount = 0;
        int runtimeErrors  = 0;

        sendMessage(new Message(MessageType.INTERPRETER_SUMMARY, new Number[] {
            executionCount,
            runtimeErrors,
            elapsedTime
        }));
    }

}

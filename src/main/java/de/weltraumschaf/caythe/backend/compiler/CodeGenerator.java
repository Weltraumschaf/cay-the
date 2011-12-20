package de.weltraumschaf.caythe.backend.compiler;

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
public class CodeGenerator extends Backend {

    @Override
    public void process(Code iintermediateCode, SymbolTableStack symbolTableStack) throws Exception {
        long startTime       = System.currentTimeMillis();
        float elapsedTime    = (System.currentTimeMillis() - startTime) / 1000f;
        int instructionCount = 0;

        sendMessage(new Message(MessageType.COMPILER_SUMMARY, new Number[] {
            instructionCount,
            elapsedTime
        }));
    }

}

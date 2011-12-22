package de.weltraumschaf.caythe.backend.interpreter;

import de.weltraumschaf.caythe.backend.Backend;
import de.weltraumschaf.caythe.backend.interpreter.executors.StatementExecutor;
import de.weltraumschaf.caythe.intermediate.Code;
import de.weltraumschaf.caythe.intermediate.CodeNode;
import de.weltraumschaf.caythe.intermediate.SymbolTableStack;
import de.weltraumschaf.caythe.message.Message;

import static de.weltraumschaf.caythe.message.MessageType.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class Executor extends Backend {

    protected static int executionCount;
    protected static RuntimeErrorHandler errorHandler;

    static {
        executionCount = 0;
        errorHandler   = new RuntimeErrorHandler();
    }

    public Executor() {}
    
    public Executor(Executor parent) {
        super();
    }

    @Override
    public void process(Code intermediateCode, SymbolTableStack symbolTableStack) throws Exception {
        this.symbolTableStack = symbolTableStack;
        this.intermediateCode = intermediateCode;
        long startTime = System.currentTimeMillis();

        // Get the root node of the intermediate code and execute.
        CodeNode rootNode = intermediateCode.getRoot();
        StatementExecutor statementExecutor = new StatementExecutor(this);
        statementExecutor.execute(rootNode);

        float elapsedTime = (System.currentTimeMillis() - startTime) / 1000f;
        int runtimeErrors = errorHandler.getErrorCount();

        sendMessage(new Message(INTERPRETER_SUMMARY, new Number[] {
            executionCount,
            runtimeErrors,
            elapsedTime
        }));
    }

}

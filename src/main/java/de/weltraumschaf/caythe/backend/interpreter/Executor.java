package de.weltraumschaf.caythe.backend.interpreter;

import de.weltraumschaf.caythe.backend.interpreter.executors.CallDeclaredExecutor;
import de.weltraumschaf.caythe.intermediate.CodeFactory;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;
import de.weltraumschaf.caythe.backend.Backend;
import de.weltraumschaf.caythe.frontend.Scanner;
import de.weltraumschaf.caythe.frontend.Source;
import de.weltraumschaf.caythe.frontend.pascal.PascalScanner;
import de.weltraumschaf.caythe.intermediate.Code;
import de.weltraumschaf.caythe.intermediate.CodeNode;
import de.weltraumschaf.caythe.intermediate.SymbolTableStack;
import de.weltraumschaf.caythe.message.Message;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;

import static de.weltraumschaf.caythe.message.MessageType.*;
import static de.weltraumschaf.caythe.intermediate.codeimpl.CodeKeyImpl.ID;
import static de.weltraumschaf.caythe.intermediate.codeimpl.CodeNodeTypeImpl.CALL;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class Executor extends Backend {

    protected static int executionCount;
    protected static RuntimeStack runtimeStack;
    protected static RuntimeErrorHandler errorHandler;

    protected static Scanner standardIn;
    protected static PrintWriter standardOut;

    static {
        executionCount = 0;
        runtimeStack   = MemoryFactory.createRuntimeStack();
        errorHandler   = new RuntimeErrorHandler();

        standardIn = new PascalScanner(
                        new Source(
                            new BufferedReader(
                                new InputStreamReader(System.in))));
        standardOut = new PrintWriter(new PrintStream(System.out));
    }

    public Executor() {}

    public Executor(Executor parent) {
        super();
    }

    @Override
    public void process(Code intermediateCode, SymbolTableStack symbolTableStack) throws Exception {
        this.symbolTableStack = symbolTableStack;
        long startTime = System.currentTimeMillis();

        SymbolTableEntry programId = symbolTableStack.getProgramId();

        // Construct an artificial CALL node to the main program.
        CodeNode callNode = CodeFactory.createCodeNode(CALL);
        callNode.setAttribute(ID, programId);

        // Execute the main program.
        CallDeclaredExecutor callExecutor = new CallDeclaredExecutor(this);
        callExecutor.execute(callNode);

        float elapsedTime = (System.currentTimeMillis() - startTime)/1000f;
        int runtimeErrors = errorHandler.getErrorCount();

        // Send the interpreter summary message.
        sendMessage(new Message(INTERPRETER_SUMMARY,
                                new Number[] {executionCount,
                                              runtimeErrors,
                                              elapsedTime}));
    }

}

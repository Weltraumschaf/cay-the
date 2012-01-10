package de.weltraumschaf.caythe.backend.interpreter.executors;

import de.weltraumschaf.caythe.intermediate.RoutineCode;
import de.weltraumschaf.caythe.backend.interpreter.Executor;
import de.weltraumschaf.caythe.intermediate.CodeNode;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;

import static de.weltraumschaf.caythe.intermediate.codeimpl.CodeKeyImpl.ID;
import static de.weltraumschaf.caythe.intermediate.symboltableimpl.RoutineCodeImpl.DECLARED;
import static de.weltraumschaf.caythe.intermediate.symboltableimpl.SymbolTableKeyImpl.ROUTINE_CODE;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class CallExecutor extends StatementExecutor {

    public CallExecutor(Executor parent) {
        super(parent);
    }

    @Override
    public Object execute(CodeNode node) {
        SymbolTableEntry routineId = (SymbolTableEntry) node.getAttribute(ID);
        RoutineCode routineCode =
                (RoutineCode) routineId.getAttribute(ROUTINE_CODE);
        CallExecutor callExecutor = routineCode == DECLARED
                                    ? new CallDeclaredExecutor(this)
                                    : new CallStandardExecutor(this);

        ++executionCount;  // count the call statement
        return callExecutor.execute(node);
    }
}

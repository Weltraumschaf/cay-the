package de.weltraumschaf.caythe.backend.interpreter.executors;

import de.weltraumschaf.caythe.backend.interpreter.Cell;
import de.weltraumschaf.caythe.intermediate.Definition;
import de.weltraumschaf.caythe.intermediate.Code;
import java.util.ArrayList;
import de.weltraumschaf.caythe.backend.interpreter.ActivationRecord;
import de.weltraumschaf.caythe.backend.interpreter.Executor;
import de.weltraumschaf.caythe.backend.interpreter.MemoryFactory;
import de.weltraumschaf.caythe.intermediate.CodeNode;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;

import de.weltraumschaf.caythe.intermediate.TypeSpecification;
import static de.weltraumschaf.caythe.intermediate.codeimpl.CodeKeyImpl.ID;
import static de.weltraumschaf.caythe.intermediate.symboltableimpl.SymbolTableKeyImpl.ROUTINE_PARAMS;
import static de.weltraumschaf.caythe.intermediate.symboltableimpl.SymbolTableKeyImpl.ROUTINE_INTERMEDIATE_CODE;
import static de.weltraumschaf.caythe.intermediate.symboltableimpl.DefinitionImpl.VALUE_PARAM;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class CallDeclaredExecutor extends CallExecutor {

    public CallDeclaredExecutor(Executor parent) {
        super(parent);
    }

    @Override
    public Object execute(CodeNode node) {
        SymbolTableEntry routineId = (SymbolTableEntry) node.getAttribute(ID);
        ActivationRecord newAr = MemoryFactory.createActivationRecord(routineId);

        // Execute any actual parameters and initialize
        // the formal parameters in the new activation record.
        if (node.getChildren().size() > 0) {
            CodeNode parmsNode = node.getChildren().get(0);
            ArrayList<CodeNode> actualNodes = parmsNode.getChildren();
            ArrayList<SymbolTableEntry> formalIds =
                (ArrayList<SymbolTableEntry>) routineId.getAttribute(ROUTINE_PARAMS);
            executeActualParms(actualNodes, formalIds, newAr);
        }

        // Push the new activation record.
        runtimeStack.push(newAr);

        sendCallMessage(node, routineId.getName());

        // Get the root node of the routine's intermediate code.
        Code iCode = (Code) routineId.getAttribute(ROUTINE_INTERMEDIATE_CODE);
        CodeNode rootNode = iCode.getRoot();

        // Execute the routine.
        StatementExecutor statementExecutor = new StatementExecutor(this);
        Object value = statementExecutor.execute(rootNode);

        // Pop off the activation record.
        runtimeStack.pop();

        sendReturnMessage(node, routineId.getName());
        return value;
    }

    private void executeActualParms(ArrayList<CodeNode> actualNodes,
                                    ArrayList<SymbolTableEntry> formalIds,
                                    ActivationRecord newAr)
    {
        ExpressionExecutor expressionExecutor = new ExpressionExecutor(this);
        AssignmentExecutor assignmentExecutor = new AssignmentExecutor(this);

        for (int i = 0; i < formalIds.size(); ++i) {
            SymbolTableEntry formalId = formalIds.get(i);
            Definition formalDefn = formalId.getDefinition();
            Cell formalCell = newAr.getCell(formalId.getName());
            CodeNode actualNode = actualNodes.get(i);

            // Value parameter.
            if (formalDefn == VALUE_PARAM) {
                TypeSpecification formalType = formalId.getTypeSpecification();
                TypeSpecification valueType  = actualNode.getTypeSpecification().baseType();
                Object value = expressionExecutor.execute(actualNode);

                assignmentExecutor.assignValue(actualNode, formalId,
                                               formalCell, formalType,
                                               value, valueType);
            }
            // VAR parameter.
            else {
                Cell actualCell =
                    (Cell) expressionExecutor.executeVariable(actualNode);
                formalCell.setValue(actualCell);
            }
        }
    }

}

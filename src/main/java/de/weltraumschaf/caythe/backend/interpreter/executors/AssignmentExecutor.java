package de.weltraumschaf.caythe.backend.interpreter.executors;

import de.weltraumschaf.caythe.intermediate.symboltableimpl.Predefined;
import de.weltraumschaf.caythe.backend.interpreter.Cell;
import de.weltraumschaf.caythe.backend.interpreter.Executor;
import de.weltraumschaf.caythe.intermediate.CodeNode;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;
import de.weltraumschaf.caythe.intermediate.TypeSpecification;
import java.util.ArrayList;

import static de.weltraumschaf.caythe.intermediate.codeimpl.CodeKeyImpl.*;
import static de.weltraumschaf.caythe.intermediate.typeimpl.TypeKeyImpl.ARRAY_ELEMENT_COUNT;

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
        SymbolTableEntry variableId = (SymbolTableEntry) variableNode.getAttribute(ID);

        // Execute the target variable to get its reference and
        // execute the expression to get its value.
        ExpressionExecutor expresionExecutor = new ExpressionExecutor(this);
        Cell targetCell = (Cell) expresionExecutor.executeVariable(variableNode);
        TypeSpecification targetType = variableNode.getTypeSpecification();
        TypeSpecification valueType  = expressionNode.getTypeSpecification().baseType();
        Object value = expresionExecutor.execute(expressionNode);

        assignValue(node, variableId, targetCell, targetType, value, valueType);
        ++executionCount;
        return null;
    }

    protected void assignValue(CodeNode node, SymbolTableEntry targetId,
                               Cell targetCell, TypeSpecification targetType,
                               Object value, TypeSpecification valueType)
    {
        // Range check.
        value = checkRange(node, targetType, value);

        // Set the target's value.
        // Convert an integer value to real if necessary.
        if ((targetType == Predefined.realType) &&
            (valueType  == Predefined.integerType))
        {
            targetCell.setValue(new Float(((Integer) value).intValue()));
        }

        // String assignment:
        //   target length < value length: truncate the value
        //   target length > value length: blank pad the value
        else if (targetType.isPascalString()) {
            int targetLength =
                    (Integer) targetType.getAttribute(ARRAY_ELEMENT_COUNT);
            int valueLength =
                    (Integer) valueType.getAttribute(ARRAY_ELEMENT_COUNT);
            String stringValue = (String) value;

            // Truncate the value string.
            if (targetLength < valueLength) {
                stringValue = stringValue.substring(0, targetLength);
            }

            // Pad the value string with blanks at the right end.
            else if (targetLength > valueLength) {
                StringBuilder buffer = new StringBuilder(stringValue);

                for (int i = valueLength; i < targetLength; ++i) {
                    buffer.append(" ");
                }

                stringValue = buffer.toString();
            }

            targetCell.setValue(copyOf(toPascal(targetType, stringValue),
                                       node));
        }

        // Simple assignment.
        else {
            targetCell.setValue(copyOf(toPascal(targetType, value), node));
        }

        sendAssignMessage(node, targetId.getName(), value);
    }
}

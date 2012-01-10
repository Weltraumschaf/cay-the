package de.weltraumschaf.caythe.backend.interpreter.executors;

import de.weltraumschaf.caythe.intermediate.RoutineCode;
import de.weltraumschaf.caythe.intermediate.symboltableimpl.Predefined;
import de.weltraumschaf.caythe.backend.BackendFactory;
import java.util.HashMap;
import de.weltraumschaf.caythe.intermediate.CodeNodeType;
import de.weltraumschaf.caythe.backend.interpreter.ActivationRecord;
import de.weltraumschaf.caythe.backend.interpreter.Cell;
import de.weltraumschaf.caythe.backend.interpreter.Executor;
import de.weltraumschaf.caythe.intermediate.CodeNode;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;
import de.weltraumschaf.caythe.intermediate.TypeSpecification;
import de.weltraumschaf.caythe.intermediate.codeimpl.CodeNodeTypeImpl;
import java.util.ArrayList;
import java.util.EnumSet;

import static de.weltraumschaf.caythe.intermediate.symboltableimpl.SymbolTableKeyImpl.*;
import static de.weltraumschaf.caythe.intermediate.symboltableimpl.RoutineCodeImpl.DECLARED;
import static de.weltraumschaf.caythe.intermediate.codeimpl.CodeKeyImpl.*;
import static de.weltraumschaf.caythe.intermediate.codeimpl.CodeNodeTypeImpl.*;
import static de.weltraumschaf.caythe.intermediate.typeimpl.TypeFormImpl.SUBRANGE;
import static de.weltraumschaf.caythe.intermediate.typeimpl.TypeKeyImpl.*;
import static de.weltraumschaf.caythe.backend.interpreter.RuntimeErrorCode.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class ExpressionExecutor extends StatementExecutor {

    public ExpressionExecutor(Executor parent) {
        super(parent);
    }

    @Override
    public Object execute(CodeNode node) {
        CodeNodeTypeImpl nodeType = (CodeNodeTypeImpl) node.getType();

        switch (nodeType) {
            case VARIABLE: {
                return executeValue(node);
            }

            case INTEGER_CONSTANT: {
                TypeSpecification type = node.getTypeSpecification();
                Integer value = (Integer) node.getAttribute(VALUE);

                // If boolean, return true or false.
                // Else return the integer value.
                return type == Predefined.booleanType
                           ? value == 1  // true or false
                           : value;      // integer value
            }

            case REAL_CONSTANT: {
                return (Float) node.getAttribute(VALUE);
            }

            case STRING_CONSTANT: {
                return (String) node.getAttribute(VALUE);
            }

            case NEGATE: {
                // Get the NEGATE node's expression node child.
                ArrayList<CodeNode> children = node.getChildren();
                CodeNode expressionNode = children.get(0);
                // Execute expression and return the nagative of its value.
                Object value = execute(expressionNode);

                if (value instanceof Integer) {
                    return -( (Integer) value );
                } else {
                    return -( (Float) value );
                }
            }

            case NOT: {
                // Get the NOT node's expression node child.
                ArrayList<CodeNode> children = node.getChildren();
                CodeNode expressionNode = children.get(0);
                // Execute expression and return the "not" of its value.
                boolean value = (Boolean) execute(expressionNode);
                return !value;
            }

            case CALL: {

                // Execute a function call.
                SymbolTableEntry functionId = (SymbolTableEntry) node.getAttribute(ID);
                RoutineCode routineCode =
                    (RoutineCode) functionId.getAttribute(ROUTINE_CODE);
                CallExecutor callExecutor = new CallExecutor(this);
                Object value = callExecutor.execute(node);

                // If it was a declared function, obtain the function value
                // from its name.
                if (routineCode == DECLARED) {
                    String functionName = functionId.getName();
                    int nestingLevel = functionId.getSymbolTable().getNestingLevel();
                    ActivationRecord ar = runtimeStack.getTopmost(nestingLevel);
                    Cell functionValueCell = ar.getCell(functionName);
                    value = functionValueCell.getValue();

                    sendFetchMessage(node, functionId.getName(), value);
                }

                // Return the function value.
                return value;
            }

            // Must be binary operator.
            default: return executeBinaryOperator(node, nodeType);
        }
    }

    private Object executeValue(CodeNode node) {
        SymbolTableEntry variableId = (SymbolTableEntry) node.getAttribute(ID);
        String variableName = variableId.getName();
        TypeSpecification variableType = variableId.getTypeSpecification();

        // Get the variable's value.
        Cell variableCell = executeVariable(node);
        Object value = variableCell.getValue();

        if (value != null) {
            value = toJava(variableType, value);
        } // Uninitialized value error: Use a default value.
        else {
            errorHandler.flag(node, UNINITIALIZED_VALUE, this);

            value = BackendFactory.defaultValue(variableType);
            variableCell.setValue(value);
        }

        sendFetchMessage(node, variableName, value);
        return value;
    }

    public Cell executeVariable(CodeNode node) {
        SymbolTableEntry variableId = (SymbolTableEntry) node.getAttribute(ID);
        String variableName = variableId.getName();
        TypeSpecification variableType = variableId.getTypeSpecification();
        int nestingLevel = variableId.getSymbolTable().getNestingLevel();

        // Get the variable reference from the appropriate activation record.
        ActivationRecord ar = runtimeStack.getTopmost(nestingLevel);
        Cell variableCell = ar.getCell(variableName);

        ArrayList<CodeNode> modifiers = node.getChildren();

        // Reference to a reference: Use the original reference.
        if (variableCell.getValue() instanceof Cell) {
            variableCell = (Cell) variableCell.getValue();
        }

        // Execute any array subscripts or record fields.
        for (CodeNode modifier : modifiers) {
            CodeNodeType nodeType = modifier.getType();

            // Subscripts.
            if (nodeType == SUBSCRIPTS) {
                ArrayList<CodeNode> subscripts = modifier.getChildren();

                // Compute a new reference for each subscript.
                for (CodeNode subscript : subscripts) {
                    TypeSpecification indexType =
                            (TypeSpecification) variableType.getAttribute(ARRAY_INDEX_TYPE);
                    int minIndex = indexType.getForm() == SUBRANGE
                            ? (Integer) indexType.getAttribute(SUBRANGE_MIN_VALUE)
                            : 0;

                    int value = (Integer) execute(subscript);
                    value = (Integer) checkRange(node, indexType, value);

                    int index = value - minIndex;
                    variableCell = ( (Cell[]) variableCell.getValue() )[index];
                    variableType = (TypeSpecification) variableType.getAttribute(ARRAY_ELEMENT_TYPE);
                }
            } // Field.
            else if (nodeType == FIELD) {
                SymbolTableEntry fieldId = (SymbolTableEntry) modifier.getAttribute(ID);
                String fieldName = fieldId.getName();

                // Compute a new reference for the field.
                HashMap<String, Cell> map =
                        (HashMap<String, Cell>) variableCell.getValue();
                variableCell = map.get(fieldName);
                variableType = fieldId.getTypeSpecification();
            }
        }

        return variableCell;
    }
    private static final EnumSet<CodeNodeTypeImpl> ARITHMETIC_OPS =
            EnumSet.of(ADD, SUBTRACT, MULTIPLY, FLOAT_DIVIDE, INTEGER_DIVIDE);

    private Object executeBinaryOperator(CodeNode node, CodeNodeTypeImpl nodeType) {
        ArrayList<CodeNode> children = node.getChildren();
        CodeNode operandNode1 = children.get(0);
        CodeNode operandNode2 = children.get(1);
        // Operands
        Object operand1 = execute(operandNode1);
        Object operand2 = execute(operandNode2);

        boolean integerMode   = false;
        boolean characterMode = false;
        boolean stringMode    = false;

        if ((operand1 instanceof Integer) && (operand2 instanceof Integer)) {
            integerMode = true;
        }
        else if ( ( (operand1 instanceof Character) ||
                    ( (operand1 instanceof String) &&
                      (((String) operand1).length() == 1) )
                  ) &&
                  ( (operand2 instanceof Character) ||
                    ( (operand2 instanceof String) &&
                      (((String) operand2).length() == 1) )
                  )
                ) {
            characterMode = true;
        }
        else if ((operand1 instanceof String) && (operand2 instanceof String)) {
            stringMode = true;
        }

        // Arithemtic operations
        if (ARITHMETIC_OPS.contains(nodeType)) {
            if (integerMode) {
                // Integer operations
                int value1 = (Integer) operand1;
                int value2 = (Integer) operand2;

                switch (nodeType) {
                    case ADD:
                        return value1 + value2;
                    case SUBTRACT:
                        return value1 - value2;
                    case MULTIPLY:
                        return value1 * value2;
                    case FLOAT_DIVIDE: {
                        // Check for division by zero.
                        if (0 != value2) {
                            return ( (float) value1 ) / ( (float) value2 );
                        } else {
                            errorHandler.flag(node, DIVISION_BY_ZERO, this);
                            return 0;
                        }
                    }
                    case INTEGER_DIVIDE: {
                        // Check for division by zero.
                        if (0 != value2) {
                            return value1 / value2;
                        } else {
                            errorHandler.flag(node, DIVISION_BY_ZERO, this);
                            return 0;
                        }
                    }
                    case MOD: {
                        // Check for division by zero.
                        if (0 != value2) {
                            return value1 % value2;
                        } else {
                            errorHandler.flag(node, DIVISION_BY_ZERO, this);
                            return 0;
                        }
                    }
                }
            } else {
                // Float operations.
                float value1 = operand1 instanceof Integer ? (Integer) operand1 : (Float) operand1;
                float value2 = operand2 instanceof Integer ? (Integer) operand2 : (Float) operand2;

                switch (nodeType) {
                    case ADD:
                        return value1 + value2;
                    case SUBTRACT:
                        return value1 - value2;
                    case MULTIPLY:
                        return value1 * value2;
                    case FLOAT_DIVIDE: {
                        if (0.0f != value2) {
                            return value1 / value2;
                        } else {
                            errorHandler.flag(node, DIVISION_BY_ZERO, this);
                            return 0.0f;
                        }
                    }
                }
            }
        } // AND and OR
        else if (( AND == nodeType ) || ( OR == nodeType )) {
            boolean value1 = (Boolean) operand1;
            boolean value2 = (Boolean) operand2;

            switch (nodeType) {
                case AND:
                    return value1 && value2;
                case OR:
                    return value1 || value2;
            }
        } // Relational opertors
        else if (integerMode) {
            // Integer operations.
            int value1 = (Integer) operand1;
            int value2 = (Integer) operand2;

            switch (nodeType) {
                case EQ:
                    return value1 == value2;
                case NE:
                    return value1 != value2;
                case LT:
                    return value1 < value2;
                case LE:
                    return value1 <= value2;
                case GT:
                    return value1 > value2;
                case GE:
                    return value1 >= value2;
            }
        }
        else if (characterMode) {
            int value1 = operand1 instanceof Character
                             ? (Character) operand1
                             : ((String) operand1).charAt(0);
            int value2 = operand2 instanceof Character
                             ? (Character) operand2
                             : ((String) operand2).charAt(0);

            // Character operands.
            switch (nodeType) {
                case EQ: return value1 == value2;
                case NE: return value1 != value2;
                case LT: return value1 <  value2;
                case LE: return value1 <= value2;
                case GT: return value1 >  value2;
                case GE: return value1 >= value2;
            }
        }
        else if (stringMode) {
            String value1 = (String) operand1;
            String value2 = (String) operand2;

            // String operands.
            int comp = value1.compareTo(value2);
            switch (nodeType) {
                case EQ: return comp == 0;
                case NE: return comp != 0;
                case LT: return comp <  0;
                case LE: return comp <= 0;
                case GT: return comp >  0;
                case GE: return comp >= 0;
            }
        }
        else {
            // Float operations.
            float value1 = operand1 instanceof Integer ? (Integer) operand1 : (Float) operand1;
            float value2 = operand2 instanceof Integer ? (Integer) operand2 : (Float) operand2;

            switch (nodeType) {
                case EQ:
                    return value1 == value2;
                case NE:
                    return value1 != value2;
                case LT:
                    return value1 < value2;
                case LE:
                    return value1 <= value2;
                case GT:
                    return value1 > value2;
                case GE:
                    return value1 >= value2;
            }
        }

        return 0; // Should never get here.
    }
}

package de.weltraumschaf.caythe.backend.interpreter.executors;

import de.weltraumschaf.caythe.backend.interpreter.Executor;
import de.weltraumschaf.caythe.backend.interpreter.RuntimeErrorCode;
import de.weltraumschaf.caythe.intermediate.CodeNode;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;
import de.weltraumschaf.caythe.intermediate.codeimpl.CodeNodeTypeImpl;

import java.util.ArrayList;
import java.util.EnumSet;
import static de.weltraumschaf.caythe.intermediate.symboltableimpl.SymbolTableKeyImpl.*;
import static de.weltraumschaf.caythe.intermediate.codeimpl.CodeKeyImpl.*;
import static de.weltraumschaf.caythe.intermediate.codeimpl.CodeNodeTypeImpl.*;
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
                SymbolTableEntry entry = (SymbolTableEntry) node.getAttribute(ID);
                return entry.getAttribute(DATA_VALUE);
            }
            case INTEGER_CONSTANT: {
                return (Integer) node.getAttribute(VALUE);
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
                    return - ((Integer) value);
                } else {
                    return - ((Float) value);
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
            // Must be binary operator.
            default: return executeBinaryOperator(node, nodeType);
        }
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
        boolean integerMode = (operand1 instanceof Integer) && (operand2 instanceof Integer);

        // Arithemtic operations
        if (ARITHMETIC_OPS.contains(nodeType)) {
            if (integerMode) {
                // Integer operations
                int value1 = (Integer) operand1;
                int value2 = (Integer) operand2;

                switch (nodeType) {
                    case ADD:      return value1 + value2;
                    case SUBTRACT: return value1 - value2;
                    case MULTIPLY: return value1 * value2;
                    case FLOAT_DIVIDE: {
                        // Check for division by zero.
                        if (0 != value2) {
                            return ((float) value1) / ((float) value2);
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
                    case ADD:      return value1 + value2;
                    case SUBTRACT: return value1 - value2;
                    case MULTIPLY: return value1 * value2;
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
        }
        // AND and OR
        else if ((AND == nodeType) || (OR == nodeType)) {
            boolean value1 = (Boolean) operand1;
            boolean value2 = (Boolean) operand2;

            switch (nodeType) {
                case AND: return value1 && value2;
                case OR:  return value1 || value2;
            }
        }
        // Relational opertors
        else if (integerMode) {
            // Integer operations.
            int value1 = (Integer) operand1;
            int value2 = (Integer) operand2;

            switch (nodeType) {
                case EQ: return value1 == value2;
                case NE: return value1 != value2;
                case LT: return value1 <  value2;
                case LE: return value1 <= value2;
                case GT: return value1 >  value2;
                case GE: return value1 >= value2;
            }
        } else {
            // Float operations.
            float value1 = operand1 instanceof Integer ? (Integer) operand1 : (Float) operand1;
            float value2 = operand2 instanceof Integer ? (Integer) operand2 : (Float) operand2;

            switch (nodeType) {
                case EQ: return value1 == value2;
                case NE: return value1 != value2;
                case LT: return value1 <  value2;
                case LE: return value1 <= value2;
                case GT: return value1 >  value2;
                case GE: return value1 >= value2;
            }
        }

        return 0; // Should never get here.
    }
}

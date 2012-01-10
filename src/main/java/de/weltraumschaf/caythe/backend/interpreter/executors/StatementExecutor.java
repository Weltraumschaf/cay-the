package de.weltraumschaf.caythe.backend.interpreter.executors;

import java.util.Iterator;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import de.weltraumschaf.caythe.backend.interpreter.MemoryFactory;
import de.weltraumschaf.caythe.backend.interpreter.Cell;
import de.weltraumschaf.caythe.intermediate.symboltableimpl.Predefined;
import de.weltraumschaf.caythe.backend.interpreter.Executor;
import de.weltraumschaf.caythe.intermediate.CodeNode;
import de.weltraumschaf.caythe.intermediate.TypeSpecification;
import de.weltraumschaf.caythe.intermediate.codeimpl.CodeNodeTypeImpl;
import de.weltraumschaf.caythe.message.Message;

import static de.weltraumschaf.caythe.message.MessageType.*;
import static de.weltraumschaf.caythe.backend.interpreter.RuntimeErrorCode.*;
import static de.weltraumschaf.caythe.intermediate.codeimpl.CodeKeyImpl.LINE;
import static de.weltraumschaf.caythe.intermediate.typeimpl.TypeFormImpl.SUBRANGE;
import static de.weltraumschaf.caythe.intermediate.typeimpl.TypeKeyImpl.SUBRANGE_MIN_VALUE;
import static de.weltraumschaf.caythe.intermediate.typeimpl.TypeKeyImpl.SUBRANGE_MAX_VALUE;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class StatementExecutor extends Executor {

    public StatementExecutor(Executor parent) {
        super(parent);
    }

    public Object execute(CodeNode node) {
        CodeNodeTypeImpl nodeType = (CodeNodeTypeImpl) node.getType();
        // Send message about current source line
        sendSourceLineMessage(node);

        switch (nodeType) {
            case COMPOUND: {
                CompundExecutor executr = new CompundExecutor(this);
                return executr.execute(node);
            }

            case ASSIGN: {
                AssignmentExecutor executor = new AssignmentExecutor(this);
                return executor.execute(node);
            }

            case LOOP: {
                LoopExecutor executor = new LoopExecutor(this);
                return executor.execute(node);
            }

            case IF: {
                IfExecutor executor = new IfExecutor(this);
                return executor.execute(node);
            }

            case SELECT: {
                SelectExecutor executor = new SelectExecutor(this);
                return executor.execute(node);
            }

            case CALL: {
                CallExecutor callExecutor = new CallExecutor(this);
                return callExecutor.execute(node);
            }

            case NO_OP:
                return null;

            default: {
                errorHandler.flag(node, UNIMPLEMENTED_FEATURE, this);
                return null;
            }
        }
    }

    protected Object toPascal(TypeSpecification targetType, Object javaValue) {
        if (javaValue instanceof String) {
            String string = (String) javaValue;

            if (targetType == Predefined.charType) {
                return string.charAt(0);  // Pascal character
            } else if (targetType.isPascalString()) {
                Cell charCells[] = new Cell[string.length()];

                // Build an array of characters.
                for (int i = 0; i < string.length(); ++i) {
                    charCells[i] = MemoryFactory.createCell(string.charAt(i));
                }

                return charCells;  // Pascal string (array of characters)
            } else {
                return javaValue;
            }
        } else {
            return javaValue;
        }
    }

    protected Object toJava(TypeSpecification targetType, Object pascalValue) {
        if (( pascalValue instanceof Cell[] )
                && ( ( (Cell[]) pascalValue )[0].getValue() instanceof Character )) {
            Cell charCells[] = (Cell[]) pascalValue;
            StringBuilder string = new StringBuilder(charCells.length);

            // Build a Java string.
            for (Cell ref : charCells) {
                string.append((Character) ref.getValue());
            }

            return string.toString();  // Java string
        } else {
            return pascalValue;
        }
    }

    protected Object copyOf(Object value, CodeNode node) {
        Object copy = null;

        if (value instanceof Integer) {
            copy = new Integer((Integer) value);
        } else if (value instanceof Float) {
            copy = new Float((Float) value);
        } else if (value instanceof Character) {
            copy = new Character((Character) value);
        } else if (value instanceof Boolean) {
            copy = new Boolean((Boolean) value);
        } else if (value instanceof String) {
            copy = new String((String) value);
        } else if (value instanceof HashMap) {
            copy = copyRecord((HashMap<String, Object>) value, node);
        } else {
            copy = copyArray((Cell[]) value, node);
        }

        return copy;
    }

    private Object copyRecord(HashMap<String, Object> value, CodeNode node) {
        HashMap<String, Object> copy = new HashMap<String, Object>();

        if (value != null) {
            Set<Map.Entry<String, Object>> entries = value.entrySet();
            Iterator<Map.Entry<String, Object>> it = entries.iterator();

            while (it.hasNext()) {
                Map.Entry<String, Object> entry = it.next();
                String newKey = new String(entry.getKey());
                Cell valueCell = (Cell) entry.getValue();
                Object newValue = copyOf(valueCell.getValue(), node);

                copy.put(newKey, MemoryFactory.createCell(newValue));
            }
        } else {
            errorHandler.flag(node, UNINITIALIZED_VALUE, this);
        }

        return copy;
    }

    private Cell[] copyArray(Cell valueCells[], CodeNode node) {
        int length;
        Cell copy[];

        if (valueCells != null) {
            length = valueCells.length;
            copy = new Cell[length];

            for (int i = 0; i < length; ++i) {
                Cell valueCell = (Cell) valueCells[i];
                Object newValue = copyOf(valueCell.getValue(), node);
                copy[i] = MemoryFactory.createCell(newValue);
            }
        } else {
            errorHandler.flag(node, UNINITIALIZED_VALUE, this);
            copy = new Cell[1];
        }

        return copy;
    }

    protected Object checkRange(CodeNode node, TypeSpecification type, Object value) {
        if (type.getForm() == SUBRANGE) {
            int minValue = (Integer) type.getAttribute(SUBRANGE_MIN_VALUE);
            int maxValue = (Integer) type.getAttribute(SUBRANGE_MAX_VALUE);

            if (( (Integer) value ) < minValue) {
                errorHandler.flag(node, VALUE_RANGE, this);
                return minValue;
            } else if (( (Integer) value ) > maxValue) {
                errorHandler.flag(node, VALUE_RANGE, this);
                return maxValue;
            } else {
                return value;
            }
        } else {
            return value;
        }
    }

    private void sendSourceLineMessage(CodeNode node) {
        Object lineNumber = node.getAttribute(LINE);

        if (null != lineNumber) {
            sendMessage(new Message(SOURCE_LINE, lineNumber));
        }
    }

    protected void sendAssignMessage(CodeNode node, String variableName, Object value) {
        Object lineNumber = getLineNumber(node);

        // Send an ASSIGN message.
        if (lineNumber != null) {
            sendMessage(new Message(ASSIGN, new Object[]{lineNumber,
                        variableName,
                        value}));
        }
    }

    protected void sendFetchMessage(CodeNode node, String variableName, Object value) {
        Object lineNumber = getLineNumber(node);

        // Send a FETCH message.
        if (lineNumber != null) {
            sendMessage(new Message(FETCH, new Object[]{lineNumber,
                        variableName,
                        value}));
        }
    }

    protected void sendCallMessage(CodeNode node, String routineName) {
        Object lineNumber = getLineNumber(node);

        // Send a CALL message.
        if (lineNumber != null) {
            sendMessage(new Message(CALL, new Object[]{lineNumber,
                        routineName}));
        }
    }

    protected void sendReturnMessage(CodeNode node, String routineName) {
        Object lineNumber = getLineNumber(node);

        // Send a RETURN message.
        if (lineNumber != null) {
            sendMessage(new Message(RETURN, new Object[]{lineNumber,
                        routineName}));
        }
    }

    private Object getLineNumber(CodeNode node) {
        Object lineNumber = null;

        // Go up the parent links to look for a line number.
        while (( node != null )
                && ( ( lineNumber = node.getAttribute(LINE) ) == null )) {
            node = node.getParent();
        }

        return lineNumber;
    }
}

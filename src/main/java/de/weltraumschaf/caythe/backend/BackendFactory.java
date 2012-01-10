package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.backend.compiler.CodeGenerator;
import de.weltraumschaf.caythe.backend.interpreter.Executor;
import de.weltraumschaf.caythe.intermediate.TypeSpecification;
import de.weltraumschaf.caythe.intermediate.symboltableimpl.Predefined;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class BackendFactory {

    public enum Operation {
        COMPILE,
        EXECUTE
    }

    public static Object defaultValue(TypeSpecification type) {
        type = type.baseType();

        if (type == Predefined.integerType) {
            return new Integer(0);
        } else if (type == Predefined.realType) {
            return new Float(0.0f);
        } else if (type == Predefined.booleanType) {
            return new Boolean(false);
        } else if (type == Predefined.charType) {
            return new Character('#');
        } else /* strig */ {
            return new String("#");
        }
    }

    public static Backend createBackend(Operation operation) throws Exception {
        if (Operation.COMPILE == operation) {
            return new CodeGenerator();
        } else if (Operation.EXECUTE == operation) {
            return new Executor();
        } else {
            throw new Exception(String.format("Unsupported operation %s!", operation));
        }
    }
}

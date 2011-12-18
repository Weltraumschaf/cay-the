package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.backend.compiler.CodeGenerator;
import de.weltraumschaf.caythe.backend.interpreter.Executor;

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

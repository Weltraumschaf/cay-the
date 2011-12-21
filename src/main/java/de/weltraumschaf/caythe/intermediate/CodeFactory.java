package de.weltraumschaf.caythe.intermediate;

import de.weltraumschaf.caythe.intermediate.codeimpl.CodeImpl;
import de.weltraumschaf.caythe.intermediate.codeimpl.CodeNodeImpl;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class CodeFactory {

    public static Code createCode() {
        return new CodeImpl();
    }

    public static CodeNode createCodeNode(CodeNodeType type) {
        return new CodeNodeImpl(type);
    }
}

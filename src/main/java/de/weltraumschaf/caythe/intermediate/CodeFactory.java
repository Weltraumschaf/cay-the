package de.weltraumschaf.caythe.intermediate;

import de.weltraumschaf.caythe.intermediate.codeimpl.CodeImpl;
import de.weltraumschaf.caythe.intermediate.codeimpl.CodeNodeImpl;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class CodeFactory {

    public static Code createICode() {
        return new CodeImpl();
    }

    public static CodeNode createICodeNode(CodeNodeType type) {
        return new CodeNodeImpl(type);
    }
}

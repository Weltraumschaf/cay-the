package de.weltraumschaf.caythe.intermediate.codeimpl;

import de.weltraumschaf.caythe.intermediate.Code;
import de.weltraumschaf.caythe.intermediate.CodeNode;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class CodeImpl implements Code {

    private CodeNode root;

    @Override
    public CodeNode setRoot(CodeNode node) {
        root = node;
        return root;
    }

    @Override
    public CodeNode getRoot() {
        return root;
    }
}

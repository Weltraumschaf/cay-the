package de.weltraumschaf.caythe.backend.interpreter.executors;

import de.weltraumschaf.caythe.backend.interpreter.Executor;
import de.weltraumschaf.caythe.intermediate.CodeNode;
import java.util.ArrayList;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class CompundExecutor extends StatementExecutor {

    public CompundExecutor(Executor parent) {
        super(parent);
    }

    @Override
    public Object execute(CodeNode node) {
        StatementExecutor statementExecutor = new StatementExecutor(this);
        ArrayList<CodeNode> children = node.getChildren();

        for (CodeNode child : children) {
            statementExecutor.execute(child);
        }
        
        return null;
    }

}

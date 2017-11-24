package de.weltraumschaf.caythe.intermediate.ast.builder;

import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.caythe.intermediate.ast.AstNode;
import de.weltraumschaf.caythe.intermediate.ast.Statement;
import de.weltraumschaf.caythe.intermediate.ast.StatementList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class StatementListBuilder {
    private final List<AstNode> statements = new ArrayList<>();
    private final Position position;

    private StatementListBuilder(final Position position) {
        super();
        this.position = position;
    }

    private List<AstNode> list(final AstNode... nodes) {
        final List<AstNode> list = new ArrayList<>();
        Collections.addAll(list, nodes);
        return list;
    }

    public static StatementListBuilder unit(final int line, final int column) {
        return new StatementListBuilder(new Position(line, column));
    }

    public StatementList end() {
        return new StatementList(statements, position);
    }

    public StatementListBuilder statement(final AstNode statement, final int line, final int column) {
        statements.add(new Statement(statement, new Position(line, column)));
        return this;
    }
}

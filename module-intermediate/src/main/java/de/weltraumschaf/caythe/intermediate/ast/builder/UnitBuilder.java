package de.weltraumschaf.caythe.intermediate.ast.builder;

import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.caythe.intermediate.ast.AstNode;
import de.weltraumschaf.caythe.intermediate.ast.Statement;
import de.weltraumschaf.caythe.intermediate.ast.Unit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class UnitBuilder {
    private final List<AstNode> statements = new ArrayList<>();
    private final Position position;

    private UnitBuilder(final Position position) {
        super();
        this.position = position;
    }

    private List<AstNode> list(final AstNode... nodes) {
        final List<AstNode> list = new ArrayList<>();
        Collections.addAll(list, nodes);
        return list;
    }

    public static UnitBuilder unit(final int line, final int column) {
        return new UnitBuilder(new Position(line, column));
    }

    public Unit end() {
        return new Unit(statements, position);
    }

    public UnitBuilder statement(final AstNode statement, final int line, final int column) {
        statements.add(new Statement(list(statement), new Position(line, column)));
        return this;
    }
}

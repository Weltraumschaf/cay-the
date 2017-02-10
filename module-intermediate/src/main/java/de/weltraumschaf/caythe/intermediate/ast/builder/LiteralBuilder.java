package de.weltraumschaf.caythe.intermediate.ast.builder;

import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.caythe.intermediate.ast.*;

public final class LiteralBuilder {
    private LiteralBuilder() {
        super();
    }

    public static BooleanLiteral bool(final int line, final int column, final boolean value) {
        return new BooleanLiteral(value, new Position(line, column));
    }

    public static RealLiteral real(final int line, final int column, final double value) {
        return new RealLiteral(value, new Position(line, column));
    }

    public static Identifier identifier(final int line, final int column, final String name) {
        return new Identifier(name, new Position(line, column));
    }

    public static IntegerLiteral integer(final int line, final int column, final long value) {
        return new IntegerLiteral(value, new Position(line, column));
    }

    public static NilLiteral nil() {
        return NilLiteral.NIL;
    }

    public static NilLiteral nil(final int line, final int column) {
        return new NilLiteral(new Position(line, column));
    }

    public static StringLiteral string(final int line, final int column, final String value) {
        return new StringLiteral(value, new Position(line, column));
    }


}

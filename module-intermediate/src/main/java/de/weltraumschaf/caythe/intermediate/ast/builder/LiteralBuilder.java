package de.weltraumschaf.caythe.intermediate.ast.builder;

import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.caythe.intermediate.ast.*;

public final class LiteralBuilder {
    private LiteralBuilder() {
        super();
        throw new UnsupportedOperationException("Do not call by reflection!");
    }

    public static BooleanLiteral bool(final boolean value, final int line, final int column) {
        return new BooleanLiteral(value, new Position(line, column));
    }

    public static RealLiteral real(final double value, final int line, final int column) {
        return new RealLiteral(value, new Position(line, column));
    }

    public static Identifier identifier(final String name, final int line, final int column) {
        return new Identifier(name, new Position(line, column));
    }

    public static IntegerLiteral integer(final long value, final int line, final int column) {
        return new IntegerLiteral(value, new Position(line, column));
    }

    public static StringLiteral string(final String value, final int line, final int column) {
        return new StringLiteral(value, new Position(line, column));
    }

    public static NilLiteral nil() {
        return new NilLiteral(Position.UNKNOWN);
    }

    public static NilLiteral nil(final int line, final int column) {
        return new NilLiteral(new Position(line, column));
    }
}

package de.weltraumschaf.caythe.intermediate.model.ast.builder;

import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.caythe.intermediate.model.ast.*;

public final class LiteralFactory {
    private LiteralFactory() {
        super();
        throw new UnsupportedOperationException("Do not call by reflection!");
    }

    public static BooleanLiteral bool(final boolean value, final int line, final int column) {
        return bool(value, new Position(line, column));
    }

    public static BooleanLiteral bool(final boolean value, final Position pos) {
        return new BooleanLiteral(value, pos);
    }

    public static RealLiteral real(final double value, final int line, final int column) {
        return real(value, new Position(line, column));
    }

    public static RealLiteral real(final double value, final Position pos) {
        return new RealLiteral(value, pos);
    }

    public static Identifier identifier(final String name, final int line, final int column) {
        return identifier(name, new Position(line, column));
    }

    public static Identifier identifier(final String name, final Position pos) {
        return new Identifier(name, pos);
    }

    public static IntegerLiteral integer(final long value, final int line, final int column) {
        return integer(value, new Position(line, column));
    }

    public static IntegerLiteral integer(final long value, final Position pos) {
        return new IntegerLiteral(value, pos);
    }

    public static StringLiteral string(final String value, final int line, final int column) {
        return string(value, new Position(line, column));
    }

    public static StringLiteral string(final String value, final Position pos) {
        return new StringLiteral(value, pos);
    }

    public static NilLiteral nil() {
        return nil(Position.UNKNOWN);
    }

    public static NilLiteral nil(final int line, final int column) {
        return nil(new Position(line, column));
    }

    public static NilLiteral nil(final Position pos) {
        return new NilLiteral(pos);
    }
}

package de.weltraumschaf.caythe.backend.experimental.types;

public final class BreakType implements ObjectType {
    public static final BreakType INSTANCE = new BreakType();

    private BreakType() {
        super();
    }

    @Override
    public Type type() {
        return Type.BREAK;
    }

    @Override
    public String inspect() {
        return "break";
    }

    @Override
    public String toString() {
        return "BreakType{}";
    }
}

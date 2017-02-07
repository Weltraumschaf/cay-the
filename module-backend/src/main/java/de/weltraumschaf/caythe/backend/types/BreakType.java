package de.weltraumschaf.caythe.backend.types;

public final class BreakType implements ObjectType {
    public static final BreakType BREAK = new BreakType();

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

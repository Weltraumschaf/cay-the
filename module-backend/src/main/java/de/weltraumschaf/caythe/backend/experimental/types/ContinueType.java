package de.weltraumschaf.caythe.backend.experimental.types;

public final class ContinueType implements ObjectType {

    public static final ContinueType CONTINUE = new ContinueType();

    private ContinueType() {
        super();
    }

    @Override
    public Type type() {
        return Type.CONTINUE;
    }

    @Override
    public String inspect() {
        return "continue";
    }

    @Override
    public String toString() {
        return "ContinueType{}";
    }
}

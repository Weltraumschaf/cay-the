package de.weltraumschaf.caythe.backend.types;

public final class NullType implements ObjectType {
    public static final NullType NULL = new NullType();

    private NullType() {
        super();
    }

    @Override
    public Type type() {
        return Type.NULL;
    }

    @Override
    public String inspect() {
        return "null";
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "NullType{}";
    }

    @Override
    public BooleanType castToBoolean() {
        return BooleanType.FALSE;
    }

    @Override
    public IntegerType castToInteger() {
        return new IntegerType(0);
    }

    @Override
    public RealType castToReal() {
        return new RealType(0);
    }

    @Override
    public StringType castToString() {
        return new StringType("");
    }
}

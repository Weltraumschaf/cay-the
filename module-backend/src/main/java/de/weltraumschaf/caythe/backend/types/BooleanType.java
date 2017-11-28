package de.weltraumschaf.caythe.backend.types;

import java.util.Objects;

public final class BooleanType implements ObjectType {
    public static final BooleanType TRUE = new BooleanType(true);
    public static final BooleanType FALSE = new BooleanType(false);
    private final boolean value;

    private BooleanType(final boolean value) {
        super();
        this.value = value;
    }

    @Override
    public Type type() {
        return Type.BOOLEAN;
    }

    @Override
    public String inspect() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof BooleanType)) {
            return false;
        }

        final BooleanType that = (BooleanType) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "BooleanType{" + "value=" + value + '}';
    }

    public BooleanType not() {
        return TRUE.equals(this) ? FALSE : TRUE;
    }

    @Override
    public BooleanType castToBoolean() {
        return this;
    }

    @Override
    public IntegerType castToInteger() {
        return value ? new IntegerType(1) : new IntegerType(0);
    }

    @Override
    public RealType castToReal() {
        return value ? new RealType(1) : new RealType(0);
    }

    @Override
    public StringType castToString() {
        return new StringType(String.valueOf(value));
    }

    public boolean value() {
        return value;
    }

    public static BooleanType valueOf(final String value) {
        return valueOf("true".equals(value));
    }

    public static BooleanType valueOf(final boolean value) {
        return value ? TRUE : FALSE;
    }

}

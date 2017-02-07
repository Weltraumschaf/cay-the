package de.weltraumschaf.caythe.backend.types;

import java.util.Objects;

public final class IntegerType implements ObjectType {
    private final long value;

    public IntegerType(final long value) {
        super();
        this.value = value;
    }

    @Override
    public Type type() {
        return Type.INTEGER;
    }

    @Override
    public String inspect() {
        return String.valueOf(value);
    }

    public long value() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof IntegerType)) {
            return false;
        }

        final IntegerType that = (IntegerType) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "IntegerType{" +
            "value=" + value +
            '}';
    }

    @Override
    public BooleanType castToBoolean() {
        return value == 0 ? BooleanType.FALSE : BooleanType.TRUE;
    }

    @Override
    public IntegerType castToInteger() {
        return this;
    }

    @Override
    public FloatType castToFloat() {
        return new FloatType(value);
    }

    @Override
    public StringType castToString() {
        return new StringType(String.valueOf(value));
    }

    public static IntegerType valueOf(final String value) {
        return new IntegerType(Long.parseLong(value));
    }
}

package de.weltraumschaf.caythe.backend.experimental.types;

import java.util.Objects;

public final class FloatType implements ObjectType {
    private final double value;

    public FloatType(final double value) {
        super();
        this.value = value;
    }

    public double value() {
        return value;
    }

    @Override
    public Type type() {
        return Type.FLOAT;
    }

    @Override
    public String inspect() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof FloatType)) {
            return false;
        }

        final FloatType floatType = (FloatType) o;
        return Double.compare(floatType.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "FloatType{" +
            "value=" + value +
            '}';
    }

    @Override
    public BooleanType castToBoolean() {
        return value == 0 ? BooleanType.FALSE : BooleanType.TRUE;
    }

    @Override
    public IntegerType castToInteger() {
        return new IntegerType((long)value);
    }

    @Override
    public FloatType castToFloat() {
        return this;
    }

    @Override
    public StringType castToString() {
        return new StringType(String.valueOf(value));
    }

    public static FloatType valueOf(final String value) {
        return new FloatType(Double.parseDouble(value));
    }

}

package de.weltraumschaf.caythe.backend.types;

import java.util.Objects;

public final class RealType implements ObjectType {
    private final double value;

    public RealType(final double value) {
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
        if (!(o instanceof RealType)) {
            return false;
        }

        final RealType realType = (RealType) o;
        return Double.compare(realType.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "RealType{" +
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
    public RealType castToReal() {
        return this;
    }

    @Override
    public StringType castToString() {
        return new StringType(String.valueOf(value));
    }

    public static RealType valueOf(final String value) {
        return new RealType(Double.parseDouble(value));
    }

}

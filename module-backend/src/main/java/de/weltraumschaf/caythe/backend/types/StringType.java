package de.weltraumschaf.caythe.backend.types;

import java.util.Objects;

public final class StringType implements ObjectType {
    private final String value;

    public StringType(final String value) {
        super();
        this.value = value;
    }

    @Override
    public Type type() {
        return Type.STRING;
    }

    @Override
    public String inspect() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof StringType)) {
            return false;
        }

        final StringType that = (StringType) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "StringType{" +
            "value='" + value + '\'' +
            '}';
    }

    @Override
    public BooleanType castToBoolean() {
        return Boolean.parseBoolean(value) ? BooleanType.TRUE : BooleanType.FALSE;
    }

    @Override
    public IntegerType castToInteger() {
        try {
            return new IntegerType(Long.parseLong(value));
        } catch (final NumberFormatException ex) {
            return new IntegerType(0);
        }
    }

    @Override
    public FloatType castToFloat() {
        try {
            return new FloatType(Double.parseDouble(value));
        } catch (final NumberFormatException ex) {
            return new FloatType(0);
        }
    }

    @Override
    public StringType castToString() {
        return this;
    }

    public String value() {
        return value;
    }
}

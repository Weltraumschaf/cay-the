package de.weltraumschaf.caythe.backend.experimental.types;

import java.util.Objects;

public final class ReturnValueType implements ObjectType {
    private final ObjectType value;

    public ReturnValueType(final ObjectType value) {
        super();
        this.value = value;
    }

    @Override
    public Type type() {
        return Type.RETURN_VALUE;
    }

    public ObjectType value() {
        return value;
    }

    @Override
    public String inspect() {
        return value.inspect();
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof ReturnValueType)) {
            return false;
        }

        final ReturnValueType that = (ReturnValueType) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "ReturnValueType{" +
            "value=" + value +
            '}';
    }
}

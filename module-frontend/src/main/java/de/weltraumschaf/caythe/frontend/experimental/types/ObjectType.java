package de.weltraumschaf.caythe.frontend.experimental.types;

public interface ObjectType {
    Type type();
    String inspect();

    default boolean isOf(Type type) {
        return type() == type;
    }

    default boolean isNotOf(Type type) {
        return !isOf(type);
    }

    default boolean isOneOf(Type... types) {
        for (final Type type : types) {
            if (isOf(type)) {
                return true;
            }
        }

        return false;
    }

    default boolean isNoneOf(Type... types) {
        return !isOneOf(types);
    }

    default BooleanType castToBoolean() {
        throw new UnsupportedOperationException("Can't cast " + getClass().getSimpleName() + " to boolean type!");
    }

    default IntegerType castToInteger() {
        throw new UnsupportedOperationException("Can't cast " + getClass().getSimpleName() + " to integer type!");
    }

    default FloatType castToFloat() {
        throw new UnsupportedOperationException("Can't cast " + getClass().getSimpleName() + " to float type!");
    }

    default StringType castToString() {
        throw new UnsupportedOperationException("Can't cast " + getClass().getSimpleName() + " to string type!");
    }

}

package de.weltraumschaf.caythe.backend.experimental.operations;

import de.weltraumschaf.caythe.backend.experimental.types.BooleanType;
import de.weltraumschaf.caythe.backend.experimental.types.ObjectType;

public final class LogicOperations {

    public BooleanType and(final ObjectType left, final ObjectType right) {
        return BooleanType.valueOf(left.castToBoolean().value() && right.castToBoolean().value());
    }

    public BooleanType or(final ObjectType left, final ObjectType right) {
        return BooleanType.valueOf(left.castToBoolean().value() || right.castToBoolean().value());
    }

    public BooleanType not(final ObjectType operand) {
        return operand.castToBoolean().not();
    }
}

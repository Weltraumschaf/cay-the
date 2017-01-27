package de.weltraumschaf.caythe.frontend.experimental.operations;

import de.weltraumschaf.caythe.frontend.experimental.types.BooleanType;
import de.weltraumschaf.caythe.frontend.experimental.types.ObjectType;

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

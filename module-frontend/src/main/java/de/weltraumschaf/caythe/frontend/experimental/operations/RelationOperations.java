package de.weltraumschaf.caythe.frontend.experimental.operations;

import de.weltraumschaf.caythe.frontend.experimental.types.BooleanType;
import de.weltraumschaf.caythe.frontend.experimental.types.ObjectType;

import java.util.Objects;

public final class RelationOperations {
    public ObjectType equal(final ObjectType left, final ObjectType right) {
        return BooleanType.valueOf(Objects.equals(left, right));
    }

    public ObjectType notEqual(final ObjectType left, final ObjectType right) {
        return BooleanType.valueOf(Objects.equals(left, right)).not();
    }

    public ObjectType lessThan(final ObjectType left, final ObjectType right) {
        return BooleanType.valueOf(left.castToFloat().value() < right.castToFloat().value());
    }

    public ObjectType lessThanOrEqual(final ObjectType left, final ObjectType right) {
        return BooleanType.valueOf(left.castToFloat().value() <= right.castToFloat().value());
    }

    public ObjectType greaterThan(final ObjectType left, final ObjectType right) {
        return BooleanType.valueOf(left.castToFloat().value() > right.castToFloat().value());
    }

    public ObjectType greaterThanOrEqual(final ObjectType left, final ObjectType right) {
        return BooleanType.valueOf(left.castToFloat().value() >= right.castToFloat().value());
    }
}

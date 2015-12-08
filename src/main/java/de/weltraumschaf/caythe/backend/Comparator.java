package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.backend.Pool.Value;

/**
 */
final class Comparator {

    private final BoolOperations bool = new BoolOperations();

    Value equal(final Value left, final Value right) {
        if (sameType(left, right)) {
            return left.getValue().equals(right.getValue()) ? Value.TRUE : Value.FALSE;
        }

        if (left.isNil()) {
            return equal(left.asType(right.getType()), right);
        }

        return equal(left, right.asType(left.getType()));
    }

    Value notEqual(final Value left, final Value right) {
        return bool.not(equal(left, right));
    }

    Value greaterThan(final Value left, final Value right) {
        if (sameType(left, right)) {

        }

        return Value.FALSE;
    }

    Value lessThan(final Value left, final Value right) {
        return bool.not(greaterThan(left, right));
    }

    Value greaterEqual(final Value left, final Value right) {
        return bool.or(greaterThan(left, right), equal(left, right));
    }

    Value lessEqual(final Value left, final Value right) {
        return bool.or(lessThan(left, right), equal(left, right));
    }

    private boolean sameType(final Value left, final Value right) {
        return left.getType() == right.getType();
    }
}

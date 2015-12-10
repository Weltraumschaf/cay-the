package de.weltraumschaf.caythe.backend;

/**
 * Compares relational given values.
 *
 * @since 1.0.0
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
            if (left.isType(Type.STRING)) {
                return Value.newBool(left.asString().compareTo(right.asString()) == 1);
            } else {
                return Value.newBool(left.asFloat() > right.asFloat());
            }
        }

        if (left.isNil()) {
            return greaterThan(left.asType(right.getType()), right);
        }

        return greaterThan(left, right.asType(left.getType()));
    }

    Value lessThan(final Value left, final Value right) {
        if (sameType(left, right)) {
            if (left.isType(Type.STRING)) {
                return Value.newBool(left.asString().compareTo(right.asString()) == -1);
            } else {
                return Value.newBool(left.asFloat() < right.asFloat());
            }
        }

        if (left.isNil()) {
            return lessThan(left.asType(right.getType()), right);
        }

        return lessThan(left, right.asType(left.getType()));
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

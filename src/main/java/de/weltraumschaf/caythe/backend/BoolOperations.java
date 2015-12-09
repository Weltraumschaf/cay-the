package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.backend.Pool.Value;

/**
 * Performs boolean operations on given values.
 *
 * @since 1.0.0
 */
final class BoolOperations {

    Value and(final Value left, final Value right) {
        return Value.newBool(left.asBool() && right.asBool());
    }

    Value or(final Value left, final Value right) {
        return Value.newBool(left.asBool() || right.asBool());
    }

    Value not(final Value in) {
        return Value.newBool(!in.asBool());
    }
}

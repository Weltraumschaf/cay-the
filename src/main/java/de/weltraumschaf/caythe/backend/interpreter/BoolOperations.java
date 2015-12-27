package de.weltraumschaf.caythe.backend.interpreter;

import de.weltraumschaf.caythe.backend.symtab.Value;

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

package de.weltraumschaf.caythe.backend.interpreter;

import de.weltraumschaf.caythe.backend.symtab.Value;

/**
 * Performs boolean operations on given values.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
final class BoolOperations {

    /**
     * Does logical and operation for the both arguments.
     *
     * @param left must not be {@code null}
     * @param right must not be {@code null}
     * @return never {@code null}
     */
    Value and(final Value left, final Value right) {
        return Value.newBool(left.asBool() && right.asBool());
    }

    /**
     * Does logical or operation for the both arguments.
     *
     * @param left must not be {@code null}
     * @param right must not be {@code null}
     * @return never {@code null}
     */
    Value or(final Value left, final Value right) {
        return Value.newBool(left.asBool() || right.asBool());
    }

    /**
     * Does logical not operation for the argument.
     *
     * @param in must not be {@code null}
     * @return never {@code null}
     */
    Value not(final Value in) {
        return Value.newBool(!in.asBool());
    }
}

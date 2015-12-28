package de.weltraumschaf.caythe.backend.interpreter;

import de.weltraumschaf.caythe.backend.symtab.BuildInTypeSymbol;
import de.weltraumschaf.caythe.backend.symtab.Value;
import java.util.Objects;

/**
 * Compares relational given values.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
final class Comparator {

    /**
     * Used for logical operations.
     */
    private final BoolOperations bool = new BoolOperations();

    /**
     * Does equality operation for the both arguments.
     *
     * @param left must not be {@code null}
     * @param right must not be {@code null}
     * @return never {@code null}
     */
    Value equal(final Value left, final Value right) {
        if (sameType(left, right)) {
            return left.getValue().equals(right.getValue()) ? Value.TRUE : Value.FALSE;
        }

        if (left.isNil()) {
            return equal(left.asType(right.getType()), right);
        }

        return equal(left, right.asType(left.getType()));
    }

    /**
     * Does non-equality operation for the both arguments.
     *
     * @param left must not be {@code null}
     * @param right must not be {@code null}
     * @return never {@code null}
     */
    Value notEqual(final Value left, final Value right) {
        return bool.not(equal(left, right));
    }

    /**
     * Does greater than operation for the both arguments.
     *
     * @param left must not be {@code null}
     * @param right must not be {@code null}
     * @return never {@code null}
     */
    Value greaterThan(final Value left, final Value right) {
        if (sameType(left, right)) {
            if (left.isOfType(BuildInTypeSymbol.STRING)) {
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

    /**
     * Does less than operation for the both arguments.
     *
     * @param left must not be {@code null}
     * @param right must not be {@code null}
     * @return never {@code null}
     */
    Value lessThan(final Value left, final Value right) {
        if (sameType(left, right)) {
            if (left.isOfType(BuildInTypeSymbol.STRING)) {
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

    /**
     * Does greater than or equality operation for the both arguments.
     *
     * @param left must not be {@code null}
     * @param right must not be {@code null}
     * @return never {@code null}
     */
    Value greaterEqual(final Value left, final Value right) {
        return bool.or(greaterThan(left, right), equal(left, right));
    }

    /**
     * Does less than or equality operation for the both arguments.
     *
     * @param left must not be {@code null}
     * @param right must not be {@code null}
     * @return never {@code null}
     */
    Value lessEqual(final Value left, final Value right) {
        return bool.or(lessThan(left, right), equal(left, right));
    }

    /**
     * Whether two values have the same type or not.
     *
     * @param left may be {@code null}
     * @param right may be {@code null}
     * @return {@code true} if same, else {@code false}
     */
    private boolean sameType(final Value left, final Value right) {
        return Objects.equals(left.getType(), right.getType());
    }
}

package de.weltraumschaf.caythe.backend.interpreter;

import de.weltraumschaf.caythe.backend.symtab.Type;
import de.weltraumschaf.caythe.backend.symtab.BuildInTypeSymbol;
import de.weltraumschaf.caythe.backend.symtab.Value;

/**
 * Provides mathematic operations.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
final class MathOperations {

    /**
     * Adds to values.
     *
     * @param left must not be {@code null}
     * @param right must not be {@code null}
     * @return never {@code null}
     */
    Value add(final Value left, final Value right) {
        final Type type = left.getType();

        if (BuildInTypeSymbol.NIL.equals(type)) {
            if (right.isNil()) {
                return Value.NIL;
            } else {
                return add(left.asType(right.getType()), right);
            }
        } else if (BuildInTypeSymbol.BOOL.equals(type)) {
            return Value.NIL;
        } else if (BuildInTypeSymbol.INT.equals(type)) {
            return Value.newInt(left.asInt() + right.asInt());
        } else if (BuildInTypeSymbol.FLOAT.equals(type)) {
            return Value.newFloat(left.asFloat() + right.asFloat());
        } else if (BuildInTypeSymbol.STRING.equals(type)) {
            return Value.newString(left.asString() + right.asString());
        } else {
            throw new IllegalArgumentException(String.format("Unsupported type '%s'!", type));
        }
    }

    /**
     * Subtracts to values.
     *
     * @param left must not be {@code null}
     * @param right must not be {@code null}
     * @return never {@code null}
     */
    Value sub(final Value left, final Value right) {
        final Type type = left.getType();

        if (BuildInTypeSymbol.NIL.equals(type)) {
            if (right.isNil()) {
                return Value.NIL;
            } else {
                return add(left.asType(right.getType()), right);
            }
        } else if (BuildInTypeSymbol.BOOL.equals(type)) {
            return Value.NIL;
        } else if (BuildInTypeSymbol.INT.equals(type)) {
            return Value.newInt(left.asInt() - right.asInt());
        } else if (BuildInTypeSymbol.FLOAT.equals(type)) {
            return Value.newFloat(left.asFloat() - right.asFloat());
        } else if (BuildInTypeSymbol.STRING.equals(type)) {
            return Value.NIL;
        } else {
            throw new IllegalArgumentException(String.format("Unsupported type '%s'!", type));
        }
    }

    /**
     * Multiply to values.
     *
     * @param left must not be {@code null}
     * @param right must not be {@code null}
     * @return never {@code null}
     */
    Value mul(final Value left, final Value right) {
        final Type type = left.getType();

        if (BuildInTypeSymbol.NIL.equals(type)) {
            if (right.isNil()) {
                return Value.NIL;
            } else {
                return add(left.asType(right.getType()), right);
            }
        } else if (BuildInTypeSymbol.BOOL.equals(type)) {
            return Value.NIL;
        } else if (BuildInTypeSymbol.INT.equals(type)) {
            return Value.newInt(left.asInt() * right.asInt());
        } else if (BuildInTypeSymbol.FLOAT.equals(type)) {
            return Value.newFloat(left.asFloat() * right.asFloat());
        } else if (BuildInTypeSymbol.STRING.equals(type)) {
            return Value.NIL;
        } else {
            throw new IllegalArgumentException(String.format("Unsupported type '%s'!", type));
        }
    }

    /**
     * Divides to values.
     *
     * @param left must not be {@code null}
     * @param right must not be {@code null}
     * @return never {@code null}
     */
    Value div(final Value left, final Value right) {
        final Type type = left.getType();

        if (BuildInTypeSymbol.NIL.equals(type)) {
            if (right.isNil()) {
                return Value.NIL;
            } else {
                return add(left.asType(right.getType()), right);
            }
        } else if (BuildInTypeSymbol.BOOL.equals(type)) {
            return Value.NIL;
        } else if (BuildInTypeSymbol.INT.equals(type)) {
            return Value.newInt(left.asInt() / right.asInt());
        } else if (BuildInTypeSymbol.FLOAT.equals(type)) {
            return Value.newFloat(left.asFloat() / right.asFloat());
        } else if (BuildInTypeSymbol.STRING.equals(type)) {
            return Value.NIL;
        } else {
            throw new IllegalArgumentException(String.format("Unsupported type '%s'!", type));
        }
    }

    /**
     * Modulus to values.
     *
     * @param left must not be {@code null}
     * @param right must not be {@code null}
     * @return never {@code null}
     */
    Value mod(final Value left, final Value right) {
        final Type type = left.getType();

        if (BuildInTypeSymbol.NIL.equals(type)) {
            if (right.isNil()) {
                return Value.NIL;
            } else {
                return add(left.asType(right.getType()), right);
            }
        } else if (BuildInTypeSymbol.BOOL.equals(type)) {
            return Value.NIL;
        } else if (BuildInTypeSymbol.INT.equals(type)) {
            return Value.newInt(left.asInt() % right.asInt());
        } else if (BuildInTypeSymbol.FLOAT.equals(type)) {
            return Value.newFloat(left.asFloat() % right.asFloat());
        } else if (BuildInTypeSymbol.STRING.equals(type)) {
            return Value.NIL;
        } else {
            throw new IllegalArgumentException(String.format("Unsupported type '%s'!", type));
        }
    }

    /**
     * Powers a base to an exponent.
     *
     * @param base must not be {@code null}
     * @param exponent must not be {@code null}
     * @return never {@code null}
     */
    Value pow(final Value base, final Value exponent) {
        final Type type = base.getType();

        if (BuildInTypeSymbol.NIL.equals(type) || BuildInTypeSymbol.BOOL.equals(type)) {
            return Value.NIL;
        } else if (BuildInTypeSymbol.INT.equals(type) || BuildInTypeSymbol.FLOAT.equals(type)) {
            final Double result = Math.pow(base.asFloat().doubleValue(), exponent.asFloat().doubleValue());
            return Value.newFloat(result.floatValue());
        } else if (BuildInTypeSymbol.STRING.equals(type)) {
            return Value.NIL;
        } else {
            throw new IllegalArgumentException(String.format("Unsupported type '%s'!", type));
        }
    }
}

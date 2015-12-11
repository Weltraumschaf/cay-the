package de.weltraumschaf.caythe.backend.interpreter;

import de.weltraumschaf.caythe.backend.Type;
import de.weltraumschaf.caythe.backend.Value;

/**
 * @since 1.0.0
 */
final class MathOperations {

    Value add(final Value left, final Value right) {
        final Type type = left.getType();

        switch (type) {
            case NIL:
                if (right.isNil()) {
                    return Value.NIL;
                } else {
                    return add(left.asType(right.getType()), right);
                }
            case BOOL:
                return Value.NIL;
            case INT:
                return Value.newInt(left.asInt() + right.asInt());
            case FLOAT:
                return Value.newFloat(left.asFloat() + right.asFloat());
            case STRING:
                return Value.newString(left.asString() + right.asString());
            default:
                throw new IllegalArgumentException(String.format("Unsupported type '%s'!", type));
        }
    }

    Value sub(final Value left, final Value right) {
        final Type type = left.getType();

        switch (type) {
            case NIL:
                if (right.isNil()) {
                    return Value.NIL;
                } else {
                    return add(left.asType(right.getType()), right);
                }
            case BOOL:
                return Value.NIL;
            case INT:
                return Value.newInt(left.asInt() - right.asInt());
            case FLOAT:
                return Value.newFloat(left.asFloat() - right.asFloat());
            case STRING:
                return Value.NIL;
            default:
                throw new IllegalArgumentException(String.format("Unsupported type '%s'!", type));
        }
    }

    Value mul(final Value left, final Value right) {
        final Type type = left.getType();

        switch (type) {
            case NIL:
                if (right.isNil()) {
                    return Value.NIL;
                } else {
                    return add(left.asType(right.getType()), right);
                }
            case BOOL:
                return Value.NIL;
            case INT:
                return Value.newInt(left.asInt() * right.asInt());
            case FLOAT:
                return Value.newFloat(left.asFloat() * right.asFloat());
            case STRING:
                return Value.NIL;
            default:
                throw new IllegalArgumentException(String.format("Unsupported type '%s'!", type));
        }
    }

    Value div(final Value left, final Value right) {
        final Type type = left.getType();

        switch (type) {
            case NIL:
                if (right.isNil()) {
                    return Value.NIL;
                } else {
                    return add(left.asType(right.getType()), right);
                }
            case BOOL:
                return Value.NIL;
            case INT:
                return Value.newInt(left.asInt() / right.asInt());
            case FLOAT:
                return Value.newFloat(left.asFloat() / right.asFloat());
            case STRING:
                return Value.NIL;
            default:
                throw new IllegalArgumentException(String.format("Unsupported type '%s'!", type));
        }
    }

    Value mod(final Value left, final Value right) {
        final Type type = left.getType();

        switch (type) {
            case NIL:
                if (right.isNil()) {
                    return Value.NIL;
                } else {
                    return add(left.asType(right.getType()), right);
                }
            case BOOL:
                return Value.NIL;
            case INT:
                return Value.newInt(left.asInt() % right.asInt());
            case FLOAT:
                return Value.newFloat(left.asFloat() % right.asFloat());
            case STRING:
                return Value.NIL;
            default:
                throw new IllegalArgumentException(String.format("Unsupported type '%s'!", type));
        }
    }

    Value pow(final Value base, final Value exponent) {
        final Type type = base.getType();

        switch (type) {
            case NIL:
                return Value.NIL;
            case BOOL:
                return Value.NIL;
            case INT:
            case FLOAT:
                final Double result = Math.pow(base.asFloat().doubleValue(), exponent.asFloat().doubleValue());
                return Value.newFloat(result.floatValue());
            case STRING:
                return Value.NIL;
            default:
                throw new IllegalArgumentException(String.format("Unsupported type '%s'!", type));
        }
    }
}

package de.weltraumschaf.caythe.backend.interpreter;

import de.weltraumschaf.caythe.backend.symtab.Type;
import de.weltraumschaf.caythe.backend.symtab.BuildInTypeSymbol;
import de.weltraumschaf.caythe.backend.symtab.Value;

/**
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
final class MathOperations {

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

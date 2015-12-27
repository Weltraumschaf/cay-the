package de.weltraumschaf.caythe.backend.symtab;

import de.weltraumschaf.commons.validate.Validate;
import java.util.Objects;

/**
 * Represents a stored value.
 * <p>
 * This class is immutable by design.
 * </p>
 *
 * @since 1.0.0
 */
public final class Value {

    private static final Object NIL_VALUE = new Object();
    /**
     * A not available value.
     */
    public static final Value NIL = new Value(BuildInTypeSymbol.NIL, NIL_VALUE);
    public static final Value TRUE = new Value(BuildInTypeSymbol.BOOL, Boolean.TRUE);
    public static final Value FALSE = new Value(BuildInTypeSymbol.BOOL, Boolean.FALSE);
    public static final Value DEFAULT_BOOL = FALSE;
    public static final Value DEFAULT_INT = new Value(BuildInTypeSymbol.INT, 0);
    public static final Value DEFAULT_FLOAT = new Value(BuildInTypeSymbol.FLOAT, 0.0f);
    public static final Value DEFAULT_STRING = new Value(BuildInTypeSymbol.STRING, "");

    private final Type type;
    private final Object value;

    Value(final Type type, final Object value) {
        super();
        this.type = Validate.notNull(type, "type");
        this.value = Validate.notNull(value, "value");
    }

    public Type getType() {
        return type;
    }

    public boolean isType(final Type wanted) {
        return type == wanted;
    }

    public boolean isNil() {
        return isType(BuildInTypeSymbol.NIL);
    }

    public Object getValue() {
        if (isNil()) {
            return NIL_VALUE;
        }

        return value;
    }

    public Value asType(final Type wanted) {
        if (wanted == type) {
            return this;
        }

        if (wanted.equals(BuildInTypeSymbol.BOOL)) {
            return newBool(asBool());
        } else if (wanted.equals(BuildInTypeSymbol.INT)) {
            return newInt(asInt());
        } else if (wanted.equals(BuildInTypeSymbol.FLOAT)) {
            return newFloat(asFloat());
        } else if (wanted.equals(BuildInTypeSymbol.STRING)) {
            return newString(asString());
        }

        throw new IllegalArgumentException(String.format("Unsupported type '%s'!", wanted));
    }

    public Boolean asBool() {
        if (type.equals(BuildInTypeSymbol.NIL)) {
            return Boolean.FALSE;
        } else if (type.equals(BuildInTypeSymbol.BOOL)) {
            return (Boolean) value;
        } else if (type.equals(BuildInTypeSymbol.INT)) {
            return 0 != (Integer) value;
        } else if (type.equals(BuildInTypeSymbol.FLOAT)) {
            return 0.0 != (Float) value;
        } else if (type.equals(BuildInTypeSymbol.STRING)) {
            return Boolean.valueOf((String) value);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public Integer asInt() {
        if (type.equals(BuildInTypeSymbol.NIL)) {
            return 0;
        } else if (type.equals(BuildInTypeSymbol.BOOL)) {
            return (Boolean) value ? 1 : 0;
        } else if (type.equals(BuildInTypeSymbol.INT)) {
            return (Integer) value;
        } else if (type.equals(BuildInTypeSymbol.FLOAT)) {
            return ((Float) value).intValue();
        } else if (type.equals(BuildInTypeSymbol.STRING)) {
            try {
                return Integer.parseInt((String) value);
            } catch (final NumberFormatException ex) {
                return 0;
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public Float asFloat() {
        if (type.equals(BuildInTypeSymbol.NIL)) {
            return 0.0f;
        } else if (type.equals(BuildInTypeSymbol.BOOL)) {
            return (Boolean) value ? 1.0f : 0.0f;
        } else if (type.equals(BuildInTypeSymbol.INT)) {
            return ((Integer) value).floatValue();
        } else if (type.equals(BuildInTypeSymbol.FLOAT)) {
            return (Float) value;
        } else if (type.equals(BuildInTypeSymbol.STRING)) {
            try {
                return Float.parseFloat((String) value);
            } catch (final NumberFormatException ex) {
                return 0.0f;
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public String asString() {
        if (type.equals(BuildInTypeSymbol.NIL)) {
            return "";
        } else if (
            type.equals(BuildInTypeSymbol.BOOL)
            || type.equals(BuildInTypeSymbol.INT)
            || type.equals(BuildInTypeSymbol.FLOAT)) {
            return String.valueOf(value);
        } else if (type.equals(BuildInTypeSymbol.STRING)) {
            return (String) value;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Value)) {
            return false;
        }

        final Value other = (Value) obj;
        return Objects.equals(type, other.type)
            && Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }

    @Override
    public String toString() {
        return "Value{" + "type=" + type + ", value=" + value + '}';
    }

    public static Value newBool(final boolean in) {
        return in ? TRUE : FALSE;
    }

    public static Value newInt(final int in) {
        return new Value(BuildInTypeSymbol.INT, in);
    }

    public static Value newFloat(final float in) {
        return new Value(BuildInTypeSymbol.FLOAT, in);
    }

    public static Value newString(final String in) {
        return new Value(BuildInTypeSymbol.STRING, in);
    }
}

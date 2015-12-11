package de.weltraumschaf.caythe.backend;

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
    public static final Value NIL = new Value(Type.NIL, NIL_VALUE);
    public static final Value TRUE = new Value(Type.BOOL, Boolean.TRUE);
    public static final Value FALSE = new Value(Type.BOOL, Boolean.FALSE);
    public static final Value DEFAULT_BOOL = FALSE;
    public static final Value DEFAULT_INT = new Value(Type.INT, 0);
    public static final Value DEFAULT_FLOAT = new Value(Type.FLOAT, 0.0f);
    public static final Value DEFAULT_STRING = new Value(Type.STRING, "");

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
        return isType(Type.NIL);
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

        switch (wanted) {
            case BOOL:
                return newBool(asBool());
            case INT:
                return newInt(asInt());
            case FLOAT:
                return newFloat(asFloat());
            case STRING:
                return newString(asString());
        }

        throw new IllegalArgumentException(String.format("Unsupported type '%s'!", wanted));
    }

    public Boolean asBool() {
        switch (type) {
            case NIL:
                return Boolean.FALSE;
            case BOOL:
                return (Boolean) value;
            case INT:
                return 0 != (Integer) value;
            case FLOAT:
                return 0.0 != (Float) value;
            case STRING:
                return Boolean.valueOf((String) value);
            default:
                throw new UnsupportedOperationException();
        }
    }

    public Integer asInt() {
        switch (type) {
            case NIL:
                return 0;
            case BOOL:
                return (Boolean) value ? 1 : 0;
            case INT:
                return (Integer) value;
            case FLOAT:
                return ((Float) value).intValue();
            case STRING:
                try {
                    return Integer.parseInt((String) value);
                } catch (final NumberFormatException ex) {
                    return 0;
                }
            default:
                throw new UnsupportedOperationException();
        }
    }

    public Float asFloat() {
        switch (type) {
            case NIL:
                return 0.0f;
            case BOOL:
                return (Boolean) value ? 1.0f : 0.0f;
            case INT:
                return ((Integer) value).floatValue();
            case FLOAT:
                return (Float) value;
            case STRING:
                try {
                    return Float.parseFloat((String) value);
                } catch (final NumberFormatException ex) {
                    return 0.0f;
                }
            default:
                throw new UnsupportedOperationException();
        }
    }

    public String asString() {
        switch (type) {
            case NIL:
                return "";
            case BOOL:
            case INT:
            case FLOAT:
                return String.valueOf(value);
            case STRING:
                return (String) value;
            default:
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
        return new Value(Type.INT, in);
    }

    public static Value newFloat(final float in) {
        return new Value(Type.FLOAT, in);
    }

    public static Value newString(final String in) {
        return new Value(Type.STRING, in);
    }
}

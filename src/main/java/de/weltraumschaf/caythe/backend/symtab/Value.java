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
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class Value {

    /**
     * Value for the {@link #NIL nil object}.
     */
    private static final Object NIL_VALUE = new Object();
    /**
     * A not available value.
     */
    public static final Value NIL = new Value(BuildInTypeSymbol.NIL, NIL_VALUE);
    /**
     * Reusable object to represent boolean true constant.
     */
    public static final Value TRUE = new Value(BuildInTypeSymbol.BOOL, Boolean.TRUE);
    /**
     * Reusable object to represent boolean false constant.
     */
    public static final Value FALSE = new Value(BuildInTypeSymbol.BOOL, Boolean.FALSE);
    /**
     * Default value for uninitialized booleans.
     */
    public static final Value DEFAULT_BOOL = FALSE;
    /**
     * Default value for uninitialized integers.
     */
    public static final Value DEFAULT_INT = new Value(BuildInTypeSymbol.INT, 0);
    /**
     * Default value for uninitialized floats.
     */
    public static final Value DEFAULT_FLOAT = new Value(BuildInTypeSymbol.FLOAT, 0.0f);
    /**
     * Default value for uninitialized strings.
     */
    public static final Value DEFAULT_STRING = new Value(BuildInTypeSymbol.STRING, "");

    /**
     * The type of the value.
     */
    private final Type type;
    /**
     * The value itself.
     */
    private final Object value;

    /**
     * Dedicated constructor.
     *
     * @param type must not be {@code null}
     * @param value must not be {@code null}
     */
    Value(final Type type, final Object value) {
        super();
        this.type = Validate.notNull(type, "type");
        this.value = Validate.notNull(value, "value");
    }

    /**
     * Get the type.
     *
     * @return never {@code null}
     */
    public Type getType() {
        return type;
    }

    /**
     * Whether the value is of a particular type.
     *
     * @param wanted may be {@code null}
     * @return {@code true} if it is, else {@code false}
     */
    public boolean isOfType(final Type wanted) {
        return type.equals(wanted);
    }

    /**
     * Whether this is a nil value.
     *
     * @return {@code true} if it is, else {@code false}
     */
    public boolean isNil() {
        return isOfType(BuildInTypeSymbol.NIL);
    }

    /**
     * Get the raw untyped value.
     * @return
     */
    public Object getValue() {
        if (isNil()) {
            return NIL_VALUE;
        }

        return value;
    }

    /**
     * Convert this value to a particular type.
     * <p>
     * Throws {@link IllegalArgumentException} if wanted type is not supported.
     * </p>
     *
     * @param wanted must not be {@code null}
     * @return never {@code null}, always new instance, but self if wanted type is the same type
     */
    public Value asType(final Type wanted) {
        Validate.notNull(wanted, "wanted");

        if (isOfType(wanted)) {
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

    /**
     * Get the {@link #getValue() value} as boolean.
     * <p>
     * Throws {@link IllegalArgumentException} if {@link #getType() type} is not supported.
     * </p>
     *
     * @return never {@code null}
     */
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
            throw new IllegalArgumentException(String.format("Unsupported type '%s'!", type));
        }
    }

    /**
     * Get the {@link #getValue() value} as integer.
     * <p>
     * Throws {@link IllegalArgumentException} if {@link #getType() type} is not supported.
     * </p>
     *
     * @return never {@code null}
     */
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
            throw new IllegalArgumentException(String.format("Unsupported type '%s'!", type));
        }
    }

    /**
     * Get the {@link #getValue() value} as float.
     * <p>
     * Throws {@link IllegalArgumentException} if {@link #getType() type} is not supported.
     * </p>
     *
     * @return never {@code null}
     */
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
            throw new IllegalArgumentException(String.format("Unsupported type '%s'!", type));
        }
    }

    /**
     * Get the {@link #getValue() value} as string.
     * <p>
     * Throws {@link IllegalArgumentException} if {@link #getType() type} is not supported.
     * </p>
     *
     * @return never {@code null}
     */
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
            throw new IllegalArgumentException(String.format("Unsupported type '%s'!", type));
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
        return "Value{"
            + "value=" + (isNil() ? "nil" : value) + ", "
            + "type=" + type.getName()
            + '}';
    }

    /**
     * Factory method to create new booleans.
     *
     * @param in any boolean
     * @return always the same instances for {@code true} or {@code false}
     */
    public static Value newBool(final boolean in) {
        return in ? TRUE : FALSE;
    }

    /**
     * Factory method to create new integers.
     *
     * @param in any integer
     * @return never {@code null}
     */
    public static Value newInt(final int in) {
        return new Value(BuildInTypeSymbol.INT, in);
    }

    /**
     * Factory method to create new floats.
     *
     * @param in any float
     * @return never {@code null}
     */
    public static Value newFloat(final float in) {
        return new Value(BuildInTypeSymbol.FLOAT, in);
    }

    /**
     * Factory method to create new strings.
     *
     * @param in any string
     * @return never {@code null}
     */
    public static Value newString(final String in) {
        return new Value(BuildInTypeSymbol.STRING, in);
    }
}

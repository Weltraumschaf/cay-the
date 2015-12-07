package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.commons.validate.Validate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A pool contains values.
 * <p>
 * This may be constant values or variable values.
 * </p>
 *
 * @since 1.0.0
 */
final class Pool {

    /**
     * Holds the value by its slot ID.
     */
    private final Map<Integer, Value> data = new HashMap<>();

    Value get(final int index) {
        if (data.containsKey(index)) {
            return data.get(index);
        }

        return Value.NIL;
    }

    void set(final int index, final Value value) {
        data.put(index, value);
    }

    /**
     * Represents a stored value.
     * <p>
     * This class is immutable by design.
     * </p>
     *
     * @since 1.0.0
     */
    static final class Value {

        private static final Object NIL_VALUE = new Object();
        /**
         * A not available value.
         */
        static final Value NIL = new Value(Type.NIL, NIL_VALUE);
        static final Value TRUE = new Value(Type.BOOL, Boolean.TRUE);
        static final Value FALSE = new Value(Type.BOOL, Boolean.FALSE);
        static final Value DEFAULT_BOOL = FALSE;
        static final Value DEFAULT_INT = new Value(Type.INT, 0);
        static final Value DEFAULT_FLOAT = new Value(Type.FLOAT, 0.0f);
        static final Value DEFAULT_STRING = new Value(Type.STRING, "");

        private final Type type;
        private final Object value;

        Value(final Type type, final Object value) {
            super();
            this.type = Validate.notNull(type, "type");
            this.value = Validate.notNull(value, "value");
        }

        Type getType() {
            return type;
        }

        boolean isType(final Type wanted) {
            return type == wanted;
        }

        boolean isNil() {
            return isType(Type.NIL);
        }

        Object getValue() {
            if (isNil()) {
                return NIL_VALUE;
            }

            return value;
        }

        Boolean asBool() {
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

        Integer asInt() {
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

        Float asFloat() {
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

        String asString() {
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

    }

    /**
     * Available types.
     *
     * @since 1.0.0
     */
    static enum Type {

        /**
         * Not existing values.
         */
        NIL,
        /**
         * Boolean values.
         */
        BOOL,
        /**
         * Integer number values.
         */
        INT,
        /**
         * Floating point number values.
         */
        FLOAT,
        /**
         * String values.
         */
        STRING;
    }
}

package de.weltraumschaf.caythe;

import de.weltraumschaf.commons.validate.Validate;
import java.util.HashMap;
import java.util.Map;

/**
 */
final class Pool {

    private static final Value NIL = new Value(Type.NIL, new Object());

    private final Map<Integer, Value> data = new HashMap<>();

    Value get(final int index) {
        if (data.containsKey(index)) {
            return data.get(index);
        }

        return NIL;
    }

    void set(final int index, final Value value) {
        data.put(index, value);
    }

    static final class Value {

        private final Type type;
        private final Object value;

        public Value(final Type type, final Object value) {
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

        public Object getValue() {
            return value;
        }


    }

    static enum Type {

        NIL, BOOL, INT, FLOAT, STRING;
    }
}

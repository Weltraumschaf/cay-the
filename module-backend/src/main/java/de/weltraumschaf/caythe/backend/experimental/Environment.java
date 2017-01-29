package de.weltraumschaf.caythe.backend.experimental;

import de.weltraumschaf.caythe.backend.experimental.types.NullType;
import de.weltraumschaf.caythe.backend.experimental.types.ObjectType;

import java.util.HashMap;
import java.util.Map;

public final class Environment {
    private final Map<String, MemorySlot> store = new HashMap<>();
    private final Environment outer;

    public Environment() {
        this(null);
    }

    private Environment(final Environment outer) {
        super();
        this.outer = outer;
    }

    public void setVar(final String identifier, final ObjectType value) {
        if (store.containsKey(identifier) && store.get(identifier).hasType(SlotType.CONST)) {
            throw new RuntimeException("Can not reset const value for identifier " + identifier + "!");
        }

        store.put(identifier, new MemorySlot(value, SlotType.VAR));
    }

    public ObjectType get(final String identifier) {
        if (store.containsKey(identifier)) {
            return store.get(identifier).getValue();
        }

        if (outer != null) {
            return outer.get(identifier);
        }

        return NullType.NULL;
    }

    public boolean has(final String identifier) {
        if (store.containsKey(identifier)) {
            return true;
        }

        if (outer != null) {
            return outer.has(identifier);
        }

        return false;
    }

    public Environment createChild() {
        return new Environment(this);
    }

    private static final class MemorySlot {
        private final ObjectType value;
        private final SlotType type;

        MemorySlot(final ObjectType value, SlotType type) {
            super();
            this.value = value;
            this.type = type;
        }

        ObjectType getValue() {
            return value;
        }

        boolean hasType(final SlotType type) {
            return this.type == type;
        }
    }

    private enum SlotType {
        VAR, CONST;
    }
}

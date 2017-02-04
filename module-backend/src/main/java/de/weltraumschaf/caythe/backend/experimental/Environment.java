package de.weltraumschaf.caythe.backend.experimental;

import de.weltraumschaf.caythe.CayThe;
import de.weltraumschaf.caythe.backend.experimental.types.NullType;
import de.weltraumschaf.caythe.backend.experimental.types.ObjectType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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

    public void setConst(final String identifier, final ObjectType value) {
        if (store.containsKey(identifier)) {
            throw new RuntimeException("Can not redeclare const for identifier " + identifier + "!");
        }

        store.put(identifier, new MemorySlot(value, SlotType.CONST));
    }

    public ObjectType get(final String identifier) {
        if (store.containsKey(identifier)) {
            return store.get(identifier).getValue();
        }

        if (hasOuter()) {
            return outer.get(identifier);
        }

        return NullType.NULL;
    }

    private boolean hasOuter() {
        return outer != null;
    }

    public String dump() {
        final StringBuilder buffer = new StringBuilder();

        if (hasOuter()) {
            buffer.append(outer.dump());
        }

        buffer.append("ENVIRONMENT").append(CayThe.NL)
            .append("===========").append(CayThe.NL);

        if (store.isEmpty()) {
            buffer.append("  <empty>").append(CayThe.NL);
        } else {
            for (Map.Entry<String, MemorySlot> entry : store.entrySet()) {
                final MemorySlot slot = entry.getValue();
                final String value = slot.getValue().inspect();
                buffer.append("  ").append(entry.getKey()).append(" [").append(slot.getType())
                    .append("] = ").append(value).append(CayThe.NL);
            }
        }

        return buffer.toString();
    }

    public boolean has(final String identifier) {
        if (store.containsKey(identifier)) {
            return true;
        }

        if (hasOuter()) {
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

        public SlotType getType() {
            return type;
        }

        boolean hasType(final SlotType type) {
            return this.type == type;
        }

        @Override
        public String toString() {
            return "MemorySlot{" +
                "value=" + value +
                ", type=" + type +
                '}';
        }

        @Override
        public boolean equals(final Object o) {
            if (!(o instanceof MemorySlot)) {
                return false;
            }

            final MemorySlot that = (MemorySlot) o;
            return Objects.equals(value, that.value) &&
                type == that.type;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, type);
        }
    }

    private enum SlotType {
        VAR, CONST;
    }
}

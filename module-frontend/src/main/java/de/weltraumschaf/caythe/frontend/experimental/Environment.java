package de.weltraumschaf.caythe.frontend.experimental;

import de.weltraumschaf.caythe.frontend.experimental.types.NullType;
import de.weltraumschaf.caythe.frontend.experimental.types.ObjectType;

import java.util.HashMap;
import java.util.Map;

public final class Environment {
    private final Map<String, ObjectType> store = new HashMap<>();
    private final Environment outer;

    public Environment() {
        this(null);
    }

    private Environment(final Environment outer) {
        super();
        this.outer = outer;
    }

    public void set(final String identifier, final ObjectType object) {
        store.put(identifier, object);
    }

    public ObjectType get(final String identifier) {
        if (store.containsKey(identifier)) {
            return store.get(identifier);
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
}

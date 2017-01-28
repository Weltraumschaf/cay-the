package de.weltraumschaf.caythe.frontend.experimental.types;

import java.util.Map;
import java.util.Objects;

public final class HashType implements ObjectType {
    private final Map<ObjectType, ObjectType> values;

    public HashType(final Map<ObjectType, ObjectType> values) {
        super();
        this.values = values;
    }

    @Override
    public Type type() {
        return Type.HASH;
    }

    @Override
    public String inspect() {
        return values.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof HashType)) {
            return false;
        }

        final HashType hashType = (HashType) o;
        return Objects.equals(values, hashType.values);
    }

    public ObjectType get(final ObjectType key) {
        return has(key) ?
            values.get(key):
            NullType.NULL;
    }

    public boolean has(final ObjectType key) {
        return values.containsKey(key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }

    @Override
    public String toString() {
        return "HashType{" +
            "values=" + values +
            '}';
    }
}

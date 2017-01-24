package de.weltraumschaf.caythe.frontend.experimental.types;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class HashType implements ObjectType {
    private final Map<ObjectType, ObjectType> values = new HashMap<>();

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

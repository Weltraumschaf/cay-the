package de.weltraumschaf.caythe.frontend.experimental.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ArrayType implements ObjectType {
    private final List<ObjectType> elements;

    public ArrayType(final List<ObjectType> elements) {
        super();
        this.elements = new ArrayList<>(elements);
    }

    @Override
    public Type type() {
        return Type.ARRAY;
    }

    @Override
    public String inspect() {
        return elements.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof ArrayType)) {
            return false;
        }

        final ArrayType arrayType = (ArrayType) o;
        return Objects.equals(elements, arrayType.elements);
    }

    public ObjectType get(final IntegerType index) {
        return has(index) ?
            elements.get((int)index.value()) :
            NullType.NULL ;
    }

    public boolean has(final IntegerType index) {
        return elements.get((int)index.value()) != null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(elements);
    }

    @Override
    public String toString() {
        return "ArrayType{" +
            "elements=" + elements +
            '}';
    }
}

package de.weltraumschaf.caythe.backend.types;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class BuiltinType implements ObjectType {

    private final Function<Collection<ObjectType>, ObjectType> fn;

    public BuiltinType(Function<Collection<ObjectType>, ObjectType> fn) {
        super();
        this.fn = fn;
    }

    @Override
    public Type type() {
        return Type.BUILTIN;
    }

    @Override
    public String inspect() {
        return "builtin";
    }

    public ObjectType apply(final List<ObjectType> arguments) {
        return fn.apply(arguments);
    }
}

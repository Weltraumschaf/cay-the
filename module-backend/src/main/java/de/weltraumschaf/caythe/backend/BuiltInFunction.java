package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.backend.types.*;

public enum BuiltInFunction {
    PUTS("puts", new BuiltinType(args -> {
        // FIXME HEre System.out should be injected.
        for (final ObjectType arg : args) {
            if (arg.isOf(Type.ARRAY)) {
                for (final ObjectType o : ((ArrayType)arg).value()) {
                    System.out.println(o.inspect());
                }
            }

            System.out.println(arg.inspect());
        }
        return NullType.NULL;
    }));

    private final String identifier;
    private final BuiltinType object;

    BuiltInFunction(final String identifier, final BuiltinType object) {
        this.identifier = identifier;
        this.object = object;
    }

    public String identifier() {
        return identifier;
    }

    public BuiltinType object() {
        return object;
    }
}

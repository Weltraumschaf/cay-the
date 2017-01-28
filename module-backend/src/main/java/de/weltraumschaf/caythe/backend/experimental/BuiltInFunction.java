package de.weltraumschaf.caythe.backend.experimental;

import de.weltraumschaf.caythe.backend.experimental.types.*;

public enum BuiltInFunction {
    PUTS("puts");

    private final String identifier;

    BuiltInFunction(final String identifier) {
        this.identifier = identifier;
    }

    public String identifier() {
        return identifier;
    }

    public BuiltinType object() {
        return new BuiltinType(args -> {
            for (final ObjectType arg : args) {
                if (arg.isOf(Type.ARRAY)) {
                    for (final ObjectType o : ((ArrayType)arg).value()) {
                        System.out.println(o.inspect());
                    }
                }

                System.out.println(arg.inspect());
            }
            return NullType.NULL;
        });
    }
}

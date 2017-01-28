package de.weltraumschaf.caythe.frontend.experimental;

import de.weltraumschaf.caythe.frontend.experimental.types.BuiltinType;
import de.weltraumschaf.caythe.frontend.experimental.types.NullType;
import de.weltraumschaf.caythe.frontend.experimental.types.ObjectType;

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
                System.out.println(arg.inspect());
            }
            return NullType.NULL;
        });
    }
}

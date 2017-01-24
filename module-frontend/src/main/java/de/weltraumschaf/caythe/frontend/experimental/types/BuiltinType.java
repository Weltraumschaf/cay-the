package de.weltraumschaf.caythe.frontend.experimental.types;

public class BuiltinType implements ObjectType {
    //Fn BuiltinFunction

    @Override
    public Type type() {
        return Type.BUILTIN;
    }

    @Override
    public String inspect() {
        return "builtin";
    }
}

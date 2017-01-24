package de.weltraumschaf.caythe.frontend.experimental.types;

public class FunctionType implements ObjectType {
//    Parameters []*ast.Identifier
//    Body       *ast.BlockStatement
//    Env        *Environment

    @Override
    public Type type() {
        return Type.FUNCTION;
    }

    @Override
    public String inspect() {
        return "fn";
    }
}

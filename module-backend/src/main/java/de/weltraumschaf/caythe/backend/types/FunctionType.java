package de.weltraumschaf.caythe.backend.types;

import de.weltraumschaf.caythe.backend.Environment;
import de.weltraumschaf.caythe.intermediate.ast.AstNode;
import de.weltraumschaf.caythe.intermediate.ast.Identifier;

import java.util.List;

public class FunctionType implements ObjectType {
    private final Environment closureScope;
    private final List<Identifier> parameterIdentifiers;
    private final List<AstNode> body;

    public FunctionType(final Environment scope, final List<Identifier> parameterIdentifiers, final List<AstNode> body) {
        super();
        this.closureScope = scope;
        this.parameterIdentifiers = parameterIdentifiers;
        this.body = body;
    }

    @Override
    public Type type() {
        return Type.FUNCTION;
    }

    @Override
    public String inspect() {
        final StringBuilder buffer = new StringBuilder();
        String parameterIdentifiersString = parameterIdentifiers.toString();
        parameterIdentifiersString = parameterIdentifiersString.substring(1, parameterIdentifiersString.length() -1 );
        buffer.append("fn(").append(parameterIdentifiersString).append(") {").append(body).append('}');
        return buffer.toString();
    }

    @Override
    public String toString() {
        return inspect();
    }

    public Environment getClosureScope() {
        return closureScope;
    }

    public List<Identifier> getParameterIdentifiers() {
        return parameterIdentifiers;
    }

    public List<AstNode> getBody() {
        return body;
    }
}

package de.weltraumschaf.caythe.backend.experimental.types;

import de.weltraumschaf.caythe.backend.CayTheSourceParser;
import de.weltraumschaf.caythe.backend.experimental.Environment;

import java.util.List;

public class FunctionType implements ObjectType {
    private final Environment closureScope;
    private final List<String> parameterIdentifiers;
    private final CayTheSourceParser.StatementContext body;

    public FunctionType(Environment scope, List<String> parameterIdentifiers, CayTheSourceParser.StatementContext body) {
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
        buffer.append("fn(").append(parameterIdentifiersString).append(") {").append(body.getText()).append('}');
        return buffer.toString();
    }

    @Override
    public String toString() {
        return inspect();
    }

    public Environment getClosureScope() {
        return closureScope;
    }

    public List<String> getParameterIdentifiers() {
        return parameterIdentifiers;
    }

    public CayTheSourceParser.StatementContext getBody() {
        return body;
    }
}

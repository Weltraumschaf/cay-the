package de.weltraumschaf.caythe.frontend.experimental.types;

import de.weltraumschaf.caythe.frontend.CayTheSourceParser;
import de.weltraumschaf.caythe.frontend.experimental.Environment;

import java.util.Collection;

public class FunctionType implements ObjectType {
    private final Environment scope;
    private final Collection<String> parameterIdentifiers;
    private final CayTheSourceParser.StatementContext body;

    public FunctionType(Environment scope, Collection<String> parameterIdentifiers, CayTheSourceParser.StatementContext body) {
        super();
        this.scope = scope;
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
}

package de.weltraumschaf.caythe.backend.vm;

import java.util.Arrays;

/**
 * To call, push one of these and pop to return.
 */
final class Context {
    /**
     * parent in the stack or "caller"
     */
    Context invokingContext;
    /**
     * info about function we're executing
     */
    private FuncMetaData metadata;
    int returnip;
    /**
     * args + locals, indexed from 0
     */
    long[] locals;

    Context(Context invokingContext, int returnip, FuncMetaData metadata) {
        super();
        this.invokingContext = invokingContext;
        this.returnip = returnip;
        this.metadata = metadata;
        locals = new long[metadata.nargs() + metadata.nlocals()];
    }


    @Override
    public String toString() {
        return "Context{" +
            "invokingContext=" + invokingContext +
            ", metadata=" + metadata +
            ", returnip=" + returnip +
            ", locals=" + Arrays.toString(locals) +
            '}';
    }
}

package de.weltraumschaf.caythe.backend.vm;

import java.util.Arrays;

/**
 * To call, push one of these and pop to return.
 */
final class Context {
    /**
     * parent in the stack or "caller"
     */
    private final Context invokingContext;
    /**
     * info about function we're executing
     */
    private final FunctionMetaData metadata;
    private final int returnPointer;
    /**
     * args + locals, indexed from 0
     */
    private long[] locals;

    Context(final FunctionMetaData metadata) {
        this(null, 0, metadata);
    }

    Context(final Context invokingContext, final int returnPointer, final FunctionMetaData metadata) {
        super();
        this.invokingContext = invokingContext;
        this.returnPointer = returnPointer;
        this.metadata = metadata;
        locals = new long[metadata.nargs() + metadata.nlocals()];
    }

    int getReturnPointer() {
        return returnPointer;
    }

    Context getInvokingContext() {
        return invokingContext;
    }

    long getLocal(final int registerName) {
        return locals[registerName];
    }

    void setLocal(final int registerName, final long value) {
        locals[registerName] = value;
    }

    @Override
    public String toString() {
        return "Context{" +
            "invokingContext=" + invokingContext +
            ", metadata=" + metadata +
            ", returnPointer=" + returnPointer +
            ", locals=" + Arrays.toString(locals) +
            '}';
    }
}

package de.weltraumschaf.caythe.frontend.experimental;

public final class Debugger {
    /**
     * It is one more because we use two method levels here in the class.
     */
    private static final int CALLER_LEVEL = 3;
    private boolean enabled;

    public Debugger on() {
        enabled = true;
        return this;
    }

    public Debugger off() {
        enabled = false;
        return this;
    }

    public Debugger debug(final String fmt, final Object ... args) {
        if (enabled) {
            System.out.println(String.format(fmt, args));
        }

        return this;
    }

    public Debugger returnValue(final Object value) {
        debug("Return value from %s(): %s", findCaller(), value);
        return this;
    }

    private String findCaller() {
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        final StackTraceElement callerStack = stackTrace[CALLER_LEVEL];
        return callerStack.getMethodName();
    }
}

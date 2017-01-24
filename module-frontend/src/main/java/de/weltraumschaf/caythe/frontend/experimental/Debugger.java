package de.weltraumschaf.caythe.frontend.experimental;

public final class Debugger {
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
        debug("Return value: %s", value);
        return this;
    }
}

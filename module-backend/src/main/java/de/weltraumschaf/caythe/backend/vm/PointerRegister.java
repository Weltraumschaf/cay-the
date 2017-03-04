package de.weltraumschaf.caythe.backend.vm;

final class PointerRegister {
    private int current;

    PointerRegister() {
        this(0);
    }

    PointerRegister(final int current) {
        super();
        this.current = current;
    }

    int current() {
        return current;
    }

    PointerRegister down(final int i) {
        return new PointerRegister(current - i);
    }

    int increment() {
        return ++current;
    }

    int decrement() {
        return --current;
    }

    int setTo(final int pointer) {
        final int oldCurrent = current;
        current = pointer;
        return oldCurrent;
    }

    @Override
    public String toString() {
        return "PointerRegister{" +
            "current=" + current +
            '}';
    }
}

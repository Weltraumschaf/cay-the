package de.weltraumschaf.caythe.backend.vm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

final class Stack {
    private static final Entry EMPTY_SENTINEL = new Entry(0);
    private Entry top = EMPTY_SENTINEL;
    private int size;

    int size() {
        return size;
    }

    void push(final long value) {
        final Entry prev = top;
        top = new Entry(prev, value);
        ++size;
    }

    long pop() {
        if (top == EMPTY_SENTINEL) {
            throw new IllegalStateException();
        }

        final long value = top.value;
        top = top.prev;
        --size;
        return value;
    }

    long get(final PointerRegister pointer) {
        final int stepsToGoBack = calculateOffset(pointer);

        if (stepsToGoBack < 0) {
            throw new IllegalStateException();
        }

        Entry current = top;

        for (int i = 0; i < stepsToGoBack; ++i) {
            if (current == EMPTY_SENTINEL) {
                throw new IllegalStateException();
            }

            current = current.prev;
        }

        return current.value;
    }

    void popDownTo(final PointerRegister pointer) {
        final int stepsToGoBack = calculateOffset(pointer);

        if (stepsToGoBack < 0) {
            throw new IllegalStateException();
        }

        Entry current = top;

        for (int i = 0; i < stepsToGoBack; ++i) {
            if (current == EMPTY_SENTINEL) {
                throw new IllegalStateException();
            }

            current = current.prev;
        }

        top = current;
    }

    String dump() {
        if (0 == size) {
            return "[]";
        }

        final List<Long> values = new ArrayList<>();
        Entry current = top;
        values.add(current.value);

        while (EMPTY_SENTINEL != current.prev) {
            current = current.prev;
            values.add(current.value);
        }

        Collections.reverse(values);
        return '[' + values.stream().map(String::valueOf).collect(Collectors.joining(", ")) + ']';
    }

    @Override
    public String toString() {
        return dump();
    }

    private int calculateOffset(final PointerRegister pointer) {
        return size - pointer.current() - 1;
    }

    private static final class Entry {
        private final long value;
        private Entry prev;

        private Entry(final long value) {
            this(null, value);
        }

        private Entry(final Entry prev, final long value) {
            super();
            this.value = value;
            this.prev = prev;
        }

        @Override
        public String toString() {
            return "Entry{" +
                "value=" + value +
                ", prev=" + prev +
                '}';
        }
    }
}

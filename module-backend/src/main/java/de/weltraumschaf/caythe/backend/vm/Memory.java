package de.weltraumschaf.caythe.backend.vm;

import java.util.HashMap;
import java.util.Map;

final class Memory {
    private Map<Integer, Long> data = new HashMap<>();

    void set(final int address, final long value) {
        data.put(address, value);
    }

    long get(final int address) {
        return data.get(address);
    }
}

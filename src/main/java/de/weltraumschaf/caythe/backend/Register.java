package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.commons.validate.Validate;
import java.util.HashMap;
import java.util.Map;

/**
 */
final class Register {

    /**
     * Maps register address -> register value.
     */
    private final Map<Byte, Integer> registers = new HashMap<>();

    public void set(final byte address, final int value) {
        Validate.greaterThanOrEqual(address, 0, "address");
        registers.put(address, value);
    }

    public int get(final byte address) {
        Validate.greaterThanOrEqual(address, 0, "address");

        if (registers.containsKey(address)) {
            return registers.get(address);
        }

        return 0;
    }
}

package de.weltraumschaf.caythe;

import de.weltraumschaf.commons.validate.Validate;
import java.util.HashMap;
import java.util.Map;

/**
 */
public final class Register {

    /**
     * Maps register address -> register value.
     */
    private final Map<Integer, Integer> registers = new HashMap<>();

    public void set(final int address, final int value) {
        Validate.greaterThanOrEqual(address, 0, "address");
        registers.put(address, value);
    }

    public int get(final int address) {
        Validate.greaterThanOrEqual(address, 0, "address");

        if (registers.containsKey(address)) {
            return registers.get(address);
        }

        return 0;
    }
}

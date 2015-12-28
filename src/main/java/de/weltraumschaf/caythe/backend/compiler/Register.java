package de.weltraumschaf.caythe.backend.compiler;

import de.weltraumschaf.commons.validate.Validate;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides 256 registers for 32 bit values.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
final class Register {

    /**
     * Maps register address -> register value.
     */
    private final Map<Byte, Integer> registers = new HashMap<>();

    /**
     * Set a register.
     * <p>
     * Old values are overwritten.
     * </p>
     *
     * @param address any byte
     * @param value any int
     */
    public void set(final byte address, final int value) {
        Validate.greaterThanOrEqual(address, 0, "address");
        registers.put(address, value);
    }

    /**
     * Get a register value.
     *
     * @param address any byte
     * @return the value from register, 0 by default if register was not set
     */
    public int get(final byte address) {
        Validate.greaterThanOrEqual(address, 0, "address");

        if (registers.containsKey(address)) {
            return registers.get(address);
        }

        return 0;
    }
}

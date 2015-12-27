package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.backend.symtab.Value;
import de.weltraumschaf.commons.validate.Validate;
import java.util.HashMap;
import java.util.Map;

/**
 * A pool contains values.
 * <p>
 * This may be constant values or variable values.
 * </p>
 *
 * @since 1.0.0
 */
public final class Pool {

    /**
     * Holds the value by its slot ID.
     */
    private final Map<Integer, Value> data = new HashMap<>();

    /**
     * Get a value from the pool.
     *
     * @param index must not be negative
     * @return never {@code null}, {@link Value.NIL} if value is not present at given index
     */
    public Value get(final int index) {
        Validate.greaterThanOrEqual(index, 0, "index");

        if (data.containsKey(index)) {
            return data.get(index);
        }

        return Value.NIL;
    }

    /**
     * Set value in pool.
     *
     * @param index index must not be negative
     * @param value must not be {@code null}
     */
    public void set(final int index, final Value value) {
        Validate.greaterThanOrEqual(index, 0, "index");
        Validate.notNull(value, "value");
        data.put(index, value);
    }

}

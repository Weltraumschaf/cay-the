package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.commons.validate.Validate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A pool contains values.
 * <p>
 * This may be constant values or variable values.
 * </p>
 *
 * @since 1.0.0
 */
final class Pool {

    /**
     * Holds the value by its slot ID.
     */
    private final Map<Integer, Value> data = new HashMap<>();

    Value get(final int index) {
        if (data.containsKey(index)) {
            return data.get(index);
        }

        return Value.NIL;
    }

    void set(final int index, final Value value) {
        data.put(index, value);
    }

}

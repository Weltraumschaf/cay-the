package de.weltraumschaf.caythe.backend.interpreter;

import de.weltraumschaf.caythe.backend.symtab.Value;
import de.weltraumschaf.commons.validate.Validate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents multiple return values.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class ReturnValues {

    /**
     * No return values.
     */
    static final ReturnValues NOTHING = new ReturnValues();

    /**
     * Holds the values.
     */
    private final List<Value> values = new ArrayList<>();

    /**
     * Use {@link #NOTHING} instead.
     */
    private ReturnValues() {
        this(new Value[0]);
    }

    /**
     * Dedicated constructor.
     *
     * @param values must not be {@code null}
     */
    ReturnValues(final Value... values) {
        this(Arrays.asList(values));
    }

    ReturnValues(final Collection<Value> values) {
        super();
        this.values.addAll(Validate.notNull(values));
    }

    /**
     * Are there any return values?
     *
     * @return {@code true} if not, else {@code false}
     */
    boolean isNothing() {
        return values.isEmpty();
    }

    /**
     * Is there only a single return value?
     *
     * @return {@code true} if yes, else {@code false}
     */
    boolean isSingleValue() {
        return values.size() == 1;
    }

    /**
     * Are there multiple return values?
     *
     * @return {@code true} if yes, else {@code false}
     */
    boolean isMultiValue() {
        return values.size() > 1;
    }

    /**
     * Get the first return value.
     *
     * @return never {@code null}, {@link Values#NIL} if there is nothing
     */
    Value getFirst() {
        if (isNothing()) {
            return Value.NIL;
        }

        return values.get(0);
    }

    /**
     * Get the return value at given index.
     *
     * @return never {@code null}, {@link Values#NIL} if there is nothing
     */
    Value get(final int index) {
        if (index < values.size()) {
            return values.get(index);
        }

        return Value.NIL;
    }

    /**
     * Get all return values.
     *
     * @return never {@code null}, maybe empty, unmodifiable
     */
    List<Value> get() {
        return Collections.unmodifiableList(values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof ReturnValues)) {
            return false;
        }

        final ReturnValues other = (ReturnValues) obj;
        return values.equals(other.values);
    }

    @Override
    public String toString() {
        return values.toString();
    }

}

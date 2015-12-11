package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.commons.validate.Validate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Table to collect symbols for variables and such.
 *
 * @since 1.0.0
 */
public final class SymbolTable {

    /**
     * Holds the symbol entries mapped by name.
     */
    private final Map<String, Entry> table = new HashMap<>();
    /**
     * Maintains the current slot ID for new entries.
     */
    private int currentSlot;

    /**
     * Look up a symbol by name.
     * <p>
     * Throws an {@link IllegalArgumentException} if the symbol is not in table.
     * </p>
     *
     * @param name must not be {@code null} or empty
     * @return never {@code null}
     */
    public Entry lookup(final String name) {
        if (enered(name)) {
            return table.get(name);
        }

        throw new IllegalArgumentException(String.format("There is no symbol with name '%s'!", name));
    }

    /**
     * Tests if a symbol was entered.
     *
     * @param name must not be {@code null} or empty
     * @return {@link true} if entered, else {@link false}
     */
    public boolean enered(final String name) {
        Validate.notEmpty(name, "name");
        return table.containsKey(name);
    }

    /**
     * Enter a new symbol.
     * <p>
     * Throws an {@link IllegalArgumentException} if it was entered already.
     * </p>
     *
     * @param name must not be {@code null} or empty
     * @return never {@code null}, the new entry in the table
     */
    public Entry enter(final String name) {
        if (enered(name)) {
            throw new IllegalArgumentException(String.format("Symbal with name '%s' already entered in table!", name));
        }

        final Entry symbol = new Entry(currentSlot++, name);
        table.put(name, symbol);
        return symbol;
    }

    /**
     * Symbol entry.
     */
    public static final class Entry {

        /**
         * The slot ID of the entry.
         */
        private final int id;
        /**
         * The name of the ID.
         */
        private final String name;

        /**
         * Dedicated constructor.
         *
         * @param id must not be negative
         * @param name must not be {@code null} or empty
         */
        public Entry(int id, final String name) {
            super();
            Validate.greaterThanOrEqual(id, 0, "id");
            this.id = id;
            this.name = Validate.notEmpty(name, "name");
        }

        /**
         * Get the slot ID.
         *
         * @return not negative
         */
        public int getId() {
            return id;
        }

        /**
         * Get the symbol name.
         *
         * @return never {@code null} or empty
         */
        public String getName() {
            return name;
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, id);
        }

        @Override
        public boolean equals(final Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }

            final Entry other = (Entry) obj;
            return Objects.equals(name, other.name)
                && Objects.equals(id, other.id);
        }

        @Override
        public String toString() {
            return "Entry{" + "id=" + id + ", name=" + name + '}';
        }

    }
}

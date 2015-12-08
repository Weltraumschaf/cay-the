package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.commons.validate.Validate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Table to collect symbols for variables and such.
 */
final class SymbolTable {

    private final Map<String, Entry> table = new HashMap<>();
    private int currentSlot;

    public Entry lookup(final String name) {
        if (enered(name)) {
            return table.get(name);
        }

        return null;
    }

    public boolean enered(final String name) {
        return table.containsKey(name);
    }

    public Entry enter(final String name) {
        if (enered(name)) {
            throw new IllegalArgumentException(String.format("Symbal with name '%s' already entered in table!", name));
        }

        final Entry symbol = new Entry(currentSlot++, name);
        table.put(name, symbol);
        return symbol;
    }

    /**
     * Symbols entries.
     */
    static final class Entry {

        private final int id;
        private final String name;

        public Entry(int id, final String name) {
            super();
            Validate.greaterThanOrEqual(id, 0, "id");
            this.id = id;
            this.name = Validate.notEmpty(name, "name");
        }

        public String getId() {
            return String.valueOf(id);
        }

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

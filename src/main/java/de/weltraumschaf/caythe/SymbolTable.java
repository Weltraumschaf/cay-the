package de.weltraumschaf.caythe;

import com.sun.org.apache.bcel.internal.classfile.Code;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 */
final class SymbolTable {

    private final Map<String, Entry> table = new HashMap<>();
    private int currentSlot;

    public Entry lookup(final String name) {
        if (table.containsKey(name)) {
            return table.get(name);
        }

        return null;
    }

    public Entry enter(final String name) {
        if (null != lookup(name)) {
            throw new IllegalArgumentException(String.format("Symbal with name '%s' already entered in table!", name));
        }

        final Entry symbol = new Entry(currentSlot, name);
        table.put(name, symbol);
        currentSlot++;
        return symbol;
    }

    static final class Entry {

        private final int id;
        private final String name;

        public Entry(int id, String name) {
            this.id = id;
            this.name = name;
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
            return Objects.equals(name, other.name) && Objects.equals(id, other.id);
        }
    }
}

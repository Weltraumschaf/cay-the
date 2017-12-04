package de.weltraumschaf.caythe.experiments;

import de.weltraumschaf.commons.validate.Validate;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public final class Tuple {

    private final List<Entry> store = new ArrayList<>();

    public int size() {
        return store.size();
    }

    public boolean isEmpty() {
        return store.isEmpty();
    }

    public void put(final Object value) {
        Validate.notNull(value, "value");
        store.add(new Entry(value, value.getClass()));
    }

    public boolean hasType(final int index, final Class<?> type) {
        return false;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(final int index) {
        return (T) store.get(index);
    }

    private static class Entry {
        private final Object value;
        private final Class<?> type;

        Entry(final Object value, final Class<?> type) {
            super();
            this.value = Validate.notNull(value, "value");
            this.type = Validate.notNull(type, "type");
        }
    }
}

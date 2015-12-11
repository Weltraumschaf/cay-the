package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.commons.validate.Validate;
import java.util.Objects;

/**
 * Symbol entry.
 *
 * @since 1.0.0
 */
public final class SymbolEntry {

    /**
     * The slot ID of the entry.
     */
    private final int id;
    /**
     * The name of the ID.
     */
    private final String name;
    private final Type type;

    /**
     * Dedicated constructor.
     *
     * @param id must not be negative
     * @param name must not be {@code null} or empty
     * @param type must not be {@code null}
     */
    public SymbolEntry(int id, final String name, final Type type) {
        super();
        Validate.greaterThanOrEqual(id, 0, "id");
        this.id = id;
        this.name = Validate.notEmpty(name, "name");
        this.type = Validate.notNull(type, "type");
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

    public Type getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, type);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof SymbolEntry)) {
            return false;
        }

        final SymbolEntry other = (SymbolEntry) obj;
        return Objects.equals(name, other.name)
            && Objects.equals(id, other.id)
            && Objects.equals(type, other.type);
    }

    @Override
    public String toString() {
        return "SymbolEntry{" + "id=" + id + ", name=" + name + ", type=" + type + '}';
    }

    public static enum Type {

        VARIABLE, CONSTANT;
    }
}

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
    private final Map<String, SymbolEntry> table = new HashMap<>();
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
    public SymbolEntry lookup(final String name) {
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
     * @return type {@code null}
     */
    public SymbolEntry enter(final String name, SymbolEntry.Type type) {
        if (enered(name)) {
            throw new IllegalArgumentException(String.format("Symbal with name '%s' already entered in table!", name));
        }

        final SymbolEntry symbol = new SymbolEntry(currentSlot++, name, type);
        table.put(name, symbol);
        return symbol;
    }

}

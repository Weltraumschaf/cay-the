package de.weltraumschaf.caythe.backend.symtab;

/**
 * Describes the types built in in the language.
 *
 * @since 1.0.0
 */
public final class BuildInTypeSymbol extends BaseSymbol implements Type {

    /**
     * Dedicated constructor.
     *
     * @param name must not be {@code null} or empty
     */
    public BuildInTypeSymbol(final String name) {
        super(name);
    }
}

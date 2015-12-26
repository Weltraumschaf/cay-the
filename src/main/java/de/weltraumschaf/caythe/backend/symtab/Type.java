package de.weltraumschaf.caythe.backend.symtab;

/**
 * Describes a type.
 *
 * @since 1.0.0
 */
public interface Type {

    /**
     * Name of the type.
     *
     * @return never {@code null} or empty
     */
    public String getName();

}

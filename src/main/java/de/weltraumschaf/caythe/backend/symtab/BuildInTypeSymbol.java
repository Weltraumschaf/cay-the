package de.weltraumschaf.caythe.backend.symtab;

/**
 * Describes the types built in in the language.
 *
 * @since 1.0.0
 */
public final class BuildInTypeSymbol extends BaseSymbol implements Type {

    public static final Type NIL  = new BuildInTypeSymbol("Nil");
    public static final Type BOOL = new BuildInTypeSymbol("Bool");
    public static final Type INT = new BuildInTypeSymbol("Int");
    public static final Type FLOAT = new BuildInTypeSymbol("Float");
    public static final Type STRING = new BuildInTypeSymbol("String");
    public static final Type FUNCTION = new BuildInTypeSymbol("Function");

    /**
     * Dedicated constructor.
     *
     * @param name must not be {@code null} or empty
     */
    public BuildInTypeSymbol(final String name) {
        super(name, Type.NULL);
    }
}

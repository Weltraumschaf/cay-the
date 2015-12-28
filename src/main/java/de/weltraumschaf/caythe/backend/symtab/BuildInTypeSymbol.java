package de.weltraumschaf.caythe.backend.symtab;

/**
 * Describes the types built in in the language.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class BuildInTypeSymbol extends BaseSymbol implements Type {

    /**
     * Build in NIL type which represents nothing/not available.
     */
    public static final Type NIL  = new BuildInTypeSymbol("Nil");
    /**
     * Build in boolean type.
     */
    public static final Type BOOL = new BuildInTypeSymbol("Bool");
    /**
     * Build in integer type.
     */
    public static final Type INT = new BuildInTypeSymbol("Int");
    /**
     * Build in float type.
     */
    public static final Type FLOAT = new BuildInTypeSymbol("Float");
    /**
     * Build in string type.
     */
    public static final Type STRING = new BuildInTypeSymbol("String");
    /**
     * Build in function type (functions are first class citizens).
     */
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

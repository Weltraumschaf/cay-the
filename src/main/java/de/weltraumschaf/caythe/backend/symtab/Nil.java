package de.weltraumschaf.caythe.backend.symtab;

/**
 * Represents the value of type {@link BuildInTypeSymbol#NIL}.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class Nil {

    /**
     * There is only one of it.
     */
    public static final Nil INSTANCE = new Nil();

    /**
     * Dedicated constructor.
     * <p>
     * Use {@link #INSTANCE} instead.
     * </p>
     */
    private Nil() {
        super();
    }

}

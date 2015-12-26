package de.weltraumschaf.caythe.backend.symtab;

/**
 * This describes the global scope.
 * <p>
 * There should be only one global scope at runtime.
 * </p>
 * <p>
 * The global scope returns {@code null} for {@link #getEnclosingScope()}.
 * </p>
 *
 * @since 1.0.0
 */
public final class GlobalScope extends BaseScope {

    /**
     * Dedicated constructor.
     */
    public GlobalScope() {
        super(null);
    }

    @Override
    public String getScopeName() {
        return "global";
    }
}

package de.weltraumschaf.caythe.backend.symtab;

import de.weltraumschaf.commons.validate.Validate;

/**
 * Describes a local scope.
 * <p>
 * Local scopes always have an {@link #getEnclosingScope() parent scope}.
 * </p>
 *
 * @since 1.0.0
 */
public final class LocalScope extends BaseScope {

    /**
     * Dedicated constructor.
     *
     * @param parent must not be {@code null}
     */
    public LocalScope(final Scope parent) {
        super(Validate.notNull(parent, "parent"));
    }

    @Override
    public String getScopeName() {
        return "local";
    }
}

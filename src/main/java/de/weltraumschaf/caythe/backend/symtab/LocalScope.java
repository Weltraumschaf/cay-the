package de.weltraumschaf.caythe.symtab;

/**
 */
public final class LocalScope extends BaseScope {

    public LocalScope(Scope parent) {
        super(parent);
    }

    @Override
    public String getScopeName() {
        return "local";
    }
}

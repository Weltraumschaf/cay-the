package de.weltraumschaf.caythe.symtab;

/**
 */
public final class GlobalScope extends BaseScope {

    public GlobalScope() {
        super(null);
    }

    @Override
    public String getScopeName() {
        return "global";
    }
}

package de.weltraumschaf.caythe.symtab;

/**
 */
public class Symbol {

    String name;      // All symbols at least have a name
    Type type;
    Scope scope;      // All symbols know what scope contains them.

    public Symbol (String name) {
        this.name = name;
    }

    public Symbol (String name, Type type) {
        this(name);
        this.type = type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        if (type != null) {
            return '<' + getName() + ":" + type + '>';
        }

        return getName();
    }
}

package de.weltraumschaf.caythe.backend.env;

import de.weltraumschaf.caythe.backend.Pool;
import de.weltraumschaf.caythe.backend.symtab.Value;
import de.weltraumschaf.caythe.backend.symtab.VariableSymbol;

/**
 * The heap allocates the space for constants and variables.
 *
 * @since 1.0.0
 */
public final class Heap {

    private final Pool variables = new Pool();
    private final Pool consants = new Pool();

    public Value get(final VariableSymbol symbol) {
        return null;
    }
}

package de.weltraumschaf.caythe.backend.env;

/**
 * Common environment implementation.
 *
 * @since 1.0.0
 */
abstract class BaseEnvironment implements Environment {

    private final Heap heap = new Heap();

    @Override
    public final Heap heap() {
        return heap;
    }

}

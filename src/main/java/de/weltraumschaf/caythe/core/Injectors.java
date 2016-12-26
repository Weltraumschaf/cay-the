package de.weltraumschaf.caythe.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.weltraumschaf.commons.application.IO;

/**
 * Factory to create injectors to create instances with ready to use dependencies.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class Injectors {

    /**
     * Dependency injection configuration.
     */
    private final InterpreterModule module;

    /**
     * Dedicated constructor.
     *
     * @param ioStreams must not be {@code null}
     */
    public Injectors(final IO ioStreams) {
        super();
        this.module = new InterpreterModule(ioStreams);
    }

    /**
     * Creates a new injector.
     *
     * @return never {@code null}, always new instance
     */
    public Injector newInterpreterInjector() {
        return Guice.createInjector(module);
    }

}
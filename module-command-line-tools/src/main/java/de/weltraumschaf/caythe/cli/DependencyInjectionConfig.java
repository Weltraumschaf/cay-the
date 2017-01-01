package de.weltraumschaf.caythe.cli;

import com.google.inject.AbstractModule;
import de.weltraumschaf.commons.application.IO;
import de.weltraumschaf.commons.validate.Validate;

/**
 * Configures the dependency injection.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class DependencyInjectionConfig extends AbstractModule {

    private final IO ioStreams;

    public DependencyInjectionConfig(final IO ioStreams) {
        super();
        this.ioStreams = Validate.notNull(ioStreams, "ioStreams");
    }

    @Override
    protected void configure() {
        // General application dependencies.
        bind(IO.class).toInstance(ioStreams);
    }

}

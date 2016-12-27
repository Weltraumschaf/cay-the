package de.weltraumschaf.caythe.core;

import com.google.inject.AbstractModule;
import de.weltraumschaf.caythe.frontend.*;
import de.weltraumschaf.commons.application.IO;
import de.weltraumschaf.commons.validate.Validate;
import org.antlr.v4.runtime.ANTLRErrorListener;

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

        // ANTLR dependencies.
        bind(ANTLRErrorListener.class).to(ErrorListener.class);
        bind(CayTheManifestVisitor.class).to(DefaultCayTheManifestVisitor.class);
        bind(CayTheSourceVisitor.class).to(DefaultCayTheSourceVisitor.class);
    }

}

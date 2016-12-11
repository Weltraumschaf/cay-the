package de.weltraumschaf.caythe.core;

import com.google.inject.AbstractModule;
import de.weltraumschaf.caythe.frontend.*;
import de.weltraumschaf.commons.application.IO;
import de.weltraumschaf.commons.validate.Validate;

/**
 * Configures the dependency injection.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class InterpreterModule extends AbstractModule {

    private IO ioStreams;

    @Override
    protected void configure() {
        // General application dependencies.
        bind(IO.class).toInstance(ioStreams);

        // ANTLR dependencies.
        bind(Parsers.class);
        bind(CayTheManifestVisitor.class).to(CayTheManifestBaseVisitor.class);
        bind(CayTheSourceVisitor.class).to(CayTheSourceBaseVisitor.class);
    }

    public void setIoStreams(final IO ioStreams) {
        this.ioStreams = Validate.notNull(ioStreams, "ioStreams");
    }
}

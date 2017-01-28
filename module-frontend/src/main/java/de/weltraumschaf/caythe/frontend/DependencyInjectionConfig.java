package de.weltraumschaf.caythe.frontend;

import com.google.inject.AbstractModule;
import org.antlr.v4.runtime.ANTLRErrorListener;

/**
 * * Configures the dependency injection.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
final class DependencyInjectionConfig extends AbstractModule {

    @Override
    protected void configure() {
        bind(ANTLRErrorListener.class).to(ErrorListener.class);
        bind(de.weltraumschaf.caythe.backend.CayTheManifestVisitor.class).to(DefaultCayTheManifestVisitor.class);
    }
}

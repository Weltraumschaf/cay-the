package de.weltraumschaf.caythe.frontend;

import com.google.inject.AbstractModule;

/**
 * * Configures the dependency injection.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
final class DependencyInjectionConfig extends AbstractModule {

    @Override
    protected void configure() {
        bind(ErrorListener.class).to(DefaultErrorListener.class);
        bind(CayTheManifestVisitor.class).to(ManifestToIntermediateTransformer.class);
        bind(CayTheSourceVisitor.class).to(SourceToIntermediateTransformer.class);
    }
}

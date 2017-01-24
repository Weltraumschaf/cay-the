package de.weltraumschaf.caythe.frontend;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.weltraumschaf.caythe.frontend.experimental.types.ObjectType;
import org.junit.Test;

import java.io.IOException;
import java.util.Objects;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link DefaultCayTheSourceVisitor}.
 */
public class DefaultCayTheSourceVisitorTest {
    private final Injector injector = Guice.createInjector(new DependencyInjectionConfig());
    private final Parsers parsers = new Parsers(injector);
    @SuppressWarnings("unchecked")
    private final CayTheSourceVisitor<ObjectType> sut = injector.getInstance(DefaultCayTheSourceVisitor.class);

    @Test
    public void file() throws IOException {
        final CayTheSourceParser parser = parsers.newSourceParser(getClass().getResourceAsStream("/de/weltraumschaf/caythe/frontend/test.ct"));

        sut.visit(parser.unit());
    }
}

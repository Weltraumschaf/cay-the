package de.weltraumschaf.caythe.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.weltraumschaf.commons.application.IO;
import de.weltraumschaf.commons.application.IOStreams;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * Tests for {@link DependencyInjectionConfig}.
 */
public class DependencyInjectionConfigTest {

    private final DependencyInjectionConfig sut = new DependencyInjectionConfig(mock(IO.class));
    private final Injector injector = Guice.createInjector(sut);

}

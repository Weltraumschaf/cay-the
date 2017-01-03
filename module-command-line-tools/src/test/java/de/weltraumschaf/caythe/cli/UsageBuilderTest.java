package de.weltraumschaf.caythe.cli;

import com.beust.jcommander.Parameter;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Collection;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link UsageBuilder}.
 */
public class UsageBuilderTest {

    @Test
    public void generate_emptyByDefault() {
        assertThat(UsageBuilder.generate(Object.class), is(""));
    }

    @Test
    public void generate() {
        assertThat(
            UsageBuilder.generate(Example.class),
            is("-c|--baz [-f|--foo] [-b|--bar]"));
    }

    @Test
    public void findAllParameters() throws NoSuchFieldException {
        final Collection<Field> parameters = UsageBuilder.findAllParameters(Example.class);

        assertThat(parameters, containsInAnyOrder(
            Example.class.getDeclaredField("foo"),
            Example.class.getDeclaredField("bar"),
            Example.class.getDeclaredField("baz")
        ));
    }

    private static final class Example {
        @Parameter(names = {"-f", "--foo"}, description = "foo")
        private String foo;
        @Parameter(names = {"-b", "--bar"}, description = "bar")
        private String bar;
        @Parameter(names = {"-c", "--baz"}, description = "baz", required = true)
        private String baz;
        private String snafu;
    }
}

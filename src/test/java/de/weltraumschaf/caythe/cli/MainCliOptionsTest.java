package de.weltraumschaf.caythe.cli;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link MainCliOptions}.
 */
public class MainCliOptionsTest {

    @Test
    public void delimitedSubcommandNames() {
        assertThat(MainCliOptions.delimitedSubcommandNames(), is("create|compile"));
    }

    @Test
    public void usage() {
        assertThat(
            MainCliOptions.usage(),
            is("create|compile [--version] [-h|--help] [-d|--debug]"));
    }
}

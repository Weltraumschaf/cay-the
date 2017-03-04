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
        assertThat(MainCliOptions.delimitedSubcommandNames(), is("assemble|create|compile|run|repl"));
    }

    @Test
    public void usage() {
        assertThat(
            MainCliOptions.usage(),
            is("assemble|create|compile|run|repl [--version] [-h|--help] [--debug]"));
    }
}

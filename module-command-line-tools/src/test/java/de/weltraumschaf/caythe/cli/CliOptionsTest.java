package de.weltraumschaf.caythe.cli;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link CliOptions}.
 */
public class CliOptionsTest {
    private final CliOptions sut = new CliOptions();

    @Test
    public void usage() {
        assertThat(sut.usage(), is("assemble|create|compile|run|repl [--version] [-h|--help] [--debug]"));
    }

    @Test
    public void usage_withSubCommand() {
        assertThat(
            sut.usage(SubCommandName.CREATE),
            is("create -d|--directory [-g|--group] [-a|--artifact] [-n|--namespace] [-h|--help] [--debug]"));
    }
}

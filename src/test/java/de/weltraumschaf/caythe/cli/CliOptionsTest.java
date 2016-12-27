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
        assertThat(sut.usage(), is("create|compile [--version] [-h|--help] [-d|--debug]"));
    }

    @Test
    public void usage_withSubCommand() {
        assertThat(
            sut.usage(SubCommandName.CREATE),
            is("create -m|--module <module name> [-g|--group <group>] [-a|--artifact] <artifact> [-n|--namespace <namespace>] [-h|--help] [-d|--debug]"));
    }
}

package de.weltraumschaf.caythe.cli;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link CommonCliOptions}.
 */
public class CommonCliOptionsTest {

    @Test
    public void usage() {
        assertThat(CommonCliOptions.usage(), is("[-h|--help] [--debug]"));
    }
}

package de.weltraumschaf.caythe.cli;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link CreateCliOptions}.
 */
public class CreateCliOptionsTest {

    @Test
    public void usage() {
        assertThat(
            CreateCliOptions.usage(),
            is("create -d|--directory [-g|--group] [-a|--artifact] [-n|--namespace] [-h|--help] [--debug]"));
    }
}

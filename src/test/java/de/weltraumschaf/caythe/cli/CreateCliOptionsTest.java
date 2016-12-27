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
            is("create -m|--module <module name> [-g|--group <group>] [-a|--artifact] <artifact> [-n|--namespace <namespace>] [-h|--help] [-d|--debug]"));
    }
}

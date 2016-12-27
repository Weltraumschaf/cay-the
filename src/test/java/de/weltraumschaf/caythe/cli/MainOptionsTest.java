package de.weltraumschaf.caythe.cli;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link MainOptions}.
 */
public class MainOptionsTest {

    @Test
    public void delimitedSubcommandNames() {
        assertThat(MainOptions.delimitedSubcommandNames(), is("create|compile"));
    }

    @Test
    @Ignore
    public void usage() {

    }
}

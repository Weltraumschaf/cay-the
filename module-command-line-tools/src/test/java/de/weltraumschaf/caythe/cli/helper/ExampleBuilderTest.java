package de.weltraumschaf.caythe.cli.helper;

import de.weltraumschaf.caythe.cli.SubCommandName;
import de.weltraumschaf.caythe.cli.helper.ExampleBuilder;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link ExampleBuilder}.
 */
public class ExampleBuilderTest {

    @Test
    public void stringOfEmptyDefaultBuilder() {
        assertThat(ExampleBuilder.crete().toString(), is(""));
    }

    @Test
    public void stringWithText() {
        assertThat(
            ExampleBuilder.crete()
                .text("Lorem ipsum dolor.")
                .toString(),
            is("Lorem ipsum dolor."));
    }

    @Test
    public void stringWithTextAndNEwlines() {
        assertThat(
            ExampleBuilder.crete()
                .text("Lorem ipsum dolor.")
                .nl().nl()
                .text("foobar")
                .toString(),
            is("Lorem ipsum dolor.\n\nfoobar"));
    }

    @Test
    public void stringWithCommand() {
        assertThat(
            ExampleBuilder.crete()
                .command("foo bar bar")
                .toString(),
            is("  $> caythe foo bar bar"));
    }

    @Test
    public void stringWithSubCommand() {
        assertThat(
            ExampleBuilder.crete(SubCommandName.CREATE)
                .command("foo bar bar")
                .toString(),
            is("  $> caythe create foo bar bar"));
    }

    @Test
    public void stringAllTogether() {
        assertThat(
            ExampleBuilder.crete()
                .text("Lorem ipsum dolor.")
                .nl().nl()
                .command("foobar")
                .toString(),
            is("Lorem ipsum dolor.\n\n  $> caythe foobar"));
    }
}

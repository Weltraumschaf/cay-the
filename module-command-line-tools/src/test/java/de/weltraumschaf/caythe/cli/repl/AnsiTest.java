package de.weltraumschaf.caythe.cli.repl;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Tests for {@link Ansi}.
 *
 * @author Sven Strittmatter
 */
public class AnsiTest {

    private final Ansi sut = Ansi.fmt();

    @Test
    public void toString_emptyByDefault() {
        assertThat(sut.toString(), is(""));
    }

    @Test
    public void reset() {
        assertThat(sut.reset(), is(sameInstance(sut)));

        assertThat(sut.toString(), is("\u001B[0m"));
    }

    @Test
    public void bold() {
        assertThat(sut.bold(), is(sameInstance(sut)));

        assertThat(sut.toString(), is("\u001B[1m"));
    }

    @Test
    public void italic() {
        assertThat(sut.italic(), is(sameInstance(sut)));

        assertThat(sut.toString(), is("\u001B[3m"));
    }

    @Test
    public void fg() {
        assertThat(sut.fg(Ansi.Color.WHITE), is(sameInstance(sut)));

        assertThat(sut.toString(), is("\u001B[37m"));
    }

    @Test
    public void fgBright() {
        assertThat(sut.fgBright(Ansi.Color.YELLOW), is(sameInstance(sut)));

        assertThat(sut.toString(), is("\u001B[93m"));
    }

    @Test
    public void bg() {
        assertThat(sut.bg(Ansi.Color.CYAN), is(sameInstance(sut)));

        assertThat(sut.toString(), is("\u001B[46m"));
    }

    @Test
    public void bgBright() {
        assertThat(sut.bgBright(Ansi.Color.DEFAULT), is(sameInstance(sut)));

        assertThat(sut.toString(), is("\u001B[109m"));
    }

    @Test
    public void text() {
        assertThat(sut.text("foo"), is(sameInstance(sut)));

        assertThat(sut.toString(), is("foo"));
    }

    @Test
    public void nl() {
        assertThat(sut.nl(), is(sameInstance(sut)));

        assertThat(sut.toString(), is("\n"));
    }

    @Test
    public void complexExample() {
        sut.bold().text("foo").reset().nl()
            .fg(Ansi.Color.MAGENTA).text("bar").reset();

        assertThat(sut.toString(), is("\u001B[1mfoo\u001B[0m\n\u001B[35mbar\u001B[0m"));
    }
}

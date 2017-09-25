package de.weltraumschaf.caythe.cli.create;

import de.weltraumschaf.caythe.cli.CliContext;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link CreateSubCommand}.
 */
public final class CreateSubCommandTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    private final CreateSubCommand sut = new CreateSubCommand(mock(CliContext.class));

    @Test
    public void validateIdentifier_mustNotBeNull() {
        thrown.expect(IllegalArgumentException.class);

        sut.validateIdentifier(null);
    }

    @Test
    public void validateIdentifier_mustNotBeEmpty() {
        thrown.expect(IllegalArgumentException.class);

        sut.validateIdentifier("");
    }

    @Test
    public void validateIdentifier_mustNotBeBlank() {
        thrown.expect(IllegalArgumentException.class);

        sut.validateIdentifier("   ");
    }

    @Test
    public void validateIdentifier_multipleDotsInSequenceNotAllowed() {
        thrown.expect(IllegalArgumentException.class);

        sut.validateIdentifier("foo..bar");
    }

    @Test
    public void validateIdentifier_plusNotAllowed() {
        thrown.expect(IllegalArgumentException.class);

        sut.validateIdentifier("foo+bar");
    }

    @Test
    public void validateIdentifier_hashNotAllowed() {
        thrown.expect(IllegalArgumentException.class);

        sut.validateIdentifier("foo#bar");
    }

    @Test
    public void validateIdentifier_commaNotAllowed() {
        thrown.expect(IllegalArgumentException.class);

        sut.validateIdentifier("foo,bar");
    }

    @Test
    public void validateIdentifier_semicolonNotAllowed() {
        thrown.expect(IllegalArgumentException.class);

        sut.validateIdentifier("foo;bar");
    }

    @Test
    public void validateIdentifier_colonNotAllowed() {
        thrown.expect(IllegalArgumentException.class);

        sut.validateIdentifier("foo:bar");
    }

    @Test
    public void validateIdentifier_questionMarkNotAllowed() {
        thrown.expect(IllegalArgumentException.class);

        sut.validateIdentifier("foo?bar");
    }

    @Test
    public void validateIdentifier_exclamationMarkNotAllowed() {
        thrown.expect(IllegalArgumentException.class);

        sut.validateIdentifier("foo!bar");
    }

    @Test
    public void validateIdentifier_slashNotAllowed() {
        thrown.expect(IllegalArgumentException.class);

        sut.validateIdentifier("foo/bar");
    }

    @Test
    public void validateIdentifier_backslashNotAllowed() {
        thrown.expect(IllegalArgumentException.class);

        sut.validateIdentifier("foo\\bar");
    }

    @Test
    public void validateIdentifier() {
        assertThat(sut.validateIdentifier("foo"), is("foo"));
        assertThat(sut.validateIdentifier("foo.bar"), is("foo.bar"));
        assertThat(sut.validateIdentifier("foo.bar.baz"), is("foo.bar.baz"));
        assertThat(sut.validateIdentifier("snafu42"), is("snafu42"));
        assertThat(sut.validateIdentifier("snafu42.foo"), is("snafu42.foo"));
        assertThat(sut.validateIdentifier("foo-bar"), is("foo-bar"));
        assertThat(sut.validateIdentifier("foo-bar.baz"), is("foo-bar.baz"));
        assertThat(sut.validateIdentifier("foo-bar.baz_snafu"), is("foo-bar.baz_snafu"));
        assertThat(sut.validateIdentifier("42"), is("42"));
        assertThat(sut.validateIdentifier("42foo"), is("42foo"));
        assertThat(sut.validateIdentifier("42.foo"), is("42.foo"));
        assertThat(sut.validateIdentifier("42.foo.bar23"), is("42.foo.bar23"));
        assertThat(sut.validateIdentifier("42.foo.bar.23"), is("42.foo.bar.23"));
    }
}

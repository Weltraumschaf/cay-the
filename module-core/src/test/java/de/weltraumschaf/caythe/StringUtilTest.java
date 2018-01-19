package de.weltraumschaf.caythe;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link StringUtil}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public class StringUtilTest {

    @Test
    public void upperCaseFirst_nullGiven() {
        assertThat(StringUtil.upperCaseFirst(null), is(""));
    }

    @Test
    public void upperCaseFirst_emptyGiven() {
        assertThat(StringUtil.upperCaseFirst(""), is(""));
    }

    @Test
    public void upperCaseFirst_blankGiven() {
        assertThat(StringUtil.upperCaseFirst("  "), is(""));
    }

    @Test
    public void upperCaseFirst_firstCharacterAlreadyUpperCase() {
        assertThat(StringUtil.upperCaseFirst("Foobar"), is("Foobar"));
    }

    @Test
    public void upperCaseFirst_firstCharacterLowerCase() {
        assertThat(StringUtil.upperCaseFirst("foobar"), is("Foobar"));
    }

    @Test
    public void shorten_nullGiven() {
        assertThat(StringUtil.shorten(null), is(""));
    }

    @Test
    public void shorten_emptyGiven() {
        assertThat(StringUtil.shorten(""), is(""));
    }

    @Test
    public void shorten_blankGiven() {
        assertThat(StringUtil.shorten("  "), is(""));
    }

    @Test
    public void shorten_smallerThanTwentyChars() {
        assertThat(StringUtil.shorten("/foo/bar/baz.ct"), is("/foo/bar/baz.ct"));
    }

    @Test
    public void shorten_longerThanTwentyChars() {
        final String input = "/de/weltraumschaf/caythe/frontend/transform/property_decl/OnePropertyWithCustomGetterNoSetter.ct";

        assertThat(StringUtil.shorten(input).length(), is(StringUtil.MAX_LENGTH_TO_SHORTEN));
        assertThat(StringUtil.shorten(input), is("/de/wel...operty_decl/OnePropertyWithCustomGetterNoSetter.ct"));
    }
}
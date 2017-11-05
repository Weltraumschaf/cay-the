package de.weltraumschaf.caythe;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link StringUtil}.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
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
}
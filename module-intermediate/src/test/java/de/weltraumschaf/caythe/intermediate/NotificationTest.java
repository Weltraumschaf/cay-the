package de.weltraumschaf.caythe.intermediate;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link Notification}.
 */
public class NotificationTest {
    private final Notification sut = new Notification();

    @Test
    public void isOk_trueByDefault() {
        assertThat(sut.isOk(), is(true));
    }

    @Test
    public void isOk_falseIfErrorReported() {
        sut.error("error");

        assertThat(sut.isOk(), is(false));
    }

    @Test
    public void report_emptyByDefault() {
        assertThat(sut.report(), is(""));
    }

    @Test
    public void errorCollectingAndReporting() {
        sut.error("An error!");
        sut.error("Some %s occurred at %s!", "FOO", "BAR");
        sut.error("Error: %s at line %d occurred because %s!", "SNAFU", 5, "FOOBAR");

        assertThat(sut.report(), is("An error!\n"
            + "Some FOO occurred at BAR!\n"
            + "Error: SNAFU at line 5 occurred because FOOBAR!"));
    }
}
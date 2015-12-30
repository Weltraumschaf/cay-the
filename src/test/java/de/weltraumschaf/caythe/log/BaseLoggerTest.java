
package de.weltraumschaf.caythe.log;

import static org.hamcrest.Matchers.is;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for {link BaseLogger}.
 */
public class BaseLoggerTest {

    private final BaseLogger sut = new BaseLogger() {};

    @Test
    public void format() {
        assertThat(sut.format("Foo %s baz %d.", "bar", 42), is("Foo bar baz 42."));
    }

}
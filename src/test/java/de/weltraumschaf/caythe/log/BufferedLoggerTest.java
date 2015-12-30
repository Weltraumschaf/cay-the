
package de.weltraumschaf.caythe.log;

import de.weltraumschaf.caythe.CayThe;
import static org.hamcrest.Matchers.is;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for {@link BufferedLogger}.
 */
public class BufferedLoggerTest {

    private final BufferedLogger sut =new BufferedLogger();

    @Test
    public void log() {
        sut.log("foobar %d", 23);

        assertThat(sut.toString(), is("foobar 23" + CayThe.DEFAULT_NEWLINE));
    }

}
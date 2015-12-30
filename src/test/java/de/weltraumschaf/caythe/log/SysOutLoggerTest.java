
package de.weltraumschaf.caythe.log;

import java.io.PrintStream;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link SysOutLogger}.
 */
public class SysOutLoggerTest {

    private final PrintStream out = mock(PrintStream.class);
    private final SysOutLogger sut = new SysOutLogger(out);

    @Test
    public void log() {
        sut.log("foobar %d", 23);

        verify(out, times(1)).println("[cay-the] foobar 23");
    }

}
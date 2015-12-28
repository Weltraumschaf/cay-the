package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.CayThe;
import de.weltraumschaf.caythe.backend.env.Environment;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link KernelApi}.
 */
public class KernelApiTest {

    private final Environment io = mock(Environment.class);
    private final KernelApi sut = new KernelApi(io);

    @Test
    public void print() {
        sut.print("foobar");

        verify(io, times(1)).stdOut("foobar");
    }

    @Test
    public void println() {
        sut.println("foobar");

        verify(io, times(1)).stdOut("foobar" + CayThe.DEFAULT_NEWLINE);
    }

    @Test
    public void error() {
        sut.error("foobar");

        verify(io, times(1)).stdErr("foobar");
    }

    @Test
    public void errorln() {
        sut.errorln("foobar");

        verify(io, times(1)).stdErr("foobar" + CayThe.DEFAULT_NEWLINE);
    }

    @Test
    public void exit() {
    }

}

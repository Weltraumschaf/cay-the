
package de.weltraumschaf.caythe.backend.env;

import de.weltraumschaf.commons.application.IO;
import org.junit.Test;
import org.junit.Ignore;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link DefaultEnvironmnet}.
 */
public class DefaultEnvironmnetTest {

    private final IO io = mock(IO.class);
    private final DefaultEnvironmnet sut = new DefaultEnvironmnet(io);

    @Test
    public void stdOut() {
        sut.stdOut("foobar");

        verify(io, times(1)).print("foobar");
    }
    @Test
    public void stdErr() {
        sut.stdErr("foobar");

        verify(io, times(1)).error("foobar");
    }

    @Test
    @Ignore("Not implemented yet.")
    public void stdIn() {
    }

}
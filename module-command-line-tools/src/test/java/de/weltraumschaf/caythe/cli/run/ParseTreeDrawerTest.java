package de.weltraumschaf.caythe.cli.run;

import de.weltraumschaf.commons.application.IO;
import org.junit.Test;

import java.nio.file.Paths;

import static org.mockito.Mockito.mock;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link ParseTreeDrawer}.
 */
public final class ParseTreeDrawerTest {
    private final ParseTreeDrawer sut = new ParseTreeDrawer(mock(IO.class));

    @Test
    public void convertFileName() {
        assertThat(sut.convertFileName(Paths.get("Module.mf")), is(Paths.get("Module.ps")));
        assertThat(sut.convertFileName(Paths.get("foo/bar/Module.mf")), is(Paths.get("Module.ps")));
        assertThat(sut.convertFileName(Paths.get("FooBar.ct")), is(Paths.get("FooBar.ps")));
        assertThat(sut.convertFileName(Paths.get("foo/bar/FooBar.ct")), is(Paths.get("FooBar.ps")));
    }
}

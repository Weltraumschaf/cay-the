package de.weltraumschaf.caythe.backend.experimental;

import de.weltraumschaf.caythe.backend.experimental.types.NullType;
import de.weltraumschaf.caythe.backend.experimental.types.StringType;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Tests for {@link EnvironmentTest}.
 */
public class EnvironmentTest {
    private final Environment sut = new Environment();

    @Test
    public void get_ifNothingSet() {
        assertThat(sut.get("foo"), is(NullType.NULL));
    }

    @Test
    public void setVar() {
        assertThat(sut.get("foo"), is(NullType.NULL));

        sut.setVar("foo", new StringType("foobar"));

        assertThat(sut.get("foo"), is(new StringType("foobar")));
    }

    @Test
    public void setVar_alreadySetAsVar() {
        sut.setVar("foo", new StringType("foobar"));
        sut.setVar("foo", new StringType("snafu"));

        assertThat(sut.get("foo"), is(new StringType("snafu")));
    }

    @Test(expected = RuntimeException.class)
    public void setVar_alreadySetAsCost() {
        sut.setConst("foo", new StringType("foobar"));
        sut.setVar("foo", new StringType("snafu"));
    }

    @Test
    public void setConst() {
        assertThat(sut.get("foo"), is(NullType.NULL));

        sut.setConst("foo", new StringType("foobar"));

        assertThat(sut.get("foo"), is(new StringType("foobar")));
    }

    @Test(expected = RuntimeException.class)
    public void setConst_alreadySet() {
        sut.setConst("foo", new StringType("foobar"));
        sut.setConst("foo", new StringType("snafu"));
    }
}
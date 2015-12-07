package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.backend.Register;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Tests for {@link Register}.
 */
public class RegisterTest {

    private final Register sut = new Register();

    @Test(expected = IllegalArgumentException.class)
    public void set_addresMustNotBeNegative() {
        sut.set((byte) -1, 0);
    }

    @Test
    public void set() {
        sut.set((byte) 23, 42);

        assertThat(sut.get((byte) 23), is(42));
    }

    @Test(expected = IllegalArgumentException.class)
    public void get_addresMustNotBeNegative() {
        sut.get((byte) -1);
    }

    @Test
    public void get_zeroByDefault() {
        assertThat(sut.get((byte) 23), is(0));
    }
}

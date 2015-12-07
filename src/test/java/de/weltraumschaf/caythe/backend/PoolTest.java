package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.backend.Pool.Value;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Tests for {@link Pool}.
 */
public final class PoolTest {

    private final Pool sut = new Pool();

    @Test
    public void get_notExistingValue() {
        assertThat(sut.get(23), is(Value.NIL));
    }

    @Test
    public void get_existingValue() {
        sut.set(3, Value.TRUE);

        assertThat(sut.get(3), is(Value.TRUE));
    }

}

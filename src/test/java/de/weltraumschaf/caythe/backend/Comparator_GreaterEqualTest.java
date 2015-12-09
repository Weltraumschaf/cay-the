package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.backend.Pool.Value;
import java.util.Arrays;
import java.util.Collection;
import static org.hamcrest.Matchers.is;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for
 * {@link Comparator#greaterEqual(de.weltraumschaf.caythe.backend.Pool.Value, de.weltraumschaf.caythe.backend.Pool.Value)}.
 */
@RunWith(Parameterized.class)
public class Comparator_GreaterEqualTest {

    private final Comparator sut = new Comparator();
    private final Value left;
    private final Value right;
    private final Value expected;

    public Comparator_GreaterEqualTest(final Value left, final Value right, final Value expected) {
        super();
        this.left = left;
        this.right = right;
        this.expected = expected;
    }

    @Parameters(name = "{index}: greaterEqual({0}, {1}) = {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            // left, right, expected
            // NIL:
            {Value.NIL, Value.NIL, Value.TRUE},
        });
    }

    @Test
    public void greaterEqual() {
        assertThat(sut.greaterEqual(left, right), is(expected));
    }

}

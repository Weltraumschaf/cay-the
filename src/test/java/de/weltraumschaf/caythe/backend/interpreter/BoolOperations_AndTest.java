package de.weltraumschaf.caythe.backend.interpreter;

import de.weltraumschaf.caythe.backend.Value;
import de.weltraumschaf.caythe.backend.interpreter.BoolOperations;
import java.util.Arrays;
import java.util.Collection;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Tests for {@link BoolOperations#and(de.weltraumschaf.caythe.backend.Pool.Value, de.weltraumschaf.caythe.backend.Pool.Value)}.
 */
@RunWith(Parameterized.class)
public class BoolOperations_AndTest {

    private final BoolOperations sut = new BoolOperations();
    private final Value left;
    private final Value right;
    private final Value expected;

    public BoolOperations_AndTest(final Value left, final Value right, final Value expected) {
        super();
        this.left = left;
        this.right = right;
        this.expected = expected;
    }

    @Parameterized.Parameters(name = "{index}: and({0}, {1}) = {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            // left, right, expected
            {Value.NIL, Value.NIL, Value.FALSE},
            {Value.NIL, Value.TRUE, Value.FALSE},
            {Value.TRUE, Value.NIL, Value.FALSE},
            {Value.NIL, Value.FALSE, Value.FALSE},
            {Value.FALSE, Value.NIL, Value.FALSE},
            {Value.TRUE, Value.TRUE, Value.TRUE},
            {Value.TRUE, Value.FALSE, Value.FALSE},
            {Value.FALSE, Value.TRUE, Value.FALSE},
            {Value.FALSE, Value.FALSE, Value.FALSE},
            {Value.newInt(0), Value.newInt(0), Value.FALSE},
            {Value.newInt(42), Value.newInt(0), Value.FALSE},
            {Value.newInt(0), Value.newInt(42), Value.FALSE},
            {Value.newInt(42), Value.newInt(42), Value.TRUE},
            {Value.newFloat(0.0f), Value.newFloat(0.0f), Value.FALSE},
            {Value.newFloat(23.3f), Value.newFloat(0.0f), Value.FALSE},
            {Value.newFloat(0.0f), Value.newFloat(23.3f), Value.FALSE},
            {Value.newFloat(23.3f), Value.newFloat(23.3f), Value.TRUE},
            {Value.newString("foo"), Value.newString("bar"), Value.FALSE},
            {Value.newString("true"), Value.newString("bar"), Value.FALSE},
            {Value.newString("foo"), Value.newString("true"), Value.FALSE},
            {Value.newString("true"), Value.newString("true"), Value.TRUE},
            {Value.newString("foo"), Value.TRUE, Value.FALSE},
            {Value.newString("true"), Value.FALSE, Value.FALSE},
            {Value.newString(""), Value.FALSE, Value.FALSE},
            {Value.newString("true"), Value.TRUE, Value.TRUE},
        });
    }
    @Test
    public void and() {
        assertThat(sut.and(left, right), is(expected));
    }

}

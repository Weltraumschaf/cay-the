package de.weltraumschaf.caythe.backend;

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
 * {@link Comparator#notEqual(de.weltraumschaf.caythe.backend.Pool.Value, de.weltraumschaf.caythe.backend.Pool.Value)}.
 */
@RunWith(Parameterized.class)
public class Comparator_NotEqualTest {

    private final Comparator sut = new Comparator();
    private final Value left;
    private final Value right;
    private final Value expected;

    public Comparator_NotEqualTest(final Value left, final Value right, final Value expected) {
        super();
        this.left = left;
        this.right = right;
        this.expected = expected;
    }

    @Parameters(name = "{index}: equal({0}, {1}) = {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            // left, right, expected
            // NIL:
            {Value.NIL, Value.NIL, Value.FALSE},
            {Value.NIL, Value.FALSE, Value.FALSE},
            {Value.NIL, Value.TRUE, Value.TRUE},
            {Value.NIL, Value.newInt(0), Value.FALSE},
            {Value.NIL, Value.newInt(1), Value.TRUE},
            {Value.NIL, Value.newFloat(0.0f), Value.FALSE},
            {Value.NIL, Value.newFloat(1.0f), Value.TRUE},
            // Bool:
            {Value.TRUE, Value.TRUE, Value.FALSE},
            {Value.TRUE, Value.newInt(12), Value.FALSE},
            {Value.TRUE, Value.newFloat(12.3f), Value.FALSE},
            {Value.TRUE, Value.newString("true"), Value.FALSE},
            {Value.TRUE, Value.NIL, Value.TRUE},
            {Value.TRUE, Value.FALSE, Value.TRUE},
            {Value.TRUE, Value.newInt(0), Value.TRUE},
            {Value.TRUE, Value.newFloat(0.0f), Value.TRUE},
            {Value.TRUE, Value.newString("false"), Value.TRUE},
            {Value.TRUE, Value.newString("foo"), Value.TRUE},
            {Value.TRUE, Value.newString(""), Value.TRUE},
            {Value.FALSE, Value.NIL, Value.FALSE},
            {Value.FALSE, Value.newInt(0), Value.FALSE},
            {Value.FALSE, Value.newFloat(0.0f), Value.FALSE},
            {Value.FALSE, Value.newString("false"), Value.FALSE},
            {Value.FALSE, Value.newString("foo"), Value.FALSE},
            {Value.FALSE, Value.newString(""), Value.FALSE},
            {Value.FALSE, Value.TRUE, Value.TRUE},
            {Value.FALSE, Value.newInt(-3), Value.TRUE},
            {Value.FALSE, Value.newFloat(12.3f), Value.TRUE},
            {Value.FALSE, Value.newString("true"), Value.TRUE},
            // Int:
            {Value.newInt(42), Value.newInt(42), Value.FALSE},
            {Value.newInt(42), Value.newInt(23), Value.TRUE},
            {Value.newInt(23), Value.newInt(42), Value.TRUE},
            {Value.newInt(23), Value.TRUE, Value.TRUE},
            {Value.newInt(23), Value.FALSE, Value.TRUE},
            {Value.newInt(0), Value.FALSE, Value.FALSE},
            {Value.newInt(0), Value.NIL, Value.FALSE},
            {Value.newInt(1), Value.NIL, Value.TRUE},
            {Value.newInt(23), Value.newFloat(23.0f), Value.FALSE},
            {Value.newInt(23), Value.newFloat(23.3f), Value.FALSE},
            {Value.newInt(23), Value.newFloat(42.2f), Value.TRUE},
            {Value.newInt(42), Value.newString("42"), Value.FALSE},
            {Value.newInt(42), Value.newString("43"), Value.TRUE},
            {Value.newInt(42), Value.newString(""), Value.TRUE},
            {Value.newInt(42), Value.newString("foo"), Value.TRUE},
            // Float
            {Value.newFloat(42.2f), Value.newFloat(42.2f), Value.FALSE},
            {Value.newFloat(42.2f), Value.newFloat(23.3f), Value.TRUE},
            {Value.newFloat(23.3f), Value.newFloat(42.2f), Value.TRUE},
            {Value.newFloat(23.3f), Value.TRUE, Value.TRUE},
            {Value.newFloat(23.3f), Value.FALSE, Value.TRUE},
            {Value.newFloat(0.0f), Value.FALSE, Value.FALSE},
            {Value.newFloat(0.0f), Value.NIL, Value.FALSE},
            {Value.newFloat(1.0f), Value.NIL, Value.TRUE},
            {Value.newFloat(23.0f), Value.newInt(23), Value.FALSE},
            {Value.newFloat(23.3f), Value.newInt(23), Value.TRUE},
            {Value.newFloat(23.3f), Value.newInt(42), Value.TRUE},
            {Value.newFloat(42.2f), Value.newString("42.2"), Value.FALSE},
            {Value.newFloat(42.2f), Value.newString("43"), Value.TRUE},
            {Value.newFloat(42.2f), Value.newString(""), Value.TRUE},
            {Value.newFloat(42.2f), Value.newString("foo"), Value.TRUE},
            // String:
            {Value.newString("hello"), Value.newString("hello"), Value.FALSE},
            {Value.newString("hello"), Value.newString("world"), Value.TRUE},
            {Value.newString("world"), Value.newString("hello"), Value.TRUE},
        });
    }

    @Test
    public void notEqual() {
        assertThat(sut.notEqual(left, right), is(expected));
    }

}

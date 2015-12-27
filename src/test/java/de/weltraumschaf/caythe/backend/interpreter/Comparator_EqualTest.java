package de.weltraumschaf.caythe.backend.interpreter;

import de.weltraumschaf.caythe.backend.symtab.Value;
import de.weltraumschaf.caythe.backend.interpreter.Comparator;
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
 * {@link Comparator#equal(de.weltraumschaf.caythe.backend.Pool.Value, de.weltraumschaf.caythe.backend.Pool.Value)}.
 */
@RunWith(Parameterized.class)
public class Comparator_EqualTest {

    private final Comparator sut = new Comparator();
    private final Value left;
    private final Value right;
    private final Value expected;

    public Comparator_EqualTest(final Value left, final Value right, final Value expected) {
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
            {Value.NIL, Value.NIL, Value.TRUE},
            {Value.NIL, Value.FALSE, Value.TRUE},
            {Value.NIL, Value.TRUE, Value.FALSE},
            {Value.NIL, Value.newInt(0), Value.TRUE},
            {Value.NIL, Value.newInt(1), Value.FALSE},
            {Value.NIL, Value.newFloat(0.0f), Value.TRUE},
            {Value.NIL, Value.newFloat(1.0f), Value.FALSE},
            // Bool:
            {Value.TRUE, Value.TRUE, Value.TRUE},
            {Value.TRUE, Value.newInt(12), Value.TRUE},
            {Value.TRUE, Value.newFloat(12.3f), Value.TRUE},
            {Value.TRUE, Value.newString("true"), Value.TRUE},
            {Value.TRUE, Value.NIL, Value.FALSE},
            {Value.TRUE, Value.FALSE, Value.FALSE},
            {Value.TRUE, Value.newInt(0), Value.FALSE},
            {Value.TRUE, Value.newFloat(0.0f), Value.FALSE},
            {Value.TRUE, Value.newString("false"), Value.FALSE},
            {Value.TRUE, Value.newString("foo"), Value.FALSE},
            {Value.TRUE, Value.newString(""), Value.FALSE},
            {Value.FALSE, Value.NIL, Value.TRUE},
            {Value.FALSE, Value.newInt(0), Value.TRUE},
            {Value.FALSE, Value.newFloat(0.0f), Value.TRUE},
            {Value.FALSE, Value.newString("false"), Value.TRUE},
            {Value.FALSE, Value.newString("foo"), Value.TRUE},
            {Value.FALSE, Value.newString(""), Value.TRUE},
            {Value.FALSE, Value.TRUE, Value.FALSE},
            {Value.FALSE, Value.newInt(-3), Value.FALSE},
            {Value.FALSE, Value.newFloat(12.3f), Value.FALSE},
            {Value.FALSE, Value.newString("true"), Value.FALSE},
            // Int:
            {Value.newInt(42), Value.newInt(42), Value.TRUE},
            {Value.newInt(42), Value.newInt(23), Value.FALSE},
            {Value.newInt(23), Value.newInt(42), Value.FALSE},
            {Value.newInt(23), Value.TRUE, Value.FALSE},
            {Value.newInt(23), Value.FALSE, Value.FALSE},
            {Value.newInt(0), Value.FALSE, Value.TRUE},
            {Value.newInt(0), Value.NIL, Value.TRUE},
            {Value.newInt(1), Value.NIL, Value.FALSE},
            {Value.newInt(23), Value.newFloat(23.0f), Value.TRUE},
            {Value.newInt(23), Value.newFloat(23.3f), Value.TRUE},
            {Value.newInt(23), Value.newFloat(42.2f), Value.FALSE},
            {Value.newInt(42), Value.newString("42"), Value.TRUE},
            {Value.newInt(42), Value.newString("43"), Value.FALSE},
            {Value.newInt(42), Value.newString(""), Value.FALSE},
            {Value.newInt(42), Value.newString("foo"), Value.FALSE},
            // Float
            {Value.newFloat(42.2f), Value.newFloat(42.2f), Value.TRUE},
            {Value.newFloat(42.2f), Value.newFloat(23.3f), Value.FALSE},
            {Value.newFloat(23.3f), Value.newFloat(42.2f), Value.FALSE},
            {Value.newFloat(23.3f), Value.TRUE, Value.FALSE},
            {Value.newFloat(23.3f), Value.FALSE, Value.FALSE},
            {Value.newFloat(0.0f), Value.FALSE, Value.TRUE},
            {Value.newFloat(0.0f), Value.NIL, Value.TRUE},
            {Value.newFloat(1.0f), Value.NIL, Value.FALSE},
            {Value.newFloat(23.0f), Value.newInt(23), Value.TRUE},
            {Value.newFloat(23.3f), Value.newInt(23), Value.FALSE},
            {Value.newFloat(23.3f), Value.newInt(42), Value.FALSE},
            {Value.newFloat(42.2f), Value.newString("42.2"), Value.TRUE},
            {Value.newFloat(42.2f), Value.newString("43"), Value.FALSE},
            {Value.newFloat(42.2f), Value.newString(""), Value.FALSE},
            {Value.newFloat(42.2f), Value.newString("foo"), Value.FALSE},
            // String:
            {Value.newString("hello"), Value.newString("hello"), Value.TRUE},
            {Value.newString("hello"), Value.newString("world"), Value.FALSE},
            {Value.newString("world"), Value.newString("hello"), Value.FALSE},
        });
    }

    @Test
    public void equal() {
        assertThat(sut.equal(left, right), is(expected));
    }

}

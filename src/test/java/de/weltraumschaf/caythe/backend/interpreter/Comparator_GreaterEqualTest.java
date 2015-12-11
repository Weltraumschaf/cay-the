package de.weltraumschaf.caythe.backend.interpreter;

import de.weltraumschaf.caythe.backend.Value;
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
            {Value.NIL, Value.NIL, Value.TRUE},
            {Value.TRUE, Value.FALSE, Value.TRUE},
            {Value.TRUE, Value.TRUE, Value.TRUE},
            {Value.FALSE, Value.TRUE, Value.FALSE},
            {Value.newInt(23), Value.newInt(3), Value.TRUE},
            {Value.newInt(-2), Value.newInt(3), Value.FALSE},
            {Value.newInt(3), Value.newInt(3), Value.TRUE},
            {Value.newFloat(3.14f), Value.newFloat(-3.14f), Value.TRUE},
            {Value.newFloat(3.14f), Value.newFloat(23.0f), Value.FALSE},
            {Value.newFloat(3.14f), Value.newFloat(3.14f), Value.TRUE},
            {Value.newString("bbb"), Value.newString("aaa"), Value.TRUE},
            {Value.newString("b"), Value.newString("aaa"), Value.TRUE},
            {Value.newString("aa"), Value.newString("aaa"), Value.FALSE},
            {Value.TRUE, Value.newInt(0), Value.TRUE},
            {Value.TRUE, Value.newInt(13), Value.TRUE},
            {Value.newInt(13), Value.TRUE, Value.TRUE},
        });
    }

    @Test
    public void greaterEqual() {
        assertThat(sut.greaterEqual(left, right), is(expected));
    }

}


package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.backend.Pool.Value;
import java.util.Arrays;
import java.util.Collection;
import static org.hamcrest.Matchers.is;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Tests for {@link BoolOperations#not(de.weltraumschaf.caythe.backend.Pool.Value)}.
 *
 */
@RunWith(Parameterized.class)
public class BoolOperations_NotTest {

    private final BoolOperations sut = new BoolOperations();
    private final Pool.Value input;
    private final Pool.Value expected;

    public BoolOperations_NotTest(final Pool.Value input,  final Pool.Value expected) {
        super();
        this.input = input;
        this.expected = expected;
    }

    @Parameterized.Parameters(name = "{index}: not({0}, {1}) = {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            // input, expected
            {Value.NIL, Value.TRUE},
            {Value.TRUE, Value.FALSE},
            {Value.FALSE, Value.TRUE},
            {Value.newInt(42), Value.FALSE},
            {Value.newInt(0), Value.TRUE},
            {Value.newFloat(3.14f), Value.FALSE},
            {Value.newFloat(0.0f), Value.TRUE},
            {Value.newString(""), Value.TRUE},
            {Value.newString("foo"), Value.TRUE},
            {Value.newString("false"), Value.TRUE},
            {Value.newString("true"), Value.FALSE},
        });
    }

    @Test
    public void not() {
        assertThat(sut.not(input), is(expected));
    }

}
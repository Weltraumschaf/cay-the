
package de.weltraumschaf.caythe.backend.interpreter;

import de.weltraumschaf.caythe.backend.symtab.Value;
import static javax.management.Query.value;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for {@link ReturnValues}.
 */
public class ReturnValuesTest {

    @Test
    public void nothing() {
        final ReturnValues sut = ReturnValues.NOTHING;

        assertThat(sut.isNothing(), is(true));
        assertThat(sut.isSingleValue(), is(false));
        assertThat(sut.isMultiValue(), is(false));
        assertThat(sut.getFirst(), is(Value.NIL));
        assertThat(sut.get(0), is(Value.NIL));
        assertThat(sut.get(1), is(Value.NIL));
        assertThat(sut.get(), hasSize(0));
    }

    @Test
    public void single() {
        final Value value = Value.newInt(42);
        final ReturnValues sut = new ReturnValues(value);

        assertThat(sut.isNothing(), is(false));
        assertThat(sut.isSingleValue(), is(true));
        assertThat(sut.isMultiValue(), is(false));
        assertThat(sut.getFirst(), is(value));
        assertThat(sut.get(0), is(value));
        assertThat(sut.get(1), is(Value.NIL));
        assertThat(sut.get(), hasSize(1));
        assertThat(sut.get(), contains(value));
    }

    @Test
    public void multi() {
        final Value valueOne = Value.newInt(42);
        final Value valueTwo = Value.newBool(true);
        final ReturnValues sut = new ReturnValues(valueOne, valueTwo);

        assertThat(sut.isNothing(), is(false));
        assertThat(sut.isSingleValue(), is(false));
        assertThat(sut.isMultiValue(), is(true));
        assertThat(sut.getFirst(), is(valueOne));
        assertThat(sut.get(0), is(valueOne));
        assertThat(sut.get(1), is(valueTwo));
        assertThat(sut.get(), hasSize(2));
        assertThat(sut.get(), contains(valueOne, valueTwo));
    }

}
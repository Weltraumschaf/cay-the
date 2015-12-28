
package de.weltraumschaf.caythe.backend.symtab;

import static org.hamcrest.Matchers.is;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for {@link Symbol}.
 */
public class SymbolTest {

    @Test
    public void defaultsOfNullObject() {
        final Symbol sut = Symbol.NULL;

        assertThat(sut.getName(), is("nil"));
        assertThat(sut.getScope(), is(Scope.NULL));
        assertThat(sut.load(), is(Value.NIL));
    }

}
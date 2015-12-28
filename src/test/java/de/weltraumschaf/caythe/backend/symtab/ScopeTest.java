
package de.weltraumschaf.caythe.backend.symtab;

import static org.hamcrest.Matchers.is;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for {@link Scope}.
 */
public class ScopeTest {

    @Test
    public void defaultsOfNullObject() {
        final Scope sut = Scope.NULL;

        assertThat(sut.getScopeName(), is("NULL"));
        assertThat(sut.getEnclosing(), is(Scope.NULL));
        assertThat(sut.hasEnclosing(), is(false));
        assertThat(sut.isDefined("any"), is(false));
        assertThat(sut.resolve("any"), is(Symbol.NULL));
        assertThat(sut.load(Symbol.NULL), is(Value.NIL));
    }

}
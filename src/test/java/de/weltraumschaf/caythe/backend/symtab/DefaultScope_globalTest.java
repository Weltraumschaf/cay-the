package de.weltraumschaf.caythe.backend.symtab;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Tests for {@link DefaultScope global scope}.
 */
public class DefaultScope_globalTest {

    private final Scope sut = Scope.newGlobal();

    @Test
    public void getScopeName() {
        assertThat(sut.getScopeName(), is("global"));
    }

    @Test
    public void define_variable() {
        assertThat(sut.isDefined("foo"), is(false));
        final Symbol variable = new VariableSymbol("foo", Type.NULL);

        sut.define(variable);

        assertThat(sut.isDefined("foo"), is(true));
        assertThat(sut.resolve("foo"), is(variable));
        assertThat(variable.getScope(), sameInstance(sut));
    }

    @Test
    public void define_constant() {
        assertThat(sut.isDefined("foo"), is(false));
        final Symbol constant = new ConstantSymbol("foo", Type.NULL);

        sut.define(constant);

        assertThat(sut.isDefined("foo"), is(true));
        assertThat(sut.resolve("foo"), is(constant));
        assertThat(constant.getScope(), sameInstance(sut));
    }

    @Test
    public void storeAndLoad() {
        final Symbol variable = new VariableSymbol("foo", Type.NULL);
        sut.define(variable);
        final Symbol constant = new ConstantSymbol("bar", Type.NULL);
        sut.define(constant);
        final Value foo = new Value(Type.NULL, "foo");
        sut.store(variable, foo);
        final Value bar = new Value(Type.NULL, "bar");
        sut.store(constant, bar);

        assertThat(sut.load(variable), is(foo));
        assertThat(variable.load(), is(foo));
        assertThat(sut.load(constant), is(bar));
        assertThat(constant.load(), is(bar));
    }
}

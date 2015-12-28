package de.weltraumschaf.caythe.backend.symtab;

import nl.jqno.equalsverifier.EqualsVerifier;
import static org.hamcrest.Matchers.is;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for {@link DefaultScope}.
 */
public class DefaultScopeTest {

    private final Scope sut = new DefaultScope("foo", Scope.NULL) {
    };

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(DefaultScope.class).verify();
    }

    @Test
    public void getScopeName() {
        assertThat(sut.getScopeName(), is("foo"));
    }

    @Test
    public void hasEnclosing() {
        assertThat(sut.hasEnclosing(), is(false));
    }

    @Test
    public void getEnclosing() {
        assertThat(sut.getEnclosing(), is(Scope.NULL));
    }

    @Test(expected = NullPointerException.class)
    public void resolve_nameMustNotBeNull() {
        sut.resolve(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void resolve_nameMustNotBeEmpty() {
        sut.resolve("");
    }

    @Test
    public void resolve_nullSymbolIfNotDefined() {
        assertThat(sut.resolve("foo"), is(Symbol.NULL));
    }

    @Test
    public void load_nilIfNotStored() {
        assertThat(sut.load(Symbol.NULL), is(Value.NIL));
        assertThat(sut.load(new ConstantSymbol("foo", Type.NULL)), is(Value.NIL));
        assertThat(sut.load(new VariableSymbol("foo", Type.NULL)), is(Value.NIL));
    }

    @Test(expected = NullPointerException.class)
    public void store_symbolMustNotBeNull() {
        sut.store(null, Value.NIL);
    }

    @Test(expected = NullPointerException.class)
    public void store_valueMustNotBeNull() {
        sut.store(Symbol.NULL, null);
    }

    @Test(expected = IllegalStateException.class)
    public void store_undefined() {
        sut.store(new VariableSymbol("foo", Type.NULL), Value.NIL);
    }

    @Test
    public void store_variable() {
        final Symbol constant = new ConstantSymbol("foo", Type.NULL);
        sut.define(constant);
        final Symbol variable = new VariableSymbol("foo", Type.NULL);
        sut.define(variable);

        assertThat(sut.load(constant), is(Value.NIL));
        assertThat(sut.load(variable), is(Value.NIL));
        final Value value = new Value(Type.NULL, "bar");

        sut.store(variable, value);

        assertThat(sut.load(constant), is(Value.NIL));
        assertThat(sut.load(variable), is(value));
    }

    @Test
    public void store_constant() {
        final Symbol constant = new ConstantSymbol("foo", Type.NULL);
        sut.define(constant);
        final Symbol variable = new VariableSymbol("foo", Type.NULL);
        sut.define(variable);

        assertThat(sut.load(constant), is(Value.NIL));
        assertThat(sut.load(variable), is(Value.NIL));
        final Value value = new Value(Type.NULL, "bar");

        sut.store(constant, value);

        assertThat(sut.load(constant), is(value));
        assertThat(sut.load(variable), is(Value.NIL));
    }

    @Test(expected = IllegalStateException.class)
    public void store_constantTwiceIsNotAllowed() {
        final Symbol constant = new ConstantSymbol("foo", Type.NULL);
        final Value value = new Value(Type.NULL, "bar");

        sut.store(constant, value);
        sut.store(constant, value);
    }
}


package de.weltraumschaf.caythe.backend.symtab;

import nl.jqno.equalsverifier.EqualsVerifier;
import static org.hamcrest.Matchers.is;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 * Tests for {@link FunctionSymbol}.
 */
public class FunctionSymbolTest {

    private final Scope global = Scope.newGlobal();
    private final Scope sut = new FunctionSymbol("myfunction", FunctionSymbol.VOID, FunctionSymbol.NOARGS, global);

    @Test
    @Ignore("Complains about recursive datastructure albeit it is ignored.")
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(FunctionSymbol.class)
            .allFieldsShouldBeUsedExcept("body")
            .verify();
    }

    @Test
    public void getScopeName() {
        assertThat(sut.getScopeName(), is("myfunction"));
    }

    @Test
    public void hasEnclosing() {
        assertThat(sut.hasEnclosing(), is(true));
    }

    @Test
    public void getEnclosing() {
        assertThat(sut.getEnclosing(), is(global));
    }

    @Test(expected = NullPointerException.class)
    public void resolve_nameMustNotBeNull() {
        sut.resolveValue(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void resolve_nameMustNotBeEmpty() {
        sut.resolveValue("");
    }

    @Test
    public void resolve_nullSymbolIfNotDefined() {
        assertThat(sut.resolveValue("foo"), is(Symbol.NULL));
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
        sut.defineValue(constant);
        final Symbol variable = new VariableSymbol("foo", Type.NULL);
        sut.defineValue(variable);

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
        sut.defineValue(constant);
        final Symbol variable = new VariableSymbol("foo", Type.NULL);
        sut.defineValue(variable);

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
        sut.defineValue(constant);
        final Value value = new Value(Type.NULL, "bar");

        sut.store(constant, value);
        sut.store(constant, value);
    }

}
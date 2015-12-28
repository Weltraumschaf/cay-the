package de.weltraumschaf.caythe.backend.symtab;

import nl.jqno.equalsverifier.EqualsVerifier;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Tests for {@link DefaultScope local scope}.
 * <p>
 * Tests nested scopes:
 * </p>
 * <pre>
 * +-------------+
 * |   global    |
 * +------+------+
 *        ^
 *        |
 * +------+------+
 * |    first    |
 * +------+------+
 *        ^
 *        |
 * +------+------+
 * |    second   |
 * +-------------+
 *
 * </pre>
 */
public class DefaultScope_localTest {

    private final Scope global = Scope.newGlobal();
    private final Scope first = Scope.newLocal(global);
    private final Scope second = Scope.newLocal(first);

    @Test
    public void getScopeName() {
        assertThat(first.getScopeName(), is("local"));
        assertThat(second.getScopeName(), is("local"));
    }

    @Test
    public void define() {
        final VariableSymbol fooInGlobal = new VariableSymbol("foo", Type.NULL);
        global.define(fooInGlobal);
        final VariableSymbol barInFirst = new VariableSymbol("bar", Type.NULL);
        first.define(barInFirst);
        final VariableSymbol bazInSecond = new VariableSymbol("baz", Type.NULL);
        second.define(bazInSecond);

        assertThat(second.isDefined("foo"), is(true));
        assertThat(second.resolve("foo"), is(fooInGlobal));
        assertThat(second.isDefined("bar"), is(true));
        assertThat(second.resolve("bar"), is(barInFirst));
        assertThat(second.isDefined("baz"), is(true));
        assertThat(second.resolve("baz"), is(bazInSecond));
    }

    @Test
    public void storeAndLoad() {

    }
}

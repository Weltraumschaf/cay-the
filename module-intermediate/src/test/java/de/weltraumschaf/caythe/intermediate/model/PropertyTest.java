package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.caythe.intermediate.ast.NoOperation;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link Property}.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class PropertyTest {

    @Test
    public void defaultGetter() throws Exception {
        final TypeName returnType = new TypeName("org.snafu", "Foo");

        final Method getter = Property.defaultGetter("foo", Visibility.PACKAGE, returnType);

        assertThat(getter.getName(), is("foo"));
        assertThat(getter.getArguments(), hasSize(0));
        assertThat(getter.getBody(), is(new NoOperation()));
        assertThat(getter.getReturnType(), is(returnType));
        assertThat(getter.getVisibility(), is(Visibility.PACKAGE));
    }

    @Test
    public void defaultSetter() throws Exception {
        final TypeName argumentType = new TypeName("org.snafu", "Foo");

        final Method setter = Property.defaultSetter("foo", Visibility.PACKAGE, argumentType);

        assertThat(setter.getName(), is("foo"));
        assertThat(setter.getArguments(), hasSize(1));
        assertThat(setter.getArguments(), containsInAnyOrder(
            new Argument("newFoo", argumentType)
        ));
        assertThat(setter.getBody(), is(new NoOperation()));
        assertThat(setter.getReturnType(), is(TypeName.NONE));
        assertThat(setter.getVisibility(), is(Visibility.PACKAGE));
    }

}
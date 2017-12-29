package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.caythe.intermediate.Notification;
import de.weltraumschaf.caythe.intermediate.ast.NoOperation;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link Property}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
@Ignore
public class PropertyTest {

    private final Property sut = new Property(
        "myProperty", Visibility.PACKAGE, new TypeName("org.foo", "Bar"));

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

    @Test
    public void probeEquivalence_toItself() {
        final Notification result = new Notification();

        sut.probeEquivalence(sut, result);

        assertThat(result.isOk(), is(true));
    }

    @Test
    public void probeEquivalence_differentVisibility() {
        final Property other = new Property(
            "myProperty", Visibility.PUBLIC, new TypeName("org.foo", "Bar"));
        final Notification result = new Notification();

        sut.probeEquivalence(other, result);

        assertThat(result.isOk(), is(false));
        assertThat(
            result.report(),
            is("Visibility differ: This has visibility 'PACKAGE' but other has visibility 'PUBLIC'!"));
    }

    @Test
    public void probeEquivalence_differentType() {
        final Property other = new Property(
            "myProperty", Visibility.PACKAGE, new TypeName("org.foo", "Baz"));
        final Notification result = new Notification();

        sut.probeEquivalence(other, result);

        assertThat(result.isOk(), is(false));
        assertThat(
            result.report(),
            is("Type differ: This has type 'org.foo.Bar' but other has type 'org.foo.Baz'!"));
    }

    @Test
    public void probeEquivalence_differentName() {
        final Property other = new Property(
            "snafu", Visibility.PACKAGE, new TypeName("org.foo", "Bar"));
        final Notification result = new Notification();

        sut.probeEquivalence(other, result);

        assertThat(result.isOk(), is(false));
        assertThat(
            result.report(),
            is("Name differ: This has name 'myProperty' but other has name 'snafu'!"));
    }

    @Test
    @Ignore
    public void probeEquivalence_differentGetter() {}

    @Test
    @Ignore
    public void probeEquivalence_differentSetter() {}
}
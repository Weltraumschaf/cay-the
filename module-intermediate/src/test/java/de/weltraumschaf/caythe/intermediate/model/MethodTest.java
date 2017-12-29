package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.caythe.intermediate.Notification;
import de.weltraumschaf.caythe.intermediate.ast.NoOperation;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link Method}.
 */
@Ignore
public class MethodTest {

    private final Method sut = new Method(
        "myMethod",
        Visibility.PACKAGE,
        new TypeName("org.foo", "Bar"),
        Collections.singleton(new Argument("foo", new TypeName("org.foo", "Foo"))),
        new NoOperation());

    @Test
    public void probeEquivalence_toItself() {
        final Notification result = new Notification();

        sut.probeEquivalence(sut, result);

        assertThat(result.isOk(), is(true));
    }

    @Test
    public void probeEquivalence_differentVisibility() {
        final Method other = new Method(
            "myMethod",
            Visibility.PUBLIC,
            new TypeName("org.foo", "Bar"),
            Collections.singleton(new Argument("foo", new TypeName("org.foo", "Foo"))),
            new NoOperation());
        final Notification result = new Notification();

        sut.probeEquivalence(other, result);

        assertThat(result.isOk(), is(false));
        assertThat(
            result.report(),
            is("Visibility differ: This has visibility 'PACKAGE' but other has visibility 'PUBLIC'!"));
    }

    @Test
    public void probeEquivalence_differentName() {
        final Method other = new Method(
            "snafu",
            Visibility.PACKAGE,
            new TypeName("org.foo", "Bar"),
            Collections.singleton(new Argument("foo", new TypeName("org.foo", "Foo"))),
            new NoOperation());
        final Notification result = new Notification();

        sut.probeEquivalence(other, result);

        assertThat(result.isOk(), is(false));
        assertThat(
            result.report(),
            is("Name differ: This has name 'myMethod' but other has name 'snafu'!"));
    }

    @Test
    public void probeEquivalence_differentReturnType() {
        final Method other = new Method(
            "myMethod",
            Visibility.PACKAGE,
            new TypeName("org.foo", "Baz"),
            Collections.singleton(new Argument("foo", new TypeName("org.foo", "Foo"))),
            new NoOperation());
        final Notification result = new Notification();

        sut.probeEquivalence(other, result);

        assertThat(result.isOk(), is(false));
        assertThat(
            result.report(),
            is("Return type differ: This has return type 'org.foo.Bar' but other has return type 'org.foo.Baz'!"));
    }

    @Test
    public void probeEquivalence_differentArgumentsLength() {
        final Method other = new Method(
            "myMethod",
            Visibility.PACKAGE,
            new TypeName("org.foo", "Bar"),
            Arrays.asList(
                new Argument("foo", new TypeName("org.foo", "Foo")),
                new Argument("foo", new TypeName("org.foo", "Bar"))),
            new NoOperation());
        final Notification result = new Notification();

        sut.probeEquivalence(other, result);

        assertThat(result.isOk(), is(false));
        assertThat(
            result.report(),
            is("Argument count differ: This has 1 arguments but other has 2 arguments!"));
    }

    @Test
    @Ignore
    public void probeEquivalence_differentArguments() {}

    @Test
    @Ignore
    public void probeEquivalence_differentBody() {}
}
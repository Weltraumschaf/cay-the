package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.backend.Pool.Type;
import de.weltraumschaf.caythe.backend.Pool.Value;
import nl.jqno.equalsverifier.EqualsVerifier;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests for {@link Pool.Value}.
 */
public final class Pool_ValueTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Value.class).verify();
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void typeMustNotBenull() {
        new Value(null, new Object());
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void valueMustNotBeNull() {
        new Value(Type.NIL, null);
    }

    @Test
    public void isType() {
        assertThat(new Value(Type.NIL, new Object()).isType(Type.NIL), is(true));
        assertThat(new Value(Type.NIL, new Object()).isType(Type.INT), is(false));
    }

    @Test
    public void isNil() {
        assertThat(new Value(Type.NIL, new Object()).isNil(), is(true));
        assertThat(new Value(Type.INT, new Object()).isNil(), is(false));
    }

    @Test
    public void nil_asBool() {
        assertThat(Value.NIL.asBool(), is(false));
    }

    @Test
    public void nil_asInt() {
        assertThat(Value.NIL.asInt(), is(0));
    }

    @Test
    public void nil_asFoat() {
        assertThat(Value.NIL.asFloat(), is(0.0f));
    }

    @Test
    public void nil_asString() {
        assertThat(Value.NIL.asString(), is(""));
    }

    @Test
    public void true_asBool() {
        assertThat(Value.TRUE.asBool(), is(true));
    }

    @Test
    public void true_asInt() {
        assertThat(Value.TRUE.asInt(), is(1));
    }

    @Test
    public void true_asFoat() {
        assertThat(Value.TRUE.asFloat(), is(1.0f));
    }

    @Test
    public void true_asString() {
        assertThat(Value.TRUE.asString(), is("true"));
    }

    @Test
    public void false_asBool() {
        assertThat(Value.FALSE.asBool(), is(false));
    }

    @Test
    public void false_asInt() {
        assertThat(Value.FALSE.asInt(), is(0));
    }

    @Test
    public void false_asFoat() {
        assertThat(Value.FALSE.asFloat(), is(0.0f));
    }

    @Test
    public void false_asString() {
        assertThat(Value.FALSE.asString(), is("false"));
    }

    @Test
    public void int_asBool() {
        assertThat(new Value(Type.INT, 42).asBool(), is(true));
        assertThat(new Value(Type.INT, 0).asBool(), is(false));
        assertThat(new Value(Type.INT, -23).asBool(), is(true));
    }

    @Test
    public void int_asInt() {
        assertThat(new Value(Type.INT, 42).asInt(), is(42));
        assertThat(new Value(Type.INT, 0).asInt(), is(0));
        assertThat(new Value(Type.INT, -23).asInt(), is(-23));
    }

    @Test
    public void int_asFoat() {
        assertThat(new Value(Type.INT, 42).asFloat(), is(42.0f));
        assertThat(new Value(Type.INT, 0).asFloat(), is(0.0f));
        assertThat(new Value(Type.INT, -23).asFloat(), is(-23.0f));
    }

    @Test
    public void int_asString() {
        assertThat(new Value(Type.INT, 42).asString(), is("42"));
        assertThat(new Value(Type.INT, 0).asString(), is("0"));
        assertThat(new Value(Type.INT, -23).asString(), is("-23"));
    }

    @Test
    public void float_asBool() {
        assertThat(new Value(Type.FLOAT, 42.22222f).asBool(), is(true));
        assertThat(new Value(Type.FLOAT, 0.0f).asBool(), is(false));
        assertThat(new Value(Type.FLOAT, -23.232323f).asBool(), is(true));
    }

    @Test
    public void float_asInt() {
        assertThat(new Value(Type.FLOAT, 42.22222f).asInt(), is(42));
        assertThat(new Value(Type.FLOAT, 0.0f).asInt(), is(0));
        assertThat(new Value(Type.FLOAT, -23.232323f).asInt(), is(-23));
    }

    @Test
    public void float_asFoat() {
        assertThat(new Value(Type.FLOAT, 42.22222f).asFloat(), is(42.22222f));
        assertThat(new Value(Type.FLOAT, 0.0f).asFloat(), is(0.0f));
        assertThat(new Value(Type.FLOAT, -23.232323f).asFloat(), is(-23.232323f));
    }

    @Test
    public void float_asString() {
        assertThat(new Value(Type.FLOAT, 42.22222f).asString(), is("42.22222"));
        assertThat(new Value(Type.FLOAT, 0.0f).asString(), is("0.0"));
        assertThat(new Value(Type.FLOAT, -23.232323f).asString(), is("-23.232323"));
    }

    @Test
    public void string_asBool() {
        assertThat(new Value(Type.STRING, "true").asBool(), is(true));
        assertThat(new Value(Type.STRING, "false").asBool(), is(false));
        assertThat(new Value(Type.STRING, "").asBool(), is(false));
        assertThat(new Value(Type.STRING, "snafu").asBool(), is(false));
        assertThat(new Value(Type.STRING, "42").asBool(), is(false));
    }

    @Test
    public void string_asInt() {
        assertThat(new Value(Type.STRING, "").asInt(), is(0));
        assertThat(new Value(Type.STRING, "foo").asInt(), is(0));
        assertThat(new Value(Type.STRING, "true").asInt(), is(0));
        assertThat(new Value(Type.STRING, "23").asInt(), is(23));
    }

    @Test
    public void string_asFoat() {
        assertThat(new Value(Type.STRING, "").asFloat(), is(0.0f));
        assertThat(new Value(Type.STRING, "foo").asFloat(), is(0.0f));
        assertThat(new Value(Type.STRING, "true").asFloat(), is(0.0f));
        assertThat(new Value(Type.STRING, "23.333").asFloat(), is(23.333f));
    }

    @Test
    public void string_asString() {
        assertThat(new Value(Type.STRING, "foo bar").asString(), is("foo bar"));
    }
}

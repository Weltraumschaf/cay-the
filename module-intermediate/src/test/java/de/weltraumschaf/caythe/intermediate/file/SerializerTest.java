package de.weltraumschaf.caythe.intermediate.file;

import static de.weltraumschaf.caythe.intermediate.file.SharedFixtures.*;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link Serializer}.
 */
public class SerializerTest {

    private final Serializer sut = new Serializer();

    @Test
    public void serialize_nullGiven() {
        assertThat(sut.serialize(null), is(""));
    }

    @Test
    public void serialize() throws UnsupportedEncodingException {
        assertThat(sut.serialize(MODULE_FIXTURE), is(JSON_FIXTURE));
    }
}

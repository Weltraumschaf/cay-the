package de.weltraumschaf.caythe.backend.vm;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class ByteFormatterTest {
    private final ByteFormatter sut = new ByteFormatter();

    @Test
    public void toLong() {
        assertThat(sut.toLong(new byte[] {0, 0, 0, 0, 0, 0, 0, 0x2A}), is(42L));
        assertThat(
            sut.toLong(new byte[] {0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}),
            is(9223372036854775807L));
    }

    @Test
    public void fromLong() {
        assertThat(sut.fromLong(42L), is(new byte[] {0, 0, 0, 0, 0, 0, 0, 0x2A}));
        assertThat(
            sut.fromLong(9223372036854775807L),
            is(new byte[] {0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}));
    }

    @Test
    public void toInt() {
        assertThat(sut.toInt(new byte[] {0, 0, 0, 0x2A}), is(42));
    }

    @Test
    public void fromInt() {
        assertThat(sut.fromInt(42), is(new byte[] {0, 0, 0, 0x2A}));
    }

}
package de.weltraumschaf.caythe.intermediate.file;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.zip.DataFormatException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link Compressor}.
 */
public class CompressorTest {
    private static final byte[] LONG_BYTE_ARRAY_UNCOMPRESSED
        = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor.\n".getBytes();
    private static final byte[] LONG_BYTE_ARRAY_COMPRESSED
        = new byte[] {
        120, -100, 37, -52, -63, 9, -64, 32, 12, 5, -48, 123, -89, -8, 3, -108, -18, -48, 123, -105, 8, -6,
        -111, -128, 73, 68, -29, -2, 45, -12, -2, 120, 79, 76, 26, 116, -84, 109, -88, -47, 99, 98, 105, 66,
        -116, 121, -94, -124, 47, -106, 100, 110, 78, 72, -43, -95, -85, -88, 55, -80, 107, 94, -72, -23, 20,
        -1, -112, 89, -44, 64, -41, -74, -69, -128, -115, -7, 71, -41, -15, 2, 54, -98, 33, -83};

    private final Compressor sut = new Compressor();

    @Test
    public void compress_nullAsInput() throws IOException {
        assertThat(sut.compress(null), is(new byte[0]));
    }

    @Test
    public void compress_emptyAsInput() throws IOException {
        assertThat(sut.compress(new byte[0]), is(new byte[0]));
    }

    @Test
    public void compress() throws IOException {
        assertThat(
            sut.compress(LONG_BYTE_ARRAY_UNCOMPRESSED),
            is(LONG_BYTE_ARRAY_COMPRESSED));
    }

    @Test
    public void decompress_nullAsInput() throws IOException, DataFormatException {
        assertThat(sut.decompress(null), is(new byte[0]));
    }

    @Test
    public void decompress_emptyAsInput() throws IOException, DataFormatException {
        assertThat(sut.decompress(new byte[0]), is(new byte[0]));
    }

    @Test
    public void decompress() throws IOException, DataFormatException {
        assertThat(sut.decompress(LONG_BYTE_ARRAY_COMPRESSED), is(LONG_BYTE_ARRAY_UNCOMPRESSED));
    }

    @Test
    public void compressAndDecompress() throws IOException, DataFormatException {
        assertThat(sut.decompress(sut.compress("Hello, World!".getBytes())), is("Hello, World!".getBytes()));
    }
}

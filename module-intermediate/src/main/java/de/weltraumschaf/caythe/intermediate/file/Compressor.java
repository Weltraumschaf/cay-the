package de.weltraumschaf.caythe.intermediate.file;

import java.io.ByteArrayOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * This class compresses and decompresses byte arrays.
 */
final class Compressor {

    private static final int BUFFER_SIZE = 1024;

    /**
     * Compresses the given byte array.
     * <p>
     * If {@code null} or empty is given then an empty array will be returned.
     * </p>
     * 
     * @param data
     *        maybe {@code null} or empty
     * @return never {@code null}, maybe empty
     */
    byte[] compress(final byte[] data) {
        if (null == data || data.length == 0) {
            return new byte[0];
        }

        final Deflater deflater = new Deflater();
        deflater.setInput(data);
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        deflater.finish();
        final byte[] buffer = new byte[BUFFER_SIZE];

        while (!deflater.finished()) {
            int count = deflater.deflate(buffer); // returns the generated code... index
            outputStream.write(buffer, 0, count);
        }

        try {
            outputStream.close();
        } catch (final IOException e) {
            // Should never happen because we only deal with in memory byte array streams.
            throw new IOError(e);
        }

        return outputStream.toByteArray();
    }

    /**
     * Decompresses the given byte array.
     * <p>
     * If {@code null} or empty is given then an empty array will be returned.
     * </p>
     * 
     * @param data
     *        maybe {@code null} or empty
     * @return never {@code null}, maybe empty
     */
    byte[] decompress(final byte[] data) throws DataFormatException {
        if (null == data || data.length == 0) {
            return new byte[0];
        }

        final Inflater inflater = new Inflater();
        inflater.setInput(data);
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        final byte[] buffer = new byte[BUFFER_SIZE];

        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }

        try {
            outputStream.close();
        } catch (IOException e) {
            // Should never happen because we only deal with in memory byte array streams.
            throw new IOError(e);
        }

        return outputStream.toByteArray();
    }
}

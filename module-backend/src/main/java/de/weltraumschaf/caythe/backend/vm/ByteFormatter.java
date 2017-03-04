package de.weltraumschaf.caythe.backend.vm;

import de.weltraumschaf.commons.validate.Validate;

import java.nio.ByteBuffer;

// http://stackoverflow.com/questions/4485128/how-do-i-convert-long-to-byte-and-back-in-java
final class ByteFormatter {
    long toLong(final byte[] bytes) {
        Validate.notNull(bytes, "bytes");

        if (bytes.length != 8) {
            throw new IllegalArgumentException();
        }

        final ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        buffer.flip(); //need flip
        return buffer.getLong();
    }

    byte[] fromLong(final long l) {
        final ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(l);
        return buffer.array();
    }

    int toInt(final byte[] bytes) {
        Validate.notNull(bytes, "bytes");

        if (bytes.length != 4) {
            throw new IllegalArgumentException();
        }

        final ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.put(bytes);
        buffer.flip(); //need flip
        return buffer.getInt();
    }

    byte[] fromInt(final int i) {
        final ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(i);
        return buffer.array();

    }
}

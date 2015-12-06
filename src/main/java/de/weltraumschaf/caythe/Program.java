package de.weltraumschaf.caythe;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 */
final class Program {

    private static final int INTEGER_NUMBER_OF_BYTES = 4;

    private final Collection<Byte> byteCode = new ArrayList<>();

    public void add(final Opcodes instruction) {
        byteCode.add(instruction.getCode());
    }

    public void add(final byte register) {
        byteCode.add(register);
    }

    public void add(final int argument) {
        final byte[] bytes = ByteBuffer.allocate(INTEGER_NUMBER_OF_BYTES).putInt(argument).array();

        for (byte b : bytes) {
            byteCode.add(b);
        }
    }

    public Collection<Byte> getByteCode() {
        return Collections.unmodifiableCollection(byteCode);
    }

}

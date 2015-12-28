package de.weltraumschaf.caythe.backend.compiler;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A program is simply an large array of bytes (opcodes and their arguments.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class Program {

    /**
     * Hom many bytes does an integer have.
     */
    private static final int INTEGER_NUMBER_OF_BYTES = 4;
    /**
     * Holds the program as byte code.
     */
    private final Collection<Byte> byteCode = new ArrayList<>();

    /**
     * Appends a opcode to the program.
     *
     * @param instruction must not be {@code null}
     */
    void add(final Opcodes instruction) {
        byteCode.add(instruction.getCode());
    }

    /**
     * Appends a register address to the program.
     *
     * @param register any byte
     */
    void add(final byte register) {
        byteCode.add(register);
    }

    /**
     * Adds an integer argument as number of bytes to the program.
     *
     * @param argument any integer
     */
    void add(final int argument) {
        final byte[] bytes = ByteBuffer.allocate(INTEGER_NUMBER_OF_BYTES).putInt(argument).array();

        for (byte b : bytes) {
            byteCode.add(b);
        }
    }

    /**
     * Get the program as byte code collection.
     *
     * @return never {@code null}, unmodifiable
     */
    public Collection<Byte> getByteCode() {
        return Collections.unmodifiableCollection(byteCode);
    }

}

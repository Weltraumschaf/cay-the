package de.weltraumschaf.caythe.backend.compiler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Enumerates all opcodes implemented by the VM.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
enum Opcodes {

    /**
     * No operation.
     */
    NOP((byte) 0x00),
    /**
     * Arguments: Operand register, operand register, result register.
     */
    INT_ADD((byte) 0x01),
    /**
     * Arguments: Operand register, operand register, result register.
     */
    INT_SUBTRACT((byte) 0x02),
    /**
     * Arguments: Operand register, operand register, result register.
     */
    INT_MULTIPLY((byte) 0x03),
    /**
     * Arguments: Operand register, operand register, result register.
     */
    INT_DIVISION((byte) 0x04),
    /**
     * Arguments: Output register.
     */
    PRINT((byte) 0x05),
    /**
     * Arguments: Register, Value.
     */
    INT_STORE((byte) 0x06);

    /**
     * Lookup table to create enums from bytes.
     */
    private static final Map<Byte, Opcodes> LOOKUP;

    static {
        final Map<Byte, Opcodes> tmp = new HashMap<>();

        for (final Opcodes code : values()) {
            tmp.put(code.getCode(), code);
        }

        LOOKUP = Collections.unmodifiableMap(tmp);
    }

    /**
     * The byte code of the opcode.
     */
    private final byte code;

    /**
     * Dedicated constructor.
     *
     * @param code any byte
     */
    private Opcodes(final byte code) {
        this.code = code;
    }

    /**
     * Get the executable byte.
     *
     * @return any byte
     */
    public byte getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.format("%s(0x%02X)", name(), code);
    }

    /**
     * Creates the enum type from given byte.
     * <p>
     * Throws {@link IllegalArgumentException} if undefined byte is given.
     * </p>
     *
     * @param opcode any byte.
     * @return never {@code null}
     */
    public static Opcodes getFor(final byte opcode) {
        if (LOOKUP.containsKey(opcode)) {
            return LOOKUP.get(opcode);
        }

        throw new IllegalArgumentException(String.format("Unknown byte: %02X!", opcode));
    }
}

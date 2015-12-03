package de.weltraumschaf.caythe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 */
public enum Opcodes {

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
    PRINT((byte)0x05),
    /**
     * Arguments: Register, Value
     */
    INT_STORE((byte)0x06);

    private static final Map<Byte, Opcodes> LOOKUP;
    static {
        final Map<Byte, Opcodes> tmp = new HashMap<>();

        for (final Opcodes code : values()) {
            tmp.put(code.getCode(), code);
        }

        LOOKUP = Collections.unmodifiableMap(tmp);
    }

    private final byte code;

    private Opcodes(final byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.format("%s(0x%02X)", name(), code);
    }

    public static Opcodes getFor(final byte opcode) {
        if (LOOKUP.containsKey(opcode)) {
            return LOOKUP.get(opcode);
        }

        throw new IllegalArgumentException(String.format("Unknown byte: %02X!", opcode));
    }
}

package de.weltraumschaf.caythe;

/**
 */
public enum Opcodes {

    NOP((byte) 0x00),
    /**
     * Arguments: Operand, Operand, Result.
     */
    INT_ADD((byte) 0x01),
    /**
     * Arguments: Operand, Operand, Result.
     */
    INT_SUBTRACT((byte) 0x02),
    /**
     * Arguments: Operand, Operand, Result.
     */
    INT_MULTIPLY((byte) 0x03),
    /**
     * Arguments: Operand, Operand, Result.
     */
    INT_DIVISION((byte) 0x04);

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

}

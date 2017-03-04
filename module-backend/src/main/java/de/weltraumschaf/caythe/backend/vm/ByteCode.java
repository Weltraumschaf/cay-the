package de.weltraumschaf.caythe.backend.vm;

import java.util.*;

enum ByteCode {

    NOOP((byte) 0x00, 0),
    IADD((byte) 0x01, 0),     // int add
    ISUB((byte) 0x02, 0),
    IMUL((byte) 0x03, 0),
    ILT((byte) 0x04, 0),     // int less than
    IEQ((byte) 0x05, 0),     // int equal
    BR((byte) 0x06, 1),     // branch
    BRT((byte) 0x07, 1),     // branch if true
    BRF((byte) 0x08, 1),     // branch if true
    ICONST((byte) 0x09, 1),   // push constant integer
    LOAD((byte) 0x010, 1),  // load from local context
    GLOAD((byte) 0x011, 1),  // load from global memory
    STORE((byte) 0x012, 1),  // store in local context
    GSTORE((byte) 0x013, 1),  // store in global memory
    PRINT((byte) 0x014, 0),  // print stack top
    POP((byte) 0x015, 0),    // throw away top of stack
    CALL((byte) 0x016, 1),
    RET((byte) 0x017, 0),    // return with/without value

    HALT((byte) 0x018, 0);

    static final Map<String, ByteCode> MNEMONIC_TO_CODE;
    static {
        final Map<String, ByteCode> tmp = new HashMap<>();
        tmp.put("iadd", IADD);
        tmp.put("isub", ISUB);
        tmp.put("imul", IMUL);
        tmp.put("ilt", ILT);
        tmp.put("ieq", IEQ);
        tmp.put("br", BR);
        tmp.put("brt", BRT);
        tmp.put("brf", BRF);
        tmp.put("iconst", ICONST);
        tmp.put("load", LOAD);
        tmp.put("gload", GLOAD);
        tmp.put("store", STORE);
        tmp.put("gstore", GSTORE);
        tmp.put("print", PRINT);
        tmp.put("pop", POP);
        tmp.put("call", CALL); // call index of function in meta-info table
        tmp.put("ret", RET);
        tmp.put("halt", HALT);
        MNEMONIC_TO_CODE = Collections.unmodifiableMap(tmp);
    }

    private static final ByteCode[] OPCODE_TO_CODE = new ByteCode[256];
    static {
        for (final ByteCode bc : values()) {
            OPCODE_TO_CODE[bc.getOpcode()] = bc;
        }
    }

    private final byte opcode;
    private final int numberOfArguments;

    ByteCode(final byte opcode, final int numberOfArguments) {
        this.opcode = opcode;
        this.numberOfArguments = numberOfArguments;
    }

    byte getOpcode() {
        return opcode;
    }

    int getNumberOfArguments() {
        return numberOfArguments;
    }

    static ByteCode decode(final byte opcode) {
        return OPCODE_TO_CODE[opcode];
    }
}

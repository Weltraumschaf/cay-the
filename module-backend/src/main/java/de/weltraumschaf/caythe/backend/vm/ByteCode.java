package de.weltraumschaf.caythe.backend.vm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

final class ByteCode {
    static final byte IADD = 1;     // int add
    static final byte ISUB = 2;
    static final byte IMUL = 3;
    static final byte ILT = 4;     // int less than
    static final byte IEQ = 5;     // int equal
    static final byte BR = 6;     // branch
    static final byte BRT = 7;     // branch if true
    static final byte BRF = 8;     // branch if true
    static final byte ICONST = 9;   // push constant integer
    static final byte LOAD = 10;  // load from local context
    static final byte GLOAD = 11;  // load from global memory
    static final byte STORE = 12;  // store in local context
    static final byte GSTORE = 13;  // store in global memory
    static final byte PRINT = 14;  // print stack top
    static final byte POP = 15;    // throw away top of stack
    static final byte CALL = 16;
    static final byte RET = 17;    // return with/without value

    static final byte HALT = 18;

    static final Map<String, Byte> MNEMONIC_TO_CODE = new HashMap<>();

    static {
        MNEMONIC_TO_CODE.put("iadd", IADD);
        MNEMONIC_TO_CODE.put("isub", ISUB);
        MNEMONIC_TO_CODE.put("imul", IMUL);
        MNEMONIC_TO_CODE.put("ilt", ILT);
        MNEMONIC_TO_CODE.put("ieq", IEQ);
        MNEMONIC_TO_CODE.put("br", BR);
        MNEMONIC_TO_CODE.put("brt", BRT);
        MNEMONIC_TO_CODE.put("brf", BRF);
        MNEMONIC_TO_CODE.put("iconst", ICONST);
        MNEMONIC_TO_CODE.put("load", LOAD);
        MNEMONIC_TO_CODE.put("gload", GLOAD);
        MNEMONIC_TO_CODE.put("store", STORE);
        MNEMONIC_TO_CODE.put("gstore", GSTORE);
        MNEMONIC_TO_CODE.put("print", PRINT);
        MNEMONIC_TO_CODE.put("pop", POP);
        MNEMONIC_TO_CODE.put("call", CALL); // call index of function in meta-info table
        MNEMONIC_TO_CODE.put("ret", RET);
        MNEMONIC_TO_CODE.put("halt", HALT);
    }

    static final Set<Byte> ZERO_ARGUMENTS = new HashSet<>();
    static {
        ZERO_ARGUMENTS.add(IADD);
        ZERO_ARGUMENTS.add(ISUB);
        ZERO_ARGUMENTS.add(IMUL);
        ZERO_ARGUMENTS.add(ILT);
        ZERO_ARGUMENTS.add(IEQ);
        ZERO_ARGUMENTS.add(PRINT);
        ZERO_ARGUMENTS.add(POP);
        ZERO_ARGUMENTS.add(RET);
        ZERO_ARGUMENTS.add(HALT);
    }
    static final Set<Byte> ONE_ARGUMENTS = new HashSet<>();
    static {
        ONE_ARGUMENTS.add(BR);
        ONE_ARGUMENTS.add(BRT);
        ONE_ARGUMENTS.add(BRF);
        ONE_ARGUMENTS.add(ICONST);
        ONE_ARGUMENTS.add(LOAD);
        ONE_ARGUMENTS.add(GLOAD);
        ONE_ARGUMENTS.add(STORE);
        ONE_ARGUMENTS.add(GSTORE);
        ONE_ARGUMENTS.add(CALL);
    }
}

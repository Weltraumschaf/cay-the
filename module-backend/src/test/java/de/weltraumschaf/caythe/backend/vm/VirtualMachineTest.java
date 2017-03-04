package de.weltraumschaf.caythe.backend.vm;

import de.weltraumschaf.commons.testing.CapturingPrintStream;
import org.junit.Ignore;
import org.junit.Test;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static de.weltraumschaf.caythe.backend.vm.ByteCode.*;

public final class VirtualMachineTest {

    static int[] hello = {ICONST, 1, ICONST, 2, IADD, PRINT, HALT};

    /*
     * iconst(1); iconst(2); iadd(); print(); halt();
     */

    private static final int[] loop = {
        // .GLOBALS 2; N, I
        // N = 10 ADDRESS
        ICONST, 10, // 0
        GSTORE, 0, // 2
        // I = 0
        ICONST, 0, // 4
        GSTORE, 1, // 6
        // WHILE I<N:
        // START (8):
        GLOAD, 1, // 8
        GLOAD, 0, // 10
        ILT, // 12
        BRF, 24, // 13
        // I = I + 1
        GLOAD, 1, // 15
        ICONST, 1, // 17
        IADD, // 19
        GSTORE, 1, // 20
        BR, 8, // 22
        // DONE (24):
        // PRINT "LOOPED "+N+" TIMES."
        HALT // 24
    };
    private static final FuncMetaData[] loop_metadata = {new FuncMetaData("main", 0, 0, 0)};

    private static final int FACTORIAL_INDEX = 1;
    private static final int FACTORIAL_ADDRESS = 0;
    private static final int MAIN_ADDRESS = 21;
    private static final byte[] factorial = {
        // .def factorial: ARGS=1, LOCALS=0 ADDRESS
        // IF N < 2 RETURN 1
        LOAD, 0, // 0
        ICONST, 2, // 2
        ILT, // 4
        BRF, 10, // 5
        ICONST, 1, // 7
        RET, // 9
        // CONT:
        // RETURN N * FACT(N-1)
        LOAD, 0, // 10
        LOAD, 0, // 12
        ICONST, 1, // 14
        ISUB, // 16
        CALL, FACTORIAL_INDEX, // 17
        IMUL, // 19
        RET, // 20
        // .DEF MAIN: ARGS=0, LOCALS=0
        // PRINT FACT(1)
        ICONST, 5, // 21 <-- MAIN METHOD!
        CALL, FACTORIAL_INDEX, // 23
        PRINT, // 25
        HALT // 26
    };
    private static final FuncMetaData[] factorial_metadata = {
        // .def factorial: ARGS=1, LOCALS=0 ADDRESS
        new FuncMetaData("main", 0, 0, MAIN_ADDRESS), new FuncMetaData("factorial", 1, 0, FACTORIAL_ADDRESS)};

    private static final int[] f = {
        // ADDRESS
        // .def main() { print f(10); }
        ICONST, 10, // 0
        CALL, 1, // 2
        PRINT, // 4
        HALT, // 5
        // .def f(x): ARGS=1, LOCALS=1
        // a = x;
        LOAD, 0, // 6 <-- start of f
        STORE, 1,
        // return 2*a
        LOAD, 1, ICONST, 2, IMUL, RET};
    private static final FuncMetaData[] f_metadata = {new FuncMetaData("main", 0, 0, 0), new FuncMetaData("f", 1, 1, 6)};

    @Test
    @Ignore
    public void run() throws UnsupportedEncodingException {
        final PrintStream out = new CapturingPrintStream();
        final VirtualMachine vm = new VirtualMachine(factorial, 0, factorial_metadata, out);
        vm.run(factorial_metadata[0].address());
    }
}
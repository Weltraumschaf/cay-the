package de.weltraumschaf.caythe;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 */
public final class VirtualMachine {

    private final Register reg = new Register();
    private final StringBuilder stdOut = new StringBuilder();
    private final List<Byte> byteCode;
    private int instructionPointer;

    public VirtualMachine(final Programm prog) {
        super();
        this.byteCode = new ArrayList<>(prog.getByteCode());
    }

    public void run() {
        stdOut.setLength(0); // Clear buffer.
        instructionPointer = 0;

        while (hasNext()) {
            final Opcodes opcode = Opcodes.getFor(current());
            next();
            execute(opcode);
        }
    }

    public String stdOut() {
        return stdOut.toString();
    }

    private byte current() {
        return byteCode.get(instructionPointer);
    }

    private boolean hasNext() {
        return instructionPointer < byteCode.size();
    }

    private void next() {
        ++instructionPointer;
    }

    private void execute(final Opcodes opcode) {
        switch (opcode) {
            case INT_STORE:
                storeInteger();
                break;
            case INT_ADD:
                addIntegers();
                break;
            case PRINT:
                print();
                break;
        }
    }

    private void print() {
        final byte targetRegister = current();
        next();
        stdOut.append(reg.get(targetRegister));
    }

    private void addIntegers() {
        final byte firstOperandRegister = current();
        next();
        final byte secondOperandRegister = current();
        next();
        final byte resultRegister = current();
        next();
        reg.set(resultRegister, reg.get(firstOperandRegister) + reg.get(secondOperandRegister));
    }

    private void storeInteger() {
        final byte targetRegister = current();
        next();
        final byte[] value = new byte[4];
        value[0] = current();
        next();
        value[1] = current();
        next();
        value[2] = current();
        next();
        value[3] = current();
        next();
        reg.set(targetRegister, ByteBuffer.wrap(value).getInt());
    }
}

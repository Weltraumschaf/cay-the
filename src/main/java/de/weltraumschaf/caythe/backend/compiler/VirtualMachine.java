package de.weltraumschaf.caythe.backend.compiler;

import de.weltraumschaf.caythe.backend.env.Environment;
import de.weltraumschaf.commons.validate.Validate;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * The VM executes byte code.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class VirtualMachine {

    /**
     * Holds the register.
     */
    private final Register reg = new Register();
    /**
     * Holds copy of executed byte code.
     */
    private final List<Byte> byteCode = new ArrayList<>();
    /**
     * USed for I/O.
     */
    private final Environment env;
    /**
     * Point where the execution is at the moment in {@link #byteCode}.
     */
    private int instructionPointer;

    /**
     * Dedicated constructor.
     *
     * @param env must not be {@code null}
     */
    public VirtualMachine(final Environment env) {
        super();
        this.env = Validate.notNull(env, "env");
    }

    /**
     * Runs a program.
     *
     * @param prog must not be {@code null}
     */
    public void run(final Program prog) {
        Validate.notNull(prog, "prog");
        init();
        byteCode.addAll(prog.getByteCode());

        while (hasNext()) {
            final Opcodes opcode = Opcodes.getFor(current());
            next();
            execute(opcode);
        }
    }

    /**
     * Initializes the VM to run a program.
     */
    private void init() {
        byteCode.clear();
        instructionPointer = 0;
    }

    /**
     * Return the current byte to execute.
     *
     * @return any byte
     */
    private byte current() {
        return byteCode.get(instructionPointer);
    }

    /**
     * Whether there are more bytes to execute.
     *
     * @return {@code true} if there are more bytes, else {@code false}
     */
    private boolean hasNext() {
        return instructionPointer < byteCode.size();
    }

    /**
     * Increment the instruction pointer.
     */
    private void next() {
        ++instructionPointer;
    }

    /**
     * Executes a given opcode.
     *
     * @param opcode must not be {@code null}
     */
    private void execute(final Opcodes opcode) {
        Validate.notNull(opcode, "opcode");

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
        env.stdOut(String.valueOf(reg.get(targetRegister)));
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

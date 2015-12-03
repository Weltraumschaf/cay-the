
package de.weltraumschaf.caythe;

import static org.hamcrest.Matchers.is;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for {@link VirtualMachine}.
 */
public class VirtualMachineTest {

    @Test
    public void run() {
        final Programm programm = new Programm();
        programm.add(Opcodes.INT_STORE);
        programm.add((byte)0x01);
        programm.add(11);
        programm.add(Opcodes.INT_STORE);
        programm.add((byte)0x02);
        programm.add(22);
        programm.add(Opcodes.INT_ADD);
        programm.add((byte)0x01);
        programm.add((byte)0x02);
        programm.add((byte)0x03);
        programm.add(Opcodes.PRINT);
        programm.add((byte)0x03);
        final VirtualMachine sut = new VirtualMachine(programm);

        sut.run();

        assertThat(sut.stdOut(), is("33"));
    }

}
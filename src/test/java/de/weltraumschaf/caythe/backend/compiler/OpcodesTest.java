
package de.weltraumschaf.caythe.backend.compiler;

import de.weltraumschaf.caythe.backend.compiler.Opcodes;
import static org.hamcrest.Matchers.is;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 */
public class OpcodesTest {

    @Test
    public void toString_containsNameAndByte() {
        assertThat(Opcodes.NOP.toString(), is("NOP(0x00)"));
        assertThat(Opcodes.INT_ADD.toString(), is("INT_ADD(0x01)"));
    }

}
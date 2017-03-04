package de.weltraumschaf.caythe.backend.vm;

import de.weltraumschaf.commons.testing.CapturingPrintStream;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public final class VirtualMachineTest {

    private static final int MAIN_ADDRESS = 0;
    private static final FunctionMetaData[] FACTORIAL_METADATA = {
        // .def main: ARGS=0, LOCALS=0 ADDRESS
        new FunctionMetaData("main", 0, 0, MAIN_ADDRESS),
    };

    @Test
    public void run() throws IOException, URISyntaxException {
        final CapturingPrintStream out = new CapturingPrintStream();
        final Path source = Paths.get(getClass().getResource("/de/weltraumschaf/caythe/backend/vm/hello.ctasm").toURI());
        final VirtualMachine vm = new VirtualMachine(
            new Assembler().assemble(source),
            0,
            FACTORIAL_METADATA,
            out);

        vm.run(0);

        assertThat(out.getCapturedOutput(), is("44"));
    }
}
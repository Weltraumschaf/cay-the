package de.weltraumschaf.caythe.backend.vm;

import de.weltraumschaf.commons.testing.CapturingPrintStream;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public final class VirtualMachineTest {

    private final CapturingPrintStream out = new CapturingPrintStream();
    private final Assembler asm = new Assembler();

    public VirtualMachineTest() throws UnsupportedEncodingException {
        super();
    }

    @Before
    public void setUp() throws Exception {
        out.reset();
    }

    private byte[] source(final String file) throws URISyntaxException, IOException {
        final Path source = Paths.get(getClass().getResource("/de/weltraumschaf/caythe/backend/vm/" + file).toURI());
        return asm.assemble(source);
    }

    @Test
    public void run_hello() throws IOException, URISyntaxException {
        final FunctionMetaData[] main = {
            new FunctionMetaData("main", 0, 0, 0),
        };
        final VirtualMachine sut = new VirtualMachine(
            source("hello.ctasm"),
            main,
            out);

        sut.run(0);

        assertThat(out.getCapturedOutput(), is("44"));
    }

    @Test
    public void run_loop() throws URISyntaxException, IOException {
        final FunctionMetaData[] main = {
            new FunctionMetaData("main", 0, 0, 0),
        };
        final VirtualMachine sut = new VirtualMachine(
            source("loop.ctasm"),
            main,
            out);

        sut.run(0);

        assertThat(out.getCapturedOutput(), is("10"));
    }

}
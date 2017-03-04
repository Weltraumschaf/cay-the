package de.weltraumschaf.caythe.backend.vm;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static de.weltraumschaf.caythe.backend.vm.ByteCode.*;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class AssemblerTest {

    private final Assembler sut = new Assembler();

    @Test
    public void assemble_empty() {
        assertThat(sut.assemble(""), is(new byte[0]));
        assertThat(sut.assemble("      "), is(new byte[0]));
        assertThat(sut.assemble("   \n     \n    "), is(new byte[0]));
        assertThat(sut.assemble("   \n //comment\n    \n    "), is(new byte[0]));
        assertThat(sut.assemble("   \n // more comment\n    \n    "), is(new byte[0]));
    }

    @Test
    public void assemble_stripTrailingComment() {
        final String source =
            "iconst 42 // Push constant onto stack.\n" +
            "halt // Halt the programm.\n";
        final byte[] expected = {
            ICONST.getOpcode(), 0, 0, 0, 0, 0, 0, 0, 0x2A, // 42
            HALT.getOpcode()};

        assertThat(sut.assemble(source), is(expected));
    }

    @Test
    public void hello() throws URISyntaxException, IOException {
        final Path source = Paths.get(getClass().getResource("/de/weltraumschaf/caythe/backend/vm/hello.ctasm").toURI());
        final byte[] expected = {
            ICONST.getOpcode(), 0, 0, 0, 0, 0, 0, 0, 0x2A, // 42
            ICONST.getOpcode(), 0, 0, 0, 0, 0, 0, 0, 0x02, // 2,
            IADD.getOpcode(),
            PRINT.getOpcode(),
            HALT.getOpcode()};

        assertThat(sut.assemble(source), is(expected));
    }

    @Test
    public void loop() throws URISyntaxException, IOException {
        final Path source = Paths.get(getClass().getResource("/de/weltraumschaf/caythe/backend/vm/loop.ctasm").toURI());
        final byte[] expected = {
            0x09, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x0A, 0x13, 0x00, 0x00, 0x00, 0x00, 0x09, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x13,
            0x00, 0x00, 0x00, 0x01, 0x11, 0x00, 0x00, 0x00,
            0x01, 0x11, 0x00, 0x00, 0x00, 0x00, 0x04, 0x08,
            0x00, 0x00, 0x00, 0x45, 0x11, 0x00, 0x00, 0x00,
            0x01, 0x09, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x01, 0x01, 0x13, 0x00, 0x00, 0x00, 0x01,
            0x06, 0x00, 0x00, 0x00, 0x1C, 0x11, 0x00, 0x00,
            0x00, 0x01, 0x14, 0x18
        };

        assertThat(sut.assemble(source), is(expected));
    }
}
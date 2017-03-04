package de.weltraumschaf.caythe.backend.vm;

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
            ICONST, 0, 0, 0, 0, 0, 0, 0, 0x2A, // 42
            HALT};

        assertThat(sut.assemble(source), is(expected));
    }

    @Test
    public void hello() throws URISyntaxException, IOException {
        final Path source = Paths.get(getClass().getResource("/de/weltraumschaf/caythe/backend/vm/hello.ctasm").toURI());
        final byte[] expected = {
            ICONST, 0, 0, 0, 0, 0, 0, 0, 0x2A, // 42
            ICONST, 0, 0, 0, 0, 0, 0, 0, 0x02, // 2,
            IADD,
            PRINT,
            HALT};

        assertThat(sut.assemble(source), is(expected));
    }
}
package de.weltraumschaf.caythe.intermediate.file;

import de.weltraumschaf.caythe.intermediate.model.Module;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.DataFormatException;

import static de.weltraumschaf.caythe.intermediate.file.SharedFixtures.MODULE_FIXTURE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link ModuleFile}.
 */
public class ModuleFileTest {

    @Rule
    public final TemporaryFolder tmp = new TemporaryFolder();
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private final ModuleFile sut = new ModuleFile();

    @Test
    public void read_sourceIsNull() throws IOException, DataFormatException {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("source");

        sut.read(null);
    }

    @Test
    public void write_moduleIsNull() throws IOException {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("module");

        sut.write(null, tmp.getRoot().toPath());
    }

    @Test
    public void write_targetIsNull() throws IOException {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("target");

        sut.write(MODULE_FIXTURE, null);
    }

    @Test
    public void writeAndRead() throws IOException, DataFormatException {
        final Path module = tmp.getRoot().toPath().resolve("module");

        sut.write(MODULE_FIXTURE, module);

        assertThat(Files.exists(module), is(true));

        Module read = sut.read(module);
        assertThat(read, is(MODULE_FIXTURE));
    }
}

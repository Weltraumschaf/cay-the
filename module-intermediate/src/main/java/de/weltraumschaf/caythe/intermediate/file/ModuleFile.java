package de.weltraumschaf.caythe.intermediate.file;

import de.weltraumschaf.caythe.intermediate.model.Module;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.DataFormatException;

/**
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class ModuleFile {

    private final Compressor compressor = new Compressor();
    private final Serializer serializer = new Serializer();

    public Module read(final Path source) throws IOException, DataFormatException {
        final byte[] compressed = Files.readAllBytes(source);
        final byte[] decompressed = compressor.decompress(compressed);
        return serializer.deserialize(decompressed);
    }

    public void write(final Module module, final Path target) throws IOException {
        final byte[] decompressed = serializer.serialize(module);
        final byte[] compressed = compressor.compress(decompressed);
        Files.write(target, compressed);
    }
}

package de.weltraumschaf.caythe.intermediate.file;

import de.weltraumschaf.caythe.intermediate.model.Module;
import de.weltraumschaf.commons.validate.Validate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.DataFormatException;

/**
 * This class persists a whole {@link Module semantic model} int o a file or restores it from one.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class ModuleFile {

    private static final String ENCODING = "utf-8";
    private final Compressor compressor = new Compressor();
    private final Serializer serializer = new Serializer();

    /**
     * Read a module from file.
     * 
     * @param source
     *        must not be {@code null}
     * @return never {@code null}
     * @throws IOException
     *         if file can't be read
     * @throws DataFormatException
     *         if it can not be decompressed
     */
    public Module read(final Path source) throws IOException, DataFormatException {
        Validate.notNull(source, "source");
        final byte[] decompressed = compressor.decompress(Files.readAllBytes(source));
        return serializer.deserialize(new String(decompressed, ENCODING));
    }

    /**
     * Write a module to a file.
     * 
     * @param module
     *        must not be {@code null}
     * @param target
     *        must not be {@code null}
     * @throws IOException
     *         if file can't be written
     */
    public void write(final Module module, final Path target) throws IOException {
        Validate.notNull(module, "module");
        Validate.notNull(target, "target");
        final byte[] decompressed = serializer.serialize(module).getBytes(ENCODING);
        Files.write(target, compressor.compress(decompressed));
    }
}

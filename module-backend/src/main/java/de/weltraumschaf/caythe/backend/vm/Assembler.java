package de.weltraumschaf.caythe.backend.vm;

import de.weltraumschaf.commons.validate.Validate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static de.weltraumschaf.caythe.backend.vm.ByteCode.*;

/**
 * Generates byte code from assembly like text.
 */
public final class Assembler {

    private static final String COMMENT_PREFIX = "//";
    private static final Function<String, String> REMOVE_TRAILING_COMMENT = line -> {
        final int i = line.indexOf(COMMENT_PREFIX);
        return i > -1 ? line.substring(0, i) : line;
    };
    private static final Predicate<String> SKIP_LINE_COMMENT = line -> !line.startsWith(COMMENT_PREFIX);
    private static final Predicate<String> SKIP_EMPTY_LINES = line -> !line.isEmpty();
    private final ByteFormatter formatter = new ByteFormatter();

    byte[] assemble(final String source) {
        Validate.notNull(source, "source");
        return assemble(Arrays.asList(source.split("\n")));
    }

    public byte[] assemble(final Path source) throws IOException {
        Validate.notNull(source, "source");
        return assemble(Files.readAllLines(source, StandardCharsets.UTF_8));
    }

    private byte[] assemble(final Collection<String> source) {
        if (source == null || source.isEmpty()) {
            return new byte[0];
        }

        final List<Byte> buffer = new ArrayList<>(source.size());
        source.stream()
            .map(String::trim)
            .filter(SKIP_EMPTY_LINES)
            .filter(SKIP_LINE_COMMENT)
            .map(REMOVE_TRAILING_COMMENT)
            .forEach(line -> compile(line, buffer));

        final byte[] code = new byte[buffer.size()];

        for (int i = 0; i < buffer.size(); ++i) {
            code[i] = buffer.get(i);
        }

        return code;
    }

    private void compile(final String line, final List<Byte> buffer) {
        final String[] parts = line.split("\\s+");
        final Byte opcode = ByteCode.MNEMONIC_TO_CODE.get(parts[0]);
        buffer.add(opcode);

        final int expectedArgsCount;

        if (ByteCode.ONE_ARGUMENTS.contains(opcode)) {
            expectedArgsCount = 1;
        } else {
            expectedArgsCount = 0;
        }

        if (parts.length - 1 == expectedArgsCount) {
            switch (opcode) {
                case ICONST:
                    for (final byte b : formatter.fromLong(Long.valueOf(parts[1]))) {
                        buffer.add(b);
                    }
                    break;
                case BR:
                case BRT:
                case BRF:
                case LOAD:
                case GLOAD:
                case STORE:
                case GSTORE:
                    for (final byte b : formatter.fromInt(Integer.valueOf(parts[1]))) {
                        buffer.add(b);
                    }
                    break;
            }
        } else {
            throw new IllegalArgumentException(String.format(
                "Argument count for opcode %s mismatch! Expected argument count is %d, but %d arguments were given.",
                parts[0], expectedArgsCount, parts.length - 1));
        }
    }
}

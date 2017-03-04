package de.weltraumschaf.caythe.backend.vm;

import de.weltraumschaf.commons.validate.Validate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

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
    private final Map<String, Integer> globals = new HashMap<>();
    private final Map<String, Label> labels = new HashMap<>();
    private int currentBytePosition;

    byte[] assemble(final String source) {
        Validate.notNull(source, "source");
        return assemble(Arrays.asList(source.split("\n")));
    }

    public byte[] assemble(final Path source) throws IOException {
        Validate.notNull(source, "source");
        return assemble(Files.readAllLines(source, StandardCharsets.UTF_8));
    }

    private byte[] assemble(final Collection<String> source) {
        currentBytePosition = 0;

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

        // Replace the empty label offsets.
        labels.values().forEach(label -> {
            label.offsets.forEach(offset -> {
                final byte[] pos = formatter.fromInt(label.position);

                for (int i = 0; i < pos.length; ++i) {
                    buffer.set(i + offset, pos[i]);
                }
            });
        });

        final byte[] code = new byte[buffer.size()];

        for (int i = 0; i < buffer.size(); ++i) {
            code[i] = buffer.get(i);
        }

        return code;
    }

    private void compile(final String line, final List<Byte> buffer) {
        if (line.startsWith(".")) {
            if (!labels.containsKey(line)) {
                labels.put(line, new Label(line));
            }

            labels.get(line).position = currentBytePosition;
            return;
        }

        final String[] parts = line.split("\\s+");
        final String opcode = parts[0];
        final ByteCode byteCode = ByteCode.MNEMONIC_TO_CODE.get(opcode);
        buffer.add(byteCode.getOpcode());
        currentBytePosition++;

        if (byteCode.getNumberOfArguments() == 0) {
            return;
        }

        if (parts.length - 1 == byteCode.getNumberOfArguments()) {
            final String firstArgument = parts[1];

            switch (byteCode) {
                case ICONST:
                    compileLong(Long.valueOf(firstArgument), buffer);
                    break;
                case BR:
                case BRT:
                case BRF:
                    if (!labels.containsKey(firstArgument)) {
                        labels.put(firstArgument, new Label(firstArgument));
                    }

                    final Label label = labels.get(firstArgument);

                    if (label.hasPosition()) {
                        compileInt(label.position, buffer);
                    } else {
                        label.addOffset(currentBytePosition);
                        compileInt(0, buffer);
                    }
                    break;
                case LOAD:
                case STORE:
                    compileInt(Integer.valueOf(firstArgument), buffer);
                    break;
                case GLOAD:
                    if (!globals.containsKey(firstArgument)) {
                        throw new IllegalStateException("No such global register name: " + firstArgument);
                    }

                    compileInt(globals.get(firstArgument), buffer);
                    break;
                case GSTORE:
                    if (!globals.containsKey(firstArgument)) {
                        globals.put(firstArgument, globals.size());
                    }

                    compileInt(globals.get(firstArgument), buffer);
            }
        } else {
            throw new IllegalArgumentException(String.format(
                "Argument count for opcode %s mismatch! Expected argument count is %d, but %d arguments were given.",
                byteCode, byteCode.getNumberOfArguments(), parts.length - 1));
        }
    }

    private void compileLong(final long value, final List<Byte> buffer) {
        for (final byte b : formatter.fromLong(value)) {
            buffer.add(b);
            currentBytePosition++;
        }
    }

    private void compileInt(final int value, final List<Byte> buffer) {
        for (final byte b : formatter.fromInt(value)) {
            buffer.add(b);
            currentBytePosition++;
        }
    }

    private static final class Label {
        private final Collection<Integer> offsets = new ArrayList<>();
        private final String name;
        private int position = -1;

        Label(final String name) {
            this.name = name;
        }

        boolean hasPosition() {
            return position != -1;
        }

        void addOffset(final int currentBytePosition) {
            offsets.add(currentBytePosition);
        }
    }
}

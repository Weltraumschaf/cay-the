package de.weltraumschaf.caythe.intermediate;

import java.util.Objects;

public final class Position {
    private static final String UNKNOWN_FILE = "n/a";
    public static final Position UNKNOWN = new Position(0, 0);
    private final String file;
    private final int line;
    private final int character;

    public Position(final int line, final int character) {
        this(UNKNOWN_FILE, line, character);
    }

    Position(final String file, final int line, final int character) {
        super();
        this.file = file;
        this.line = line;
        this.character = character;
    }

    String getFile() {
        return file;
    }

    public int getLine() {
        return line;
    }

    public int getCharacter() {
        return character;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Position)) {
            return false;
        }

        final Position position = (Position) o;
        return line == position.line &&
            character == position.character &&
            Objects.equals(file, position.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, line, character);
    }

    @Override
    public String toString() {
        return "Position{" +
            "file='" + file + '\'' +
            ", line=" + line +
            ", character=" + character +
            '}';
    }
}

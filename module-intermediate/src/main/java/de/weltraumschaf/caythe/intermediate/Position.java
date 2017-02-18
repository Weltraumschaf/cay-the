package de.weltraumschaf.caythe.intermediate;

import java.util.Objects;

public final class Position implements Serializable{
    private static final String UNKNOWN_FILE = "n/a";
    public static final Position UNKNOWN = new Position(0, 0);
    private final String file;
    private final int line;
    private final int column;

    public Position(final int line, final int column) {
        this(UNKNOWN_FILE, line, column);
    }

    Position(final String file, final int line, final int column) {
        super();
        this.file = file;
        this.line = line;
        this.column = column;
    }

    String getFile() {
        return file;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Position)) {
            return false;
        }

        final Position position = (Position) o;
        return line == position.line &&
            column == position.column &&
            Objects.equals(file, position.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, line, column);
    }

    @Override
    public String toString() {
        return "Position{" +
            "file='" + file + '\'' +
            ", line=" + line +
            ", column=" + column +
            '}';
    }

    @Override
    public String serialize() {
        return String.format("[%d:%d]", line, column);
    }
}

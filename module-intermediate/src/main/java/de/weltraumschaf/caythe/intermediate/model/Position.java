package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.caythe.StringUtil;
import de.weltraumschaf.commons.validate.Validate;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * The position in the source from where an intermediate object origins.
 * <p>
 * By default {@link #getLine() line} and {@link #getColumn() column} starts by one. If one of them is zero it means that
 * it is unknown.
 * </p>
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
@ToString(exclude = {"file"})
public final class Position implements IntermediateModel, Serializable {
    /**
     * Constant for unknown position.
     * <p>
     * Use this instead of {@code null}.
     * </p>
     */
    public static final Position UNKNOWN = new Position(0, 0);
    private static final String UNKNOWN_FILE = "n/a";
    @Getter
    private final String file;
    @Getter
    private final int line;
    @Getter
    private final int column;

    /**
     * Convenience constructor for positions without file.
     *
     * @param line   must not be negative
     * @param column must not be negative
     */
    public Position(final int line, final int column) {
        this(UNKNOWN_FILE, line, column);
    }

    /**
     * Dedicated constructor.
     *
     * @param file   must not be {@code null} nor empty
     * @param line   must not be negative
     * @param column must not be negative
     */
    public Position(final String file, final int line, final int column) {
        super();
        this.file = Validate.notEmpty(file, "file");
        this.line = Validate.greaterThanOrEqual(line, 0, "line");
        this.column = Validate.greaterThanOrEqual(column, 0, "column");
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
    public String serialize() {
        return String.format("[%d:%d]", line, column);
    }

    public String toStringWithFile() {
        return "Position(" +
            "file='" + StringUtil.shorten(file) + '\'' +
            ", line=" + line +
            ", column=" + column +
            ')';
    }
}

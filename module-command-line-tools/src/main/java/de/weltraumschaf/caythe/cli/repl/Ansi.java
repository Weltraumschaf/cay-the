package de.weltraumschaf.caythe.cli.repl;

/**
 * Builder to format console output with <a href="https://en.wikipedia.org/wiki/ANSI_escape_code">ANSI escape codes</a>.
 *
 * @author Sven Strittmatter
 */
public final class Ansi {

    //@CHECKSTYLE:OFF
    private static final int RESET = 0;
    private static final int INTENSITY_BOLD = 1;
    private static final int INTENSITY_FAINT = 2;
    private static final int ITALIC = 3;
    private static final int UNDERLINE = 4;
    private static final int BLINK_SLOW = 5;
    private static final int BLINK_FAST = 6;
    private static final int NEGATIVE_ON = 7;
    private static final int CONCEAL_ON = 8;
    private static final int STRIKETHROUGH_ON = 9;
    private static final int UNDERLINE_DOUBLE = 21;
    private static final int INTENSITY_BOLD_OFF = 22;
    private static final int ITALIC_OFF = 23;
    private static final int UNDERLINE_OFF = 24;
    private static final int BLINK_OFF = 25;
    private static final int NEGATIVE_OFF = 27;
    private static final int CONCEAL_OFF = 28;
    private static final int STRIKETHROUGH_OFF = 29;
    //@CHECKSTYLE:ON

    /**
     * Escape sequence format.
     */
    private static final String ESCAPE_SEQUENCE = "\u001B[%dm";
    /**
     * Add this to color code for foreground.
     */
    private static final int COLOR_FG = 30;
    /**
     * Add this to color code for bright foreground.
     */
    private static final int COLOR_FG_BRIGHT = 90;
    /**
     * Add this to color code for background.
     */
    private static final int COLOR_BG = 40;
    /**
     * Add this to color code for bright background.
     */
    private static final int COLOR_BG_BRIGHT = 100;

    /**
     * Buffers the formatted string.
     */
    private final StringBuilder buffer = new StringBuilder();

    /**
     * Use {@link #fmt()}}.
     */
    private Ansi() {
        super();
    }

    /**
     * Factory method to fmt new builder.
     *
     * @return never {@code null}, always new instance
     */
    public static Ansi fmt() {
        return new Ansi();
    }

    /**
     * Reset all previous formatting.
     *
     * @return self for chaining
     */
    public Ansi reset() {
        buffer.append(ansi(RESET));
        return this;
    }

    /**
     * Start bold formatted text.
     *
     * @return self for chaining
     */
    public Ansi bold() {
        buffer.append(ansi(INTENSITY_BOLD));
        return this;
    }

    /**
     * Start italic formatted text.
     *
     * @return self for chaining
     */
    public Ansi italic() {
        buffer.append(ansi(ITALIC));
        return this;
    }

    /**
     * Start foreground color text.
     *
     * @return self for chaining
     */
    public Ansi fg(final Color color) {
        buffer.append(ansi(color.value + COLOR_FG));
        return this;
    }

    /**
     * Start bright foreground color text.
     *
     * @return self for chaining
     */
    public Ansi fgBright(final Color color) {
        buffer.append(ansi(color.value + COLOR_FG_BRIGHT));
        return this;
    }

    /**
     * Start background color text.
     *
     * @return self for chaining
     */
    public Ansi bg(final Color color) {
        buffer.append(ansi(color.value + COLOR_BG));
        return this;
    }

    /**
     * Start bright background color text.
     *
     * @return self for chaining
     */
    public Ansi bgBright(final Color color) {
        buffer.append(ansi(color.value + COLOR_BG_BRIGHT));
        return this;
    }

    /**
     * Add formatted text to buffer.
     *
     * @param text must not be {@code null}
     * @param args optional format arguments
     * @return self for chaining
     */
    public Ansi text(final String text, final Object... args) {
        buffer.append(String.format(text, args));
        return this;
    }

    /**
     * Add a new line to the buffer.
     *
     * @return self for chaining
     */
    public Ansi nl() {
        buffer.append('\n');
        return this;
    }

    /**
     * Formats the given code as ANSI escape sequence.
     *
     * @param code any number which is a valid ANSI code
     * @return never {@code null} or empty
     */
    private String ansi(final int code) {
        return String.format(ESCAPE_SEQUENCE, code);
    }

    @Override
    public String toString() {
        return buffer.toString();
    }

    /**
     * Available color codes.
     */
    public enum Color {
        /**
         * Escape sequence for black colored text.
         */
        BLACK(0),
        /**
         * Escape sequence for red colored text.
         */
        RED(1),
        /**
         * Escape sequence for green colored text.
         */
        GREEN(2),
        /**
         * Escape sequence for yellow colored text.
         */
        YELLOW(3),
        /**
         * Escape sequence for blue colored text.
         */
        BLUE(4),
        /**
         * Escape sequence for magenta colored text.
         */
        MAGENTA(5),
        /**
         * Escape sequence for cyan colored text.
         */
        CYAN(6),
        /**
         * Escape sequence for white colored text.
         */
        WHITE(7),
        /**
         * Escape sequence for default colored text.
         */
        DEFAULT(9);

        /**
         * The literal color code.
         */
        private final int value;

        /**
         * Dedicated constructor.
         *
         * @param value from 0 - 9
         */
        Color(final int value) {
            this.value = value;
        }
    }
}

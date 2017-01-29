package de.weltraumschaf.caythe.cli.repl;

import de.weltraumschaf.caythe.CayThe;
import de.weltraumschaf.caythe.frontend.CayTheSourceParser;
import de.weltraumschaf.caythe.backend.TreeWalkingInterpreter;
import de.weltraumschaf.caythe.frontend.Parsers;
import de.weltraumschaf.caythe.backend.experimental.types.NullType;
import de.weltraumschaf.caythe.backend.experimental.types.ObjectType;
import de.weltraumschaf.commons.application.IO;
import de.weltraumschaf.commons.application.Version;
import de.weltraumschaf.commons.validate.Validate;
import jline.console.ConsoleReader;
import jline.console.completer.Completer;
import jline.console.completer.StringsCompleter;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static jline.internal.Preconditions.checkNotNull;

final class Repl {
    /**
     * Greeting to the user.
     */
    private static final String WELCOME =
        "           Welcome to Cay-The REPL v%s            " + CayThe.NL;
    /**
     * Some initial help examples for beginners.
     */
    private static final String INITIAL_HELP = "Hello, World example:" + CayThe.NL;
    /**
     * The REPL prompt to signal that user input is expected.
     */
    private static final String NORMAL_PROMPT = "ct> ";
    private static final String CONTINUATION_PROMPT = "    ";
    private Parsers parsers = new Parsers();
    private TreeWalkingInterpreter interpreter = new TreeWalkingInterpreter();
    /**
     * Used to print version info.
     */
    private final Version version;
    private final IO io;
    /**
     * Flag to signal that the loop should be exited.
     */
    private boolean exit;
    private boolean debug;

    /**
     * Dedicated constructor.
     *
     * @param io      must not be {@code null}
     * @param version must not be {@code null}
     */
    Repl(final IO io, final Version version) {
        this.io = io;
        this.version = Validate.notNull(version, "version");
    }

    void debug(boolean debug) {
        this.debug = debug;
        interpreter.debug(debug);
        parsers.debug(debug);
    }

    void start() throws IOException {
        final ConsoleReader reader = createReader();
        welcome(version);
        final StringBuilder inputBuffer = new StringBuilder();

        while (true) {
            final String line = reader.readLine();

            if (line == null) {
                break; // EOF sent.
            }

            if (line.trim().isEmpty()) {
                continue; // Empty string sent.
            }

            if (line.endsWith("\\")) {
                // Continue with next line.
                inputBuffer.append(line.substring(0, line.length() - 1)).append(CayThe.NL);
                reader.setPrompt(CONTINUATION_PROMPT);
                continue;
            }

            if (Command.isCmd(line)) {
                execute(Command.getCmd(line));

                if (exit) {
                    io.println(Ansi.fmt().fg(Ansi.Color.BLUE).text("Bye bye :-)").reset().toString());
                    break;
                }

                continue;
            }

            try {
                inputBuffer.append(line).append(CayThe.NL);
                final CayTheSourceParser parser = parsers.newSourceParser(
                    new ByteArrayInputStream(inputBuffer.toString().getBytes(CayThe.DEFAULT_ENCODING)));
                final ObjectType result = interpreter.visit(parser.unit());

                if (result == NullType.NULL) {
                    continue;
                }

                io.println(Ansi.fmt().fg(Ansi.Color.GREEN).bold().text(result.inspect()).reset().toString());
            } catch (final RuntimeException e) {
                if (null != e.getMessage()) {
                    io.errorln(e.getMessage());
                }

                if (debug) {
                    e.printStackTrace(io.getStderr());
                }
            } finally {
                // Empty the buffer and reset the prompt.
                inputBuffer.setLength(0);
                reader.setPrompt(normalPrompt());
            }
        }
    }

    /**
     * Show a welcome message to the user.
     *
     * @param version must not be {@code null}
     * @throws IOException if figlet can't be read
     */
    private void welcome(final Version version) throws IOException {
        io.print(Ansi.fmt()
            .fg(Ansi.Color.BLUE).bold().text(figlet()).reset()
            .nl().nl()
            .fg(Ansi.Color.BLUE).italic().text(WELCOME, version).reset()
            .nl()
            .bold().text("  Type %s for help.", Command.HELP).reset()
            .nl().nl()
            .toString());
    }

    /**
     * Reads the figlet from file.
     *
     * @return never {@code null}
     * @throws IOException if figlet can't be read
     */
    private String figlet() throws IOException {
        final InputStream input = getClass().getResourceAsStream(CayThe.BASE_PACKAGE_DIR + "/cli/repl/figlet");

        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining(CayThe.NL));
        }
    }

    /**
     * Executes a REPL command.
     *
     * @param cmd if {@code null} a error message will be printed to the user.
     */
    private void execute(final Command cmd) {
        switch (cmd) {
            case CLEAR:
                interpreter.reset();
                io.println("Environment cleared.");
                break;
            case ENV:
                io.println(interpreter.environment().dump());
                break;
            case EXAMPLES:
                io.println(INITIAL_HELP);
                break;
            case EXIT:
                exit = true;
                break;
            case HELP:
                Command.printHelp(io.getStdout());
                break;
            default:
                io.error("Unknown command: '" + cmd + "'!");
                break;
        }
    }

    /**
     * Factory method to fmt the interactive console.
     *
     * @return never {@code null}, always new instance
     * @throws IOException if the I/O streams can't be written/read
     */
    private ConsoleReader createReader() throws IOException {
        // Disable this so we can use the bang (!) for our commands as prefix.
        System.setProperty("jline.expandevents", Boolean.FALSE.toString());
        final ConsoleReader reader = new ConsoleReader(io.getStdin(), io.getStdout());
        reader.setBellEnabled(false);
        reader.addCompleter(createCompletionHints());
        reader.setPrompt(normalPrompt());
        return reader;
    }

    private String normalPrompt() {
        return Ansi.fmt().bold().fg(Ansi.Color.BLUE).text(NORMAL_PROMPT).reset().toString();
    }

    /**
     * Create completion hints for the interactive console.
     *
     * @return never {@code null}, always new instance
     */
    private Completer createCompletionHints() {
        return new CommandEnumCompleter(Command.class);
    }

    /**
     * Special commands in the REPL.
     * <p>
     * These commands are not part of the language itself and are treated case sensitive.
     * </p>
     */
    private enum Command {
        /**
         * Clears the user defined symbols.
         */
        CLEAR("Clears the whole environment: Removes all defined symbols, but reloads built in and STD lib."),
        /**
         * Shows the allocated  memory.
         */
        ENV("Shows the environment from the current scope up to the root."),
        /**
         * Show some examples.
         */
        EXAMPLES("Show some example code snippets."),
        /**
         * Exits the REPL.
         */
        EXIT("Stops the REPL and exits."),
        /**
         * Shows help about the REPL commands.
         */
        HELP("Shows this help.");

        /**
         * Escape the command to distinguish them from usual syntax.
         */
        private static final char PREFIX = '!';
        /**
         * Lookup table to find the command enum by symbol.
         */
        private static final Map<String, Command> LOOKUP = new HashMap<>();

        static {
            Arrays.stream(Command.values()).forEach(c -> LOOKUP.put(c.toString(), c));
        }

        /**
         * Help message of the command.
         */
        private final String help;

        /**
         * Dedicated constructor.
         *
         * @param help must not be {@code null} or empty
         */
        Command(final String help) {
            this.help = Validate.notEmpty(help, "help");
        }

        @Override
        public String toString() {
            return PREFIX + name().toLowerCase();
        }

        /**
         * Whether a given string is a command.
         *
         * @param in the tested string
         * @return {@code true} if it is a command, else {@code false}
         */
        public static boolean isCmd(final String in) {
            return null != in && LOOKUP.containsKey(in.trim());

        }

        /**
         * Get the command enum for given string.
         * <p>
         * Use {@link #isCmd(String)} to check if there is a command for the given string.
         * </p>
         *
         * @param in must not be {@code null}
         * @return never {@code null}
         */
        public static Command getCmd(final String in) {
            return LOOKUP.get(Validate.notNull(in, "in").trim());
        }

        /**
         * Print he help for all commands to the given io stream.
         *
         * @param out must not be {@code null}
         */
        public static void printHelp(final PrintStream out) {
            Validate.notNull(out, "out");
            out.println("Available commands:");
            Arrays.stream(values()).forEach(c -> out.println(String.format("  %1$-10s", c.toString()) + c.help));
        }
    }

    /**
     * Provides tab completion for the REPL commands to the console reader.
     */
    private static final class CommandEnumCompleter extends StringsCompleter {
        /**
         * Dedicated constructor.
         *
         * @param source must not be {@code null}
         */
        CommandEnumCompleter(Class<? extends Enum> source) {
            checkNotNull(source);

            for (Enum<?> n : source.getEnumConstants()) {
                getStrings().add(n.toString().toLowerCase());
            }
        }
    }
}

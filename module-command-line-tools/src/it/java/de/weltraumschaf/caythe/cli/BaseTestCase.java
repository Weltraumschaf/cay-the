package de.weltraumschaf.caythe.cli;

import de.weltraumschaf.caythe.CayThe;
import de.weltraumschaf.commons.validate.Validate;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Base implementation for integration tests.
 *
 * <p>This provides functionality to:</p>
 * <ul>
 *     <li>create temp dir as working dir</li>
 *     <li>execute command</li>
 *     <li>capture stdout/stderr</li>
 * </ul>
 */
public abstract class BaseTestCase {
    @Rule
    public final TemporaryFolder tmp = new TemporaryFolder();

    /**
     * Build the matchers for STDOUT expectations.
     */
    private final CapturedOutputMatcherBuilder outMatcherBuilder = new CapturedOutputMatcherBuilder();
    /**
     * Build the matchers for STDERR expectations.
     */
    private final CapturedOutputMatcherBuilder errMatcherBuilder = new CapturedOutputMatcherBuilder();

    private final Properties properties = new Properties();
    private boolean propertiesLoaded;
    private int expectedExitCode;
    private String subCommandName = "";
    private Collection<String> arguments = new ArrayList<>();

    protected BaseTestCase() {
        super();
        loadEnvironment();
    }

    @Before
    public void reset() {
        subCommandName = "";
        expectedExitCode = 0;
        arguments.clear();
    }

    protected final BaseTestCase expectExitCode(final int exitCode) {
        expectedExitCode = exitCode;
        return this;
    }

    /**
     * Adds to the list of requirements for any output printed to STDOUT that it should <em>contain</em> string
     * {@code substring}.
     *
     * @param substring must not be {@code null}
     */
    protected final BaseTestCase expectOut(final String substring) {
        return expectOut(containsString(Validate.notNull(substring, "substring")));
    }

    /**
     * Adds to the list of requirements for any output printed to STDOUT.
     *
     * @param matcher must not be {@code null}
     */
    protected final BaseTestCase expectOut(final Matcher<String> matcher) {
        outMatcherBuilder.add(Validate.notNull(matcher, "matcher"));
        return this;
    }

    /**
     * Adds to the list of requirements for any output printed to STDERR that it should <em>contain</em> string
     * {@code substring}.
     *
     * @param substring must not be {@code null}
     */
    protected final BaseTestCase expectErr(final String substring) {
        return expectErr(containsString(Validate.notNull(substring, "substring")));
    }

    /**
     * Adds to the list of requirements for any output printed to STDERR.
     *
     * @param matcher must not be {@code null}
     */
    protected final BaseTestCase expectErr(final Matcher<String> matcher) {
        errMatcherBuilder.add(Validate.notNull(matcher, "matcher"));
        return this;
    }

    protected final BaseTestCase subcommand(final String subCommandName) {
        Validate.notEmpty(subCommandName, "subCommandName");
        this.subCommandName = subCommandName;
        return this;
    }

    protected final BaseTestCase argument(final String argument) {
        Validate.notEmpty(argument, "argument");
        arguments.add(argument);
        return this;
    }

    protected final void execute() throws IOException, InterruptedException {
        final String executable = properties.getProperty("executable");
        assertThat(executable, not(isEmptyOrNullString()));
        final Collection<String> strings = new ArrayList<>();
        strings.add(executable);

        if (!subCommandName.isEmpty()) {
            strings.add(subCommandName);
        }

        strings.addAll(arguments);
        final Process process = Runtime.getRuntime().exec(strings.stream().collect(Collectors.joining(" ")));
        final IoThreadHandler stdout = new IoThreadHandler(process.getInputStream());
        stdout.start();
        final IoThreadHandler stderr = new IoThreadHandler(process.getErrorStream());
        stderr.start();
        int exitCode = process.waitFor();
        stdout.join();
        stderr.join();

        assertThat(exitCode, is(expectedExitCode));
        assertCapturedOut(stdout);
        assertCapturedErr(stderr);
    }

    private void loadEnvironment() {
        if (!propertiesLoaded) {
            InputStream in = null;

            try {
                in = getClass().getResourceAsStream(CayThe.BASE_PACKAGE_DIR + "/cli/environment.properties");
                properties.load(in);
                in.close();
            } catch (IOException e) {
                throw new IOError(e.getCause());
            } finally {
                if (null != in) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // Ignore failed close.
                    }
                }
            }

            propertiesLoaded = true;
        }
    }

    /**
     * Applies matchers on captured error output if there are any matchers.
     *
     * @throws UnsupportedEncodingException if the platform encoding is not supported
     * @param stderr
     */
    private void assertCapturedErr(final IoThreadHandler stderr) throws UnsupportedEncodingException {
        if (errMatcherBuilder.expectsSomething()) {
            assertThat(stderr.getOutput(), errMatcherBuilder.build());
        }
    }

    /**
     * Applies matchers on captured standard output if there are any matchers.
     *
     * @throws UnsupportedEncodingException if the platform encoding is not supported
     * @param stdout
     */
    private void assertCapturedOut(final IoThreadHandler stdout) throws UnsupportedEncodingException {
        if (outMatcherBuilder.expectsSomething()) {
            assertThat(stdout.getOutput(), outMatcherBuilder.build());
        }
    }

    /**
     * Builds string matchers.
     */
    private static final class CapturedOutputMatcherBuilder {

        /**
         * Hold all matchers.
         */
        private final List<Matcher<String>> matchers = new ArrayList<>();

        /**
         * Adds a matcher.
         *
         * @param matcher must not be {@code null}
         */
        void add(final Matcher<String> matcher) {
            matchers.add(matcher);
        }

        /**
         * Whether the builder has any matcher.
         *
         * @return {@code true} if there are matchers, else {@code false}
         */
        boolean expectsSomething() {
            return !matchers.isEmpty();
        }

        /**
         * Returns the combined matcher.
         *
         * @return never {@code null}
         */
        Matcher<String> build() {
            if (matchers.size() == 1) {
                return matchers.get(0);
            }

            return allOf(castedMatchers());
        }

        /**
         * Casts list to matchers of super type string.
         *
         * @return new instance, not {@code null}
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private List<Matcher<? super String>> castedMatchers() {
            return new ArrayList<>((List) matchers);
        }

    }

    /**
     * Reads the output from the process in separate thread.
     */
    private static final class IoThreadHandler extends Thread {

        /**
         * Input to read from.
         */
        private final InputStream input;
        /**
         * Collects the read input.
         */
        private final StringBuilder buffer = new StringBuilder();

        /**
         * Dedicated constructor.
         *
         * @param input must not be {@code null}
         */
        IoThreadHandler(final InputStream input) {
            super();
            this.input = Validate.notNull(input, "input");
        }

        @Override
        public void run() {
            try (final Scanner br = new Scanner(new InputStreamReader(input))) {
                while (br.hasNextLine()) {
                    buffer.append(br.nextLine());

                    if (br.hasNextLine()) {
                        buffer.append('\n');
                    }
                }
            }
        }

        /**
         * Get the collected output.
         *
         * @return never {@code null}
         */
        String getOutput() {
            return buffer.toString();
        }
    }

}

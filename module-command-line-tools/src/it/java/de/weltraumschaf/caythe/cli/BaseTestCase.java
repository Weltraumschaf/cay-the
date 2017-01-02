package de.weltraumschaf.caythe.cli;

import de.weltraumschaf.caythe.CayThe;
import de.weltraumschaf.commons.testing.rules.CapturedOutput;
import de.weltraumschaf.commons.validate.Validate;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Rule.
 * <p>
 * - create temp dir as working dir
 * - execute command
 * - capture stdout/stderr
 */
public abstract class BaseTestCase {
    @Rule
    public final TemporaryFolder tmp = new TemporaryFolder();
    @Rule
    public final CapturedOutput output = new CapturedOutput();

    private final Properties properties = new Properties();
    private boolean propertiesLoaded;
    private String subCommandName = "";
    private Collection<String> arguments = new ArrayList<>();

    protected BaseTestCase() {
        super();
        loadEnvironment();
    }

    @Before
    public void reset() {
        subCommandName = "";
        arguments.clear();
    }

    protected final BaseTestCase expectOut(final String substring) {
        output.expectOut(substring);
        return this;
    }

    protected final BaseTestCase expectOut(final Matcher<String> matcher) {
        output.expectOut(matcher);
        return this;
    }

    protected final BaseTestCase expectErr(final String substring) {
        output.expectErr(substring);
        return this;
    }

    protected final BaseTestCase expectErr(final Matcher<String> matcher) {
        output.expectErr(matcher);
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

    protected final void execute() throws IOException {
        final String executable = properties.getProperty("executable");
        final Collection<String> strings = Arrays.asList(executable, subCommandName);
        strings.addAll(arguments);
        final Process process = Runtime.getRuntime().exec(strings.stream().collect(Collectors.joining(" ")));
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

}

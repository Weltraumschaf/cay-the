package de.weltraumschaf.caythe.cli;

import de.weltraumschaf.commons.testing.rules.CapturedOutput;
import de.weltraumschaf.commons.validate.Validate;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Rule.
 *
 * - create temp dir as working dir
 * - execute command
 * - capture stdout/stderr
 */
public abstract class BaseTestCase {
    @Rule
    public final TemporaryFolder tmp = new TemporaryFolder();
    @Rule
    public final CapturedOutput output = new CapturedOutput();
    private String subCommandName = "";
    private Collection<String> arguments = new ArrayList<>();

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

    protected final void execute() {
    }
}

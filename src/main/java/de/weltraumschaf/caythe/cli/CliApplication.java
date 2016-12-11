package de.weltraumschaf.caythe.cli;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.weltraumschaf.caythe.CayThe;
import de.weltraumschaf.caythe.core.InterpreterModule;
import de.weltraumschaf.commons.application.InvokableAdapter;
import de.weltraumschaf.commons.application.Version;
import java.io.IOException;

/**
 * This class provides the {@link #main(java.lang.String[]) main entry point} for the command line application.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class CliApplication extends InvokableAdapter {

    /**
     * Version information.
     */
    private final Version version = new Version(CayThe.BASE_PACKAGE_DIR + "/version.properties");
    /**
     * Dependency injection configuration.
     */
    private final InterpreterModule module = new InterpreterModule();
    /**
     * Used to create objects with injected dependencies.
     */
    private final Injector injector = Guice.createInjector(module);

    /**
     * Dedicated constructor.
     *
     * @param args must not be {@code null}
     */
    public CliApplication(final String[] args) {
        super(args);
    }

    /**
     * Invoked from JVM.
     *
     * @param args CLI arguments
     */
    public static void main(final String[] args) {
        final CliApplication app = new CliApplication(args);
        InvokableAdapter.main(app);
    }

    /**
     * Do initial stuff.
     *
     * @return never {@code null}
     * @throws IOException if version file can not be loaded
     */
    private CliOptions setup() throws IOException {
        version.load();
        module.setIoStreams(getIoStreams());
        return CliOptions.gatherOptions(getArgs());
    }

    @Override
    public void execute() throws Exception {
        final CliOptions options = setup();

        if (options.isVersion()) {
            showVersion();
            return;
        }

        if (options.isHelp()) {
            showHelp();
            return;
        }

        getIoStreams().println("Hello, world!");
    }

    /**
     * Show help message.
     */
    private void showHelp() {
        getIoStreams().println(CliOptions.helpMessage());
    }

    /**
     * Show version message.
     */
    private void showVersion() {
        getIoStreams().println(version.getVersion());
    }

}

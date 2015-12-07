package de.weltraumschaf.caythe.cli;

import de.weltraumschaf.caythe.backend.ByteCodeVisitor;
import de.weltraumschaf.caythe.backend.DefaultEnvironmnet;
import de.weltraumschaf.caythe.backend.Program;
import de.weltraumschaf.caythe.backend.VirtualMachine;
import de.weltraumschaf.caythe.frontend.CayTheParser;
import de.weltraumschaf.caythe.frontend.Parsers;
import de.weltraumschaf.commons.application.InvokableAdapter;
import de.weltraumschaf.commons.application.Version;
import java.io.IOException;

/**
 * This class provides the {@link #main(java.lang.String[]) main entry point} for the command line application.
 *
 * @since 1.0.0
 */
public final class CliApplication extends InvokableAdapter {

    /**
     * Version information.
     */
    private final Version version;
    private final boolean debugEnabled = Boolean.valueOf(System.getProperty("CAYTHE_DEBUG", "false"));

    public CliApplication(final String[] args) {
        super(args);
        version = new Version("/de/weltraumschaf/caythe/version.properties");
    }

    public static void main(final String[] args) {
        InvokableAdapter.main(new CliApplication(args));
    }

    @Override
    public void execute() throws Exception {
        version.load();
        final CliOptions options = CliOptions.gatherOptions(getArgs());

        if (options.isVersion()) {
            showVersion();
            return;
        }

        if (options.isHelp()) {
            showHelp();
            return;
        }

        final VirtualMachine vm = new VirtualMachine(new DefaultEnvironmnet(getIoStreams()));
        vm.run(parse(options.getFile()));
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

    private Program parse(final String file) throws IOException {
        final CayTheParser parser = Parsers.newParser(file, debugEnabled);

        return new ByteCodeVisitor().visit(parser.compilationUnit());
    }
}

/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
package de.weltraumschaf.caythe;

import de.weltraumschaf.commons.application.IOStreams;
import de.weltraumschaf.commons.application.InvokableAdapter;
import de.weltraumschaf.commons.application.Version;
import de.weltraumschaf.commons.jcommander.JCommanderImproved;
import de.weltraumschaf.commons.system.Environments;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Main class invoked by the JVM.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class App extends InvokableAdapter {

    /**
     * Log facility.
     */
    private static final Logger LOG = Logger.getLogger(App.class);
    /**
     * Version information.
     */
    private final Version version;
    /**
     * To obtain environment variables.
     */
    private final Environments.Env env = Environments.defaultEnv();
    /**
     * Provides CLI options.
     */
    private final JCommanderImproved<Options> options
            = new JCommanderImproved<Options>(Constants.COMMAND_NAME.toString(), Options.class);

    /**
     * Dedicated constructor.
     *
     * @param args must not be {@code null}
     */
    public App(final String[] args) {
        super(args);
        version = new Version(Constants.PACKAGE_BASE.toString() + "/version.properties");
    }

    /**
     * Main entry point of VM.
     *
     * @param args cli arguments from VM
     */
    public static void main(final String[] args) {
        final App invokable = new App(args);

        try {
            InvokableAdapter.main(invokable, IOStreams.newDefault(), invokable.isDebug());
        } catch (final UnsupportedEncodingException ex) {
            // CHECKSTYLE:OFF
            // At this point we do not have IO streams.
            System.err.println("Can't create IO streams!");
            System.err.println(ex.getMessage());
            ex.printStackTrace(System.err);
            // CHECKSTYLE:ON
            invokable.exit(-1);
        }
    }

    /**
     * Determine if debug is enabled by environment variable {@link Constants#ENVIRONMENT_VARIABLE_DEBUG}.
     *
     * @return by default {@code false} if environment is not present or false
     */
    boolean isDebug() {
        final String debug = env.get(Constants.ENVIRONMENT_VARIABLE_DEBUG.toString());
        return "true".equalsIgnoreCase(debug.trim());
    }

    @Override
    public void execute() throws Exception {
        if (isDebug()) {
            LogManager.getRootLogger().setLevel(Level.DEBUG);
        }

        registerShutdownHook(new Callable<Void>() {
            @Override
            public Void call() throws IOException {
                return null;
            }
        });

        version.load();
        final Options opts = gatherOptions();

        if (opts.isHelp()) {
            getIoStreams().print(helpMessage());
            return;
        }

        if (opts.isVersion()) {
            getIoStreams().println(version.toString());
            return;
        }

        parseSourcesInCurrentworkingdir(opts);
    }

    private void parseSourcesInCurrentworkingdir(final Options opts) throws IOException {
        final Path currentWorkingdir = Paths.get("");
        final SourceImporter importer = new SourceImporter(
                currentWorkingdir.toAbsolutePath(),
                Constants.DEFAULT_ENCODING.toString());

        for (final String libDir : opts.getLibDirs()) {
            importer.addLibDir(Paths.get(libDir).toAbsolutePath().normalize());
        }

        getIoStreams().println(String.format(
                "Searching for files to compile in '%s' ...",
                importer.getBaseDir().toString()));

        final SourceProcessor processor = new SourceProcessor(importer);

        try {
            processor.process();
        } catch (final SyntaxException ex) {
            getIoStreams().errorln(
                    String.format("Syntax error: %s (at line %d, column %d)",
                            ex.getMessage(), ex.getLine(), ex.getColumn()));
        }

        getIoStreams().println(processor.getUnits().toString());
    }

    /**
     * Finds the CLI options.
     *
     * @return never {@code null}
     */
    Options gatherOptions() {
        return options.gatherOptions(getArgs());
    }

    /**
     * Generates help message for main command.
     *
     * @return never {@code null} or empty
     */
    String helpMessage() {
        return options.helpMessage(
                "[-v|--version] [-h|--help]",
                "This is the Caythe interpreter.",
                "$> caythe -l src/main/caythe");
    }
}

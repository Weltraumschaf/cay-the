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

import com.beust.jcommander.Parameter;
import de.weltraumschaf.commons.guava.Lists;
import de.weltraumschaf.commons.guava.Objects;
import java.util.Collections;
import java.util.List;

/**
 * Command line options.
 * <p>
 * Filled by {@link de.weltraumschaf.commons.jcommander.JCommanderImproved}.
 * </p>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Options {

    /**
     * Help flag.
     */
    @Parameter(names = {"-h", "--help"}, description = "Show this message.", help = true)
    private boolean help;
    /**
     * Verbose flag.
     */
    @Parameter(names = {"-v", "--version"}, description = "Gives the programm version.")
    private boolean version;

    @Parameter(names = {"-l", "--libdirs"}, description = "List of directories where the compiler find libraries.")
    private List<String> libDirs = Lists.newArrayList();

    /**
     * Whether the help flag is set.
     *
     * @return {@code false} by default
     */
    public boolean isHelp() {
        return help;
    }

    /**
     * Whether the version flag is set.
     *
     * @return {@code false} by default
     */
    public boolean isVersion() {
        return version;
    }

    /**
     * Library directories.
     *
     * @return empty by default, never {@code null}, unmodifiable
     */
    public List<String> getLibDirs() {
        return Collections.unmodifiableList(libDirs);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("help", help)
                .add("version", version)
                .add("libDirs", libDirs)
                .toString();
    }

}

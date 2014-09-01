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

import com.beust.jcommander.ParameterException;
import de.weltraumschaf.commons.jcommander.JCommanderImproved;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Ignore;

/**
 * Tests for {@link Options}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class OptionsTest {

    private final JCommanderImproved<Options> options
            = new JCommanderImproved<Options>(Constants.COMMAND_NAME.toString(), Options.class);

    @Test
    public void defaultOptions() {
        final Options sut = options.gatherOptions(new String[0]);

        assertThat(sut.isHelp(), is(equalTo(false)));
        assertThat(sut.isVersion(), is(equalTo(false)));
        assertThat(sut.getLibDirs(), is(not(nullValue())));
        assertThat(sut.getLibDirs(), hasSize(0));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getLibDirs_returnsUnmodifableList() {
        final Options sut = options.gatherOptions(new String[0]);

        sut.getLibDirs().add("foo");
    }

    @Test
    public void isHelp_isTrueShortOption() {
        final Options sut = options.gatherOptions(new String[]{"-h"});

        assertThat(sut.isHelp(), is(equalTo(true)));
    }

    @Test
    public void isHelp_isTrueLongOption() {
        final Options sut = options.gatherOptions(new String[]{"--help"});

        assertThat(sut.isHelp(), is(equalTo(true)));
    }

    @Test
    public void isVersion_isTrueShortOption() {
        final Options sut = options.gatherOptions(new String[]{"-v"});

        assertThat(sut.isVersion(), is(equalTo(true)));
    }

    @Test
    public void isVersion_isTrueLongOption() {
        final Options sut = options.gatherOptions(new String[]{"--version"});

        assertThat(sut.isVersion(), is(equalTo(true)));
    }

    @Test(expected = ParameterException.class)
    public void getLibDirs_emptyArgShortOption() {
        final Options sut = options.gatherOptions(new String[]{"-l"});

        assertThat(sut.getLibDirs(), is(not(nullValue())));
        assertThat(sut.getLibDirs(), hasSize(0));
    }

    @Test(expected = ParameterException.class)
    public void getLibDirs_emptyArgLongOption() {
        final Options sut = options.gatherOptions(new String[]{"--libdirs"});

        assertThat(sut.getLibDirs(), is(not(nullValue())));
        assertThat(sut.getLibDirs(), hasSize(0));
    }

    @Test
    public void getLibDirs_oneDirShortOption() {
        final Options sut = options.gatherOptions(new String[]{"-l", "foo/bar/baz"});

        assertThat(sut.getLibDirs(), is(not(nullValue())));
        assertThat(sut.getLibDirs(), hasSize(1));
        assertThat(sut.getLibDirs(), containsInAnyOrder("foo/bar/baz"));
    }

    @Test
    public void getLibDirs_oneDirLongOption() {
        final Options sut = options.gatherOptions(new String[]{"--libdirs", "foo/bar/baz"});

        assertThat(sut.getLibDirs(), is(not(nullValue())));
        assertThat(sut.getLibDirs(), hasSize(1));
        assertThat(sut.getLibDirs(), containsInAnyOrder("foo/bar/baz"));
    }

    @Test
    public void getLibDirs_threeDirShortOption() {
        final Options sut = options.gatherOptions(new String[]{"-l", "foo", "-l", "bar", "-l", "baz"});

        assertThat(sut.getLibDirs(), is(not(nullValue())));
        assertThat(sut.getLibDirs(), hasSize(3));
        assertThat(sut.getLibDirs(), containsInAnyOrder("foo", "bar", "baz"));
    }

    @Test
    public void getLibDirs_threeDirLongOption() {
        final Options sut = options.gatherOptions(new String[]{"--libdirs", "foo", "--libdirs", "bar", "--libdirs", "baz"});

        assertThat(sut.getLibDirs(), is(not(nullValue())));
        assertThat(sut.getLibDirs(), hasSize(3));
        assertThat(sut.getLibDirs(), containsInAnyOrder("foo", "bar", "baz"));
    }

}

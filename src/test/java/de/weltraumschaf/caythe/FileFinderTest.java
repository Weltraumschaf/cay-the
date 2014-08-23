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

import java.nio.file.Paths;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link FileFinder}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class FileFinderTest {

    private final FileFinder sut = new FileFinder(".ct");

    @Test
    public void getFoundFiles_emptyByDefault() {
        assertThat(sut.getFoundFiles().isEmpty(), is(equalTo(true)));
    }

    @Test
    public void isMp3() {
        assertThat(sut.isSourceFile(null), is(equalTo(false)));
        assertThat(sut.isSourceFile(Paths.get("")), is(equalTo(false)));
        assertThat(sut.isSourceFile(Paths.get("foo")), is(equalTo(false)));
        assertThat(sut.isSourceFile(Paths.get("foo.bar")), is(equalTo(false)));
        assertThat(sut.isSourceFile(Paths.get("foo.ct.bar")), is(equalTo(false)));
        assertThat(sut.isSourceFile(Paths.get("foo.bar.ct")), is(equalTo(true)));
    }

}

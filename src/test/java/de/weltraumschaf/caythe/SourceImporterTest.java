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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 * Tests for {@link SourceImporter}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class SourceImporterTest {

    @Rule
    public final TemporaryFolder tmp = new TemporaryFolder();
    private Path base;
    private Path lib;
    private SourceImporter sut;

    @Before
    public void setupSutWithDirs() throws IOException {
        base = tmp.newFolder("base").toPath();
        lib = tmp.newFolder("lib").toPath();
        sut = new SourceImporter(base, Constants.DEFAULT_ENCODING.toString());
    }

    @Test(expected = NullPointerException.class)
    public void importUnit_throwsExceptionIfNullPassedIn() {
        sut.importUnit(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void importUnit_throwsExceptionIfNotFound() {
        sut.importUnit(new FullQualifiedName("foo.bar.baz.String"));
    }

    @Test
    public void importUnit_foundInBaseDir() throws IOException {
        final Path dir = base.resolve("foo/bar/baz");
        Files.createDirectories(dir);
        final Path file = dir.resolve("String.ct");
        Files.createFile(file);

        final SourceFile source = sut.importUnit(new FullQualifiedName("foo.bar.baz.String"));

        assertThat(source, is(not(nullValue())));
        assertThat(source.getSource(), is(equalTo(file)));
    }

    @Test
    public void importUnit_foundInOneLibDir() throws IOException {
        final Path dir = lib.resolve("foo/bar/baz");
        Files.createDirectories(dir);
        final Path file = dir.resolve("String.ct");
        Files.createFile(file);
        sut.addLibDir(lib);

        final SourceFile source = sut.importUnit(new FullQualifiedName("foo.bar.baz.String"));

        assertThat(source, is(not(nullValue())));
        assertThat(source.getSource(), is(equalTo(file)));
    }

    @Test
    public void importUnit_foundInOneOfThreeLibDirs() throws IOException {
        final Path liba = lib.resolve("liba");
        Files.createDirectories(liba);
        sut.addLibDir(liba);
        final Path libb = lib.resolve("libb");
        sut.addLibDir(libb);
        final Path dir = libb.resolve("foo/bar/baz");
        Files.createDirectories(dir);
        final Path file = dir.resolve("String.ct");
        Files.createFile(file);
        final Path libc = lib.resolve("libc");
        Files.createDirectories(libc);
        sut.addLibDir(libc);

        final SourceFile source = sut.importUnit(new FullQualifiedName("foo.bar.baz.String"));

        assertThat(source, is(not(nullValue())));
        assertThat(source.getSource(), is(equalTo(file)));
    }
}

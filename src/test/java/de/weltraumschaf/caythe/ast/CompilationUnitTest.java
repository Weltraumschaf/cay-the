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

package de.weltraumschaf.caythe.ast;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link CompilationUnit}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CompilationUnitTest {

    @Test
    public void extractPackageName() {
        assertThat(CompilationUnit.extractPackageName(null), is(equalTo("")));
        assertThat(CompilationUnit.extractPackageName(""), is(equalTo("")));
        assertThat(CompilationUnit.extractPackageName("Baz.ct"), is(equalTo("")));
        assertThat(CompilationUnit.extractPackageName("bar/Baz.ct"), is(equalTo("bar")));
        assertThat(CompilationUnit.extractPackageName("foo/bar/Baz.ct"), is(equalTo("foo.bar")));
    }

    @Test
    public void extractName() {
        assertThat(CompilationUnit.extractName(null), is(equalTo("")));
        assertThat(CompilationUnit.extractName(""), is(equalTo("")));
        assertThat(CompilationUnit.extractName("Baz.ct"), is(equalTo("Baz")));
        assertThat(CompilationUnit.extractName("bar/Baz.ct"), is(equalTo("Baz")));
        assertThat(CompilationUnit.extractName("foo/bar/Baz.ct"), is(equalTo("Baz")));
    }

    @Test
    public void removeFileExtension() {
        assertThat(CompilationUnit.removeFileExtension(""), is(equalTo("")));
        assertThat(CompilationUnit.removeFileExtension("foo/bar/Baz"), is(equalTo("foo/bar/Baz")));
        assertThat(CompilationUnit.removeFileExtension("foo/Baz.ct"), is(equalTo("foo/Baz")));
        assertThat(CompilationUnit.removeFileExtension("foo/bar/Baz.ct"), is(equalTo("foo/bar/Baz")));
    }
}

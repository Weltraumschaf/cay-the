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

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link SourceFile}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class SourceFileTest {

    @Test
    public void convertToFilename() {
        assertThat(SourceFile.convertToFilename(new FullQualifiedName("String")),
                is(equalTo("String.ct")));
        assertThat(SourceFile.convertToFilename(new FullQualifiedName("foo.String")),
                is(equalTo("foo/String.ct")));
        assertThat(SourceFile.convertToFilename(new FullQualifiedName("foo.bar.String")),
                is(equalTo("foo/bar/String.ct")));
        assertThat(SourceFile.convertToFilename(new FullQualifiedName("foo.bar.baz.String")),
                is(equalTo("foo/bar/baz/String.ct")));
    }

}

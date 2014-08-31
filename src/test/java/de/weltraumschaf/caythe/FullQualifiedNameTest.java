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
 * Tests for {@link FullQualifiedName}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class FullQualifiedNameTest {

    @Test
    public void getPackageName() {
        final FullQualifiedName sut = new FullQualifiedName("foo.bar.baz", "String");

        assertThat(sut.getPackageName(), is(equalTo("foo.bar.baz")));
    }

    @Test
    public void getUnitName() {
        final FullQualifiedName sut = new FullQualifiedName("foo.bar.baz", "String");

        assertThat(sut.getUnitName(), is(equalTo("String")));
    }

    @Test
    public void getFullQualifiedName_withoutPackage() {
        final FullQualifiedName sut = new FullQualifiedName("", "String");

        assertThat(sut.getFullQualifiedName(), is(equalTo("String")));
    }

    @Test
    public void getFullQualifiedName_withPackage() {
        final FullQualifiedName sut = new FullQualifiedName("foo.bar.baz", "String");

        assertThat(sut.getFullQualifiedName(), is(equalTo("foo.bar.baz.String")));
    }

    @Test
    public void extractPackageName() {
        assertThat(FullQualifiedName.extractPackageName(null), is(equalTo("")));
        assertThat(FullQualifiedName.extractPackageName(""), is(equalTo("")));
        assertThat(FullQualifiedName.extractPackageName("String"), is(equalTo("")));
        assertThat(FullQualifiedName.extractPackageName("foo.String"), is(equalTo("foo")));
        assertThat(FullQualifiedName.extractPackageName("foo.bar.String"), is(equalTo("foo.bar")));
        assertThat(FullQualifiedName.extractPackageName("foo.bar.baz.String"), is(equalTo("foo.bar.baz")));
    }

    @Test
    public void extractUnitName() {
        assertThat(FullQualifiedName.extractUnitName(null), is(equalTo("")));
        assertThat(FullQualifiedName.extractUnitName(""), is(equalTo("")));
        assertThat(FullQualifiedName.extractUnitName("String"), is(equalTo("String")));
        assertThat(FullQualifiedName.extractUnitName("foo.String"), is(equalTo("String")));
        assertThat(FullQualifiedName.extractUnitName("foo.bar.String"), is(equalTo("String")));
        assertThat(FullQualifiedName.extractUnitName("foo.bar.baz.String"), is(equalTo("String")));
    }

    @Test
    public void constructWithFullQualifiedName_withoutPackage() {
        final FullQualifiedName sut = new FullQualifiedName("String");

        assertThat(sut.getPackageName(), is(equalTo("")));
        assertThat(sut.getUnitName(), is(equalTo("String")));
    }

    @Test
    public void constructWithFullQualifiedName_withPackage() {
        final FullQualifiedName sut = new FullQualifiedName("foo.bar.baz.String");

        assertThat(sut.getPackageName(), is(equalTo("foo.bar.baz")));
        assertThat(sut.getUnitName(), is(equalTo("String")));
    }
}

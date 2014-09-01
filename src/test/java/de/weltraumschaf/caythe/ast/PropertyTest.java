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
 * Tests for {@link Property}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class PropertyTest {

    @Test
    public void equality() {
        final Property sut1 = new Property("foo", "bar", "baz", Property.Config.READ);
        final Property sut2 = new Property("foo", "bar", "baz", Property.Config.READ);

        assertThat(sut1.equals(sut1), is(equalTo(true)));
        assertThat(sut1.equals(sut2), is(equalTo(true)));
        assertThat(sut2.equals(sut1), is(equalTo(true)));
        assertThat(sut2.equals(sut2), is(equalTo(true)));
    }

}

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

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

/**
 * Tests for {@link Method}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class MethodTest {

    @Test
    public void equalsAndHashCodeContract() {
        EqualsVerifier.forClass(Method.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

}

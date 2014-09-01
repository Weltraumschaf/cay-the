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

import de.weltraumschaf.commons.guava.Objects;
import de.weltraumschaf.commons.validate.Validate;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Property {
    private final String name;
    private final String type;
    private final String value;
    private final Config config;

    public Property(final String name, final String type, final String value, final Config config) {
        super();
        this.name = Validate.notEmpty(name, "name");
        this.type = Validate.notEmpty(type, "type");
        this.value = Validate.notNull(value, "value");
        this.config = Validate.notNull(config, "config");
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, type, value, config);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Property)) {
            return false;
        }

        final Property other = (Property) obj;
        return Objects.equal(name, other.name)
                && Objects.equal(type, other.type)
                && Objects.equal(value, other.value)
                && Objects.equal(config, other.config);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", name)
                .add("type", type)
                .add("value", value)
                .add("config", config)
                .toString();
    }

    public static enum Config {
        READ, WRITE, READWRITE;
    }

}

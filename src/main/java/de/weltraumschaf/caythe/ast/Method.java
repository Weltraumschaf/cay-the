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

import de.weltraumschaf.commons.guava.Lists;
import de.weltraumschaf.commons.guava.Objects;
import de.weltraumschaf.commons.validate.Validate;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Method {

    private final String name;
    private final List<String> returnTypes;
    private Visibility visibility  = Visibility.PRIVATE;

    public Method(final String name) {
        this(name, Lists.<String>newArrayList());
    }

    public Method(final String name, final List<String> returnTypes) {
        super();
        this.name = Validate.notEmpty(name, "name");
        this.returnTypes = Validate.notNull(returnTypes, "returnType");
    }

    public String getName() {
        return name;
    }

    public List<String> getReturnType() {
        return Collections.unmodifiableList(returnTypes);
    }

    public Visibility getVisiblity() {
        return visibility;
    }

    public Method setVisiblity(final Visibility visiblity) {
        this.visibility = visiblity;
        return this;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", name)
                .add("returnTypes", returnTypes)
                .add("visibility", visibility)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                name,
                returnTypes,
                visibility
        );
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Method)) {
            return false;
        }

        final Method other = (Method) obj;
        return Objects.equal(name, other.name)
                && Objects.equal(returnTypes, other.returnTypes)
                && Objects.equal(visibility, other.visibility);
    }

}

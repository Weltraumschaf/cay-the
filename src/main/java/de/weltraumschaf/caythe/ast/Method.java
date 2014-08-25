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
public final class Method {

    private final String name;
    private final String returnType;
    private Visiblity visibility  = Visiblity.PRIVATE;

    public Method(final String name, final String returnType) {
        super();
        this.name = Validate.notEmpty(name, "name");
        this.returnType = Validate.notNull(returnType, "returnType");
    }

    public String getName() {
        return name;
    }

    public String getReturnType() {
        return returnType;
    }

    public Visiblity getVisiblity() {
        return visibility;
    }

    public void setVisiblity(final Visiblity visiblity) {
        this.visibility = visiblity;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", name)
                .add("returnType", returnType)
                .add("visibility", visibility)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                name,
                returnType,
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
                && Objects.equal(returnType, other.returnType)
                && Objects.equal(visibility, other.visibility);
    }

}

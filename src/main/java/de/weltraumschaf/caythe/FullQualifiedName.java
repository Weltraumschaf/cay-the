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

import de.weltraumschaf.commons.guava.Objects;
import de.weltraumschaf.commons.string.Strings;
import de.weltraumschaf.commons.validate.Validate;

/**
 * Represents the full qualified name of an unit.
 *
 * <p>
 * The full qualified name is built from a package name and a unit name.
 * The package name reflects the directory of the unit's file. The unit name
 * reflects the file name of the unit, without the file extension.
 * </p>
 * <p>
 * Instead of the directory separator a own character ({@value #SEPARATOR} is used.
 * </p>
 * <p>
 * Examples:
 * </p>
 * <ul>
 * <li>String</li>
 * <li>foo.String</li>
 * <li>foo.bar.String</li>
 * <li>foo.bar.baz.String</li>
 * </ul>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class FullQualifiedName {

    /**
     * Separator character.
     */
    static final String SEPARATOR = ".";
    /**
     * Never {@code null} may be empty.
     */
    private final String packageName;
    /**
     * Never {@code null} or empty.
     */
    private final String unitName;

    /**
     * Convenience constructor which extract package and unit name from given full qualified name.
     *
     * @param fullQualifiedName must not be {@code null} or empty
     */
    public FullQualifiedName(final String fullQualifiedName) {
        this(extractPackageName(fullQualifiedName), extractUnitName(fullQualifiedName));
    }

    /**
     * Dedicated constructor.
     *
     * @param packageName must not be {@code nul}, may be empty
     * @param unitName must not be {@code null} or empty
     */
    public FullQualifiedName(final String packageName, final String unitName) {
        super();
        this.packageName = Validate.notNull(packageName, "packageName");
        this.unitName = Validate.notEmpty(unitName, "unitName");
    }

    /**
     * Get the package name.
     *
     * @return never {@code null}, maybe empty
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * Get the unit name.
     *
     * @return never {@code null} or empty
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * Get the full qualified name.
     *
     * @return never {@code null} or empty
     */
    public String getFullQualifiedName() {
        if (packageName.isEmpty()) {
            return unitName;
        }

        return packageName + SEPARATOR + unitName;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(packageName, unitName);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof FullQualifiedName)) {
            return false;
        }

        final FullQualifiedName other = (FullQualifiedName) obj;
        return Objects.equal(packageName, other.packageName)
                && Objects.equal(unitName, other.unitName);
    }

    @Override
    public String toString() {
        return "FullQualifiedName{" + "packageName=" + packageName + ", unitName=" + unitName + '}';
    }

    static String extractPackageName(final String fullQualifiedName) {
        final String trimmed = Strings.nullAwareTrim(fullQualifiedName);
        return trimmed.isEmpty() ? "" : doExtractPackageName(trimmed);
    }

    static String extractUnitName(final String fullQualifiedName) {
        final String trimmed = Strings.nullAwareTrim(fullQualifiedName);
        return trimmed.isEmpty() ? "" : doExtractUnitName(trimmed);
    }

    private static String doExtractPackageName(final String fullQualifiedName) {
        final int lastSeparator = fullQualifiedName.lastIndexOf(SEPARATOR);

        if (lastSeparator > 0) {
            return fullQualifiedName.substring(0, lastSeparator);
        }

        return "";
    }

    private static String doExtractUnitName(final String fullQualifiedName) {
        final int lastSeparator = fullQualifiedName.lastIndexOf(SEPARATOR);

        if (lastSeparator > 0) {
            return fullQualifiedName.substring(lastSeparator + 1);
        }

        return fullQualifiedName;
    }
}

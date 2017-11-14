package de.weltraumschaf.caythe.frontend.transform;

import de.weltraumschaf.caythe.intermediate.model.Import;
import de.weltraumschaf.commons.validate.Validate;

import java.util.HashMap;
import java.util.Map;

/**
 * Lookup table for {@link Import imports}.
 */
final class ImportLookupTable {
    private final Map<String, Import> imports = new HashMap<>();

    /**
     * Adds an import to the table.
     * <p>
     * Throws {@link IllegalArgumentException} if,
     * </p>
     * <ul>
     * <li>the import is already added.</li>
     * <li>there is already an import with the same base type name or alias.</li>
     * </ul>
     *
     * @param anImport must not be {@code null}
     */
    void add(final Import anImport) {
        Validate.notNull(anImport, "anImport");

        if (imports.containsValue(anImport)) {
            throw new IllegalArgumentException(
                "Duplicate import for type '" + anImport.getName().getFullQualifiedName() + "'!");
        }

        final String key = anImport.hasAlias()
            ? anImport.getAlias()
            : anImport.getName().getBasename();

        if (imports.containsKey(key)) {
            throw new IllegalArgumentException(
                "For name '" + key + "' is already a type '" + imports.get(key).getName().getFullQualifiedName() + "' imported!");
        }

        imports.put(key, anImport);
    }

    /**
     * Count the number of imports in the table.
     *
     * @return not negative
     */
    int count() {
        return imports.size();
    }

    /**
     * Test if there is an import for the given type base name.
     *
     * @param baseName must not be {@code null} or empty
     * @return {@code true} if there is one, else {@code false}
     */
    boolean has(final String baseName) {
        return imports.containsKey(Validate.notEmpty(baseName, "baseName"));
    }

    /**
     * Get an import for a type base name.
     * <p>
     * Throws {@link IllegalArgumentException} if there is no such import.
     * </p>
     *
     * @param baseName must not be {@code null} or empty
     * @return never {@code null}
     */
    Import get(final String baseName) {
        if (!has(baseName)) {
            throw new IllegalArgumentException("There is no type with base name '" + baseName + "' imported!");
        }

        return imports.get(baseName);
    }
}

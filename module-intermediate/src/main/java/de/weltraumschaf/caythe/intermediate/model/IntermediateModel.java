package de.weltraumschaf.caythe.intermediate.model;

/**
 * All implementing types are part of the intermedaite model.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public interface IntermediateModel {
    /**
     * Returns the name of the method.
     * <p>
     * The name may be used for textual representations.
     * </p>
     *
     * @return never {@code null} nor empty
     */
    default String getNodeName() {
        return getClass().getSimpleName().toLowerCase();
    }

}

package de.weltraumschaf.caythe.intermediate;

/**
 * Implementors can serialize them self into a string representation.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public interface Serializable {
    /**
     * The serialized string representation.
     *
     * @return never {@code null}
     */
    String serialize();
}

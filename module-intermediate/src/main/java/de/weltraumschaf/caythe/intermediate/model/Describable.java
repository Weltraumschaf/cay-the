package de.weltraumschaf.caythe.intermediate.model;

/**
 * Implementations have the ability to describe them self.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @since 1.0.0
 */
public interface Describable {
    /**
     * Creates a description of itself.
     *
     * @return never {@code null}
     */
    ModelDescription describe();
}

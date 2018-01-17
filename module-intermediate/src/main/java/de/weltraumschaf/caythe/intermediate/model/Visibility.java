package de.weltraumschaf.caythe.intermediate.model;

/**
 * The visibility of {@link Type types}, {@link Property properties}, and {@link Method methods}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public enum Visibility implements IntermediateModel {
    UNDEFINED,
    EXPORT,
    PUBLIC,
    PROTECTED,
    PACKAGE,
    PRIVATE;
}

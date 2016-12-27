package de.weltraumschaf.caythe.cli;

/**
 * Creates sub command instances.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public interface SubCommandFactory {
    /**
     * Creates sub command for name.
     * <p>
     * Throws {@link IllegalArgumentException} for unsupported names.
     * </p>
     *
     * @param name must not be {@code null}
     * @return never {@code null}, always new instance
     */
    SubCommand forName(final SubCommandName name);
}

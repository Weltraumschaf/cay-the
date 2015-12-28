package de.weltraumschaf.caythe.backend.symtab;

/**
 * Describes a type.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public interface Type {

    /**
     * Null object pattern: USed instead of {@code nul}.
     */
    Type NULL = new Type() {

        @Override
        public String getName() {
            return "undefined";
        }
    };

    /**
     * Name of the type.
     *
     * @return never {@code null} or empty
     */
    String getName();

}

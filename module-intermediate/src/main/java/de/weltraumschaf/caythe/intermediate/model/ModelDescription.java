package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.commons.validate.Validate;

/**
 * Holds the description of a {@link IntermediateModel}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @since 1.0.0
 */
public final class ModelDescription {
    private final ModelDescriber describer = new ModelDescriber();
    private final IntermediateModel described;

    /**
     * Dedicated constructor.
     *
     * @param described must not be {@code null}
     */
    public ModelDescription(final IntermediateModel described) {
        super();
        this.described = Validate.notNull(described, "described");
    }

    /**
     * Creates a textual representation.
     *
     * @return never {@code null} or empty
     */
    public String asPlainText() {
        return describer.describe(described);
    }
}

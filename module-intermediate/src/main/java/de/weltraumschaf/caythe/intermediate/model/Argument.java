package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.caythe.intermediate.equivalence.Equivalence;
import de.weltraumschaf.caythe.intermediate.equivalence.Notification;
import de.weltraumschaf.commons.validate.Validate;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * Represents a method argument.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
@ToString
public final class Argument implements IntermediateModel, Equivalence<Argument> {
    @Getter
    private final String name;
    @Getter
    private final TypeName type;

    public Argument(final String name, final TypeName type) {
        super();
        this.name = Validate.notEmpty(name, "name");
        this.type = Validate.notNull(type, "type");
    }

    @Override
    public ModelDescription describe() {
        return new ModelDescription(this);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Argument)) {
            return false;
        }

        final Argument argument = (Argument) o;
        return Objects.equals(name, argument.name) &&
            Objects.equals(type, argument.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    @Override
    public void probeEquivalence(final Argument other, final Notification result) {
        // TODO Write tests for this method.
        if (isNotEqual(name, other.name)) {
            result.error(
                difference(
                    "Name",
                    "This has name%n%s%nbut other has name%n%s%n"),
                name, other.name
            );
        }

        type.probeEquivalence(other.type, result);
    }
}

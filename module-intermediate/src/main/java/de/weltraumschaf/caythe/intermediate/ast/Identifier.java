package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.commons.validate.Validate;

import java.util.Objects;

/**
 * Represents an identifier literal.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class Identifier extends BaseNode {
    private final String name;

    public Identifier(final String name, final Position sourcePosition) {
        super(sourcePosition);
        this.name = Validate.notEmpty(name,"name");
    }

    public String getName() {
        return name;
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String serialize() {
        return serialize(name);
    }

    @Override
    public String getNodeName() {
        return "identifier";
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Identifier)) {
            return false;
        }

        final Identifier that = (Identifier) o;
        return Objects.equals(name, that.name)
            && Objects.equals(sourcePosition(), that.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sourcePosition());
    }

    @Override
    public String toString() {
        return "Identifier{" +
            "name='" + name + '\'' +
            ", sourcePosition=" + sourcePosition() +
            '}';
    }

}

package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.equivalence.Equivalence;
import de.weltraumschaf.caythe.intermediate.model.IntermediateModel;
import de.weltraumschaf.caythe.intermediate.model.Position;
import de.weltraumschaf.caythe.intermediate.model.Serializable;

/**
 * Basic interface for AST nodes.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public interface AstNode extends IntermediateModel, Serializable, Equivalence<AstNode> {
    /**
     * Returns the position of the token in source from where this node origins.
     *
     * @return never {@code null}, maybe {@link Position#UNKNOWN_FILE}
     */
    default Position getSourcePosition() {
        return Position.UNKNOWN;
    }

    /**
     * Accepts a visitor to traverse this node.
     *
     * @param visitor must not be {@code null}
     * @param <R>     the return type of the visitor
     * @return never {@code null}
     */
    <R> R accept(final AstVisitor<? extends R> visitor);

    /**
     * Returns the name of the method.
     * <p>
     * The name may be used for textual representations.
     * </p>
     *
     * @return never {@code null} nor empty
     */
    String getNodeName();
}

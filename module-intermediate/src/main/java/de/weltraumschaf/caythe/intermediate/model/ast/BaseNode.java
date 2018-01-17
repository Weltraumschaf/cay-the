package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.commons.validate.Validate;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
abstract class BaseNode implements AstNode {
    private static final String SERIALIZATION_FORMAT = "(%s%s%s)";
    @Getter
    private final Position sourcePosition;

    BaseNode(final Position sourcePosition) {
        super();
        this.sourcePosition = Validate.notNull(sourcePosition, "getSourcePosition");
    }

    @Override
    public String serialize() {
        return serialize("");
    }

    final String serialize(final String additional) {
        return String.format(SERIALIZATION_FORMAT,
            getNodeName(),
            additional.isEmpty() ? "" : " " + additional,
            serialize(sourcePosition));
    }

    private String serialize(final Position pos) {
        if (Position.UNKNOWN.equals(pos)) {
            return "";
        }

        return " " + sourcePosition.serialize();
    }

    String serialize(final AstNode... children) {
        return serialize(Arrays.asList(children));
    }

    String serialize(final Collection<AstNode> children) {
        if (null == children) {
            return "";
        }

        if (children.isEmpty()) {
            return "";
        }

        return children.stream()
            .map(AstNode::serialize)
            .collect(Collectors.joining(" "));
    }

}

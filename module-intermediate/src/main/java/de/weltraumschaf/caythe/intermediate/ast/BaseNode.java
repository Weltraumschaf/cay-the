package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.commons.validate.Validate;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

abstract class BaseNode implements AstNode {
    private static final String SERIALIZATION_FORMAT = "(%s%s%s)";
    private final Position sourcePosition;

    BaseNode(final Position sourcePosition) {
        super();
        this.sourcePosition = sourcePosition;
    }

    @Override
    public final Position sourcePosition() {
        return sourcePosition;
    }

    final String serialize(final String name) {
        return serialize(name, "");
    }

    final String serialize(final String name, final String additional) {
        Validate.notEmpty(name, "name");
        return String.format(SERIALIZATION_FORMAT,
            name,
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

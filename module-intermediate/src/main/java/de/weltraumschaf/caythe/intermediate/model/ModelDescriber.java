package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.caythe.intermediate.model.ast.BooleanLiteral;
import de.weltraumschaf.caythe.intermediate.model.ast.IntegerLiteral;
import de.weltraumschaf.caythe.intermediate.model.ast.RealLiteral;
import de.weltraumschaf.caythe.intermediate.model.ast.StringLiteral;
import de.weltraumschaf.commons.validate.Validate;

/**
 * Describes {@link IntermediateModel model objects}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
final class ModelDescriber {
    String describe(final IntermediateModel described) {
        Validate.notNull(described, "described");

        if (described instanceof IntegerLiteral) {
            return describe((IntegerLiteral) described);
        } else if (described instanceof RealLiteral) {
            return describe((RealLiteral) described);
        } else if (described instanceof BooleanLiteral) {
            return describe((BooleanLiteral) described);
        } else if (described instanceof StringLiteral) {
            return describe((StringLiteral) described);
        }

        return formatNode(described.getNodeName());
    }

    private String describe(final IntegerLiteral described) {
        return formatNode(described.getNodeName(), String.valueOf(described.getValue()));
    }

    private String describe(final RealLiteral described) {
        return formatNode(described.getNodeName(), String.valueOf(described.getValue()));
    }

    private String describe(final BooleanLiteral described) {
        return formatNode(described.getNodeName(), String.valueOf(described.getValue()));
    }

    private String describe(final StringLiteral described) {
        return formatNode(described.getNodeName(), '"' + String.valueOf(described.getValue()) + '"');
    }

    private String formatNode(final String nodeName) {
        return formatNode(nodeName, "");
    }

    private String formatNode(final String nodeName, final String additionalInformation) {
        final String paddedAdditionalInformation = additionalInformation.isEmpty()
            ? ""
            : " " + additionalInformation;
        return String.format("(%s%s)", nodeName, paddedAdditionalInformation);
    }
}

package de.weltraumschaf.caythe.testing;

import de.weltraumschaf.caythe.intermediate.model.ast.AstNode;
import org.hamcrest.Matcher;

/**
 */
public final class CayTheTestingMatchers {
    private CayTheTestingMatchers() {
        super();
        throw new UnsupportedOperationException("Do not call by reflection!");
    }

    public static Matcher<AstNode> asSpecified(final AstSpecification spec) {
        return AstSpecificationMatcher.asSpecified(spec);
    }
}

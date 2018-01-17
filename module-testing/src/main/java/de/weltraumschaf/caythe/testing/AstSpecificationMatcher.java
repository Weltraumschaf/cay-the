package de.weltraumschaf.caythe.testing;

import de.weltraumschaf.caythe.intermediate.model.ast.AstNode;
import de.weltraumschaf.commons.validate.Validate;
import org.hamcrest.*;

/**
 */
final class AstSpecificationMatcher extends TypeSafeMatcher<AstNode> {
    private final AstSpecificationFormatter fmt = new AstSpecificationFormatter();
    private final Matcher<String> matcher;

    private AstSpecificationMatcher(final AstSpecification spec) {
        super();
        final String expectation = fmt.format(Validate.notNull(spec, "spec").getExpectation());
        this.matcher = Matchers.equalTo(expectation);
    }

    @Override
    protected boolean matchesSafely(final AstNode item) {
        return matcher.matches(fmt.format(item.serialize()));
    }

    @Override
    public void describeTo(final Description description) {
        matcher.describeTo(description);
    }

    @Factory
    static Matcher<AstNode> asSpecified(final AstSpecification spec) {
        return new AstSpecificationMatcher(spec);
    }
}

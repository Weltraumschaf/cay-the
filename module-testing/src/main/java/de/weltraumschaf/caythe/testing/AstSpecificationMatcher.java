package de.weltraumschaf.caythe.testing;

import de.weltraumschaf.caythe.intermediate.ast.AstNode;
import de.weltraumschaf.commons.validate.Validate;
import org.hamcrest.*;

/**
 */
final class AstSpecificationMatcher extends TypeSafeMatcher<AstNode> {
    private final AstSpecificationFormatter fmt = new AstSpecificationFormatter();
    private final AstSpecification spec;
    private final Matcher<String> matcher;

    private AstSpecificationMatcher(final AstSpecification spec) {
        super();
        this.spec = fmt.format(Validate.notNull(spec, "spec"));
        this.matcher = Matchers.equalTo(spec.getExpectation());
    }

    @Override
    protected boolean matchesSafely(final AstNode item) {
        final AstSpecification actual = fmt.format(new AstSpecification("", "", item.serialize()));
        return matcher.matches(actual.getExpectation());
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText(" with the expectation ");
        description.appendDescriptionOf(matcher);
    }

    @Factory
    static Matcher<AstNode> asSpecified(final AstSpecification spec) {
        return new AstSpecificationMatcher(spec);
    }
}

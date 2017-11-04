package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.caythe.intermediate.ast.AstNode;
import de.weltraumschaf.caythe.intermediate.model.Type;
import de.weltraumschaf.caythe.intermediate.model.TypeName;
import de.weltraumschaf.caythe.testing.AstSpecification;
import de.weltraumschaf.caythe.testing.AstSpecificationFormatter;
import de.weltraumschaf.caythe.testing.AstSpecificationParser;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;

import static de.weltraumschaf.caythe.testing.CayTheTestingMatchers.asSpecified;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Runs all the test specified in {@literal *.astspec} files.
 */
@Ignore
@RunWith(Parameterized.class)
public final class AstSpecTest extends VisitorTestCase {
    @SuppressWarnings("unchecked")
    private final CayTheSourceVisitor<Type> sut = new SourceToIntermediateTransformer(TypeName.NONE);
    private final AstSpecificationFormatter fmt = new AstSpecificationFormatter();
    private final AstSpecification spec;

    public AstSpecTest(final AstSpecification spec, final String ignoredOne, final String ignoredTwo) {
        super();
        this.spec = spec;
    }

    @Parameterized.Parameters(name = "{index}: {1} : {2}")
    public static Collection<Object[]> data() throws IOException, URISyntaxException {
        final URI fixtures = AstSpecTest.class.getResource("/de/weltraumschaf/caythe/frontend").toURI();
        final AstSpecificationParser parser = new AstSpecificationParser();
        return parser.loadSpecificationsFromDirectory(Paths.get(fixtures))
            .stream()
            .map(spec -> new Object[] {spec, spec.getFile().toString(), spec.getGiven()})
            .collect(Collectors.toList());
    }

    @Test
    public void test() throws IOException {
        assertThat(spec, is(not(nullValue())));

        final Type ast = sut.visit(parseSource(spec.getGiven()));

//        assertThat(ast, is(asSpecified(spec)));
    }
}

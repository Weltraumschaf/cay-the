package de.weltraumschaf.caythe.frontend.transform;

import de.weltraumschaf.caythe.frontend.VisitorTestCase;

import java.nio.file.Paths;

/**
 * Base test case for tests of {@link SourceToIntermediateTransformer}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
class TransformVisitorTestCase extends VisitorTestCase {
    private static final String FIXTURE_DIR = "/de/weltraumschaf/caythe/frontend/transform";
    static final String EXPECTED_BASE_PACKAGE = "de.weltraumschaf.caythe.frontend.transform";

    final String createFixtureFile(final String relativeFile) {
        return FIXTURE_DIR + relativeFile;
    }

    final SourceToIntermediateTransformer createSut(final String src) {
        return new SourceToIntermediateTransformer(Paths.get(src));
    }
}

package de.weltraumschaf.caythe.testing;

import java.io.*;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

/**
 * Parses a given AST specification.
 * <p>
 * A specification is a file which contains a given source code string and  an expected AST in a simple Lisp like list style.
 * Both are separated by a marker. It may also contain comments. Formal grammar:
 * </p>
 * <pre>
 * {@code
 * specification    = declaration* EOL ;
 * declaration      = description | given | expectation ;
 * description      = '#description' ANY_CHARACTER ;
 * given            = '#given' ANY_CHARACTER ;
 * expectation      = '#expectation' ANY_CHARACTER ;
 * }
 * </pre>
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class AstSpecificationParser {
    private static final char NL = '\n';
    private Section currentSection = Section.UNKNOWN;

    public AstSpecification parse(final InputStream input) throws IOException {
        final StringBuilder description = new StringBuilder();
        final StringBuilder given = new StringBuilder();
        final StringBuilder expectation = new StringBuilder();

        try (final BufferedReader reader = createReader(input)) {
            while (true) {
                final String line = reader.readLine();

                if (line == null) {
                    break;
                }

                final String trimmedLine = line.trim();

                if (trimmedLine.isEmpty()) {
                    continue;
                }

                if (trimmedLine.startsWith("#")) {
                    currentSection = Section.valueOf(trimmedLine.substring(1).toUpperCase());
                    continue;
                }


                switch (currentSection) {
                    case DESCRIPTION:
                        description.append(line).append(NL);
                        break;
                    case GIVEN:
                        given.append(line).append(NL);
                        break;
                    case EXPECTATION:
                        expectation.append(line).append(NL);
                        break;
                }
            }
        }

        return new AstSpecification(description.toString(), given.toString(), expectation.toString());
    }

    private BufferedReader createReader(final InputStream input) {
        final CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();
        return new BufferedReader(new InputStreamReader(input, decoder));
    }

    private enum Section {
        UNKNOWN,
        DESCRIPTION,
        GIVEN,
        EXPECTATION;

        public String toString() {
            return name().toLowerCase();
        }
    }
}

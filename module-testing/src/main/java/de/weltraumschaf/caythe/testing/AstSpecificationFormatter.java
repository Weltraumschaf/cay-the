package de.weltraumschaf.caythe.testing;

import de.weltraumschaf.commons.parse.characters.CharacterStream;

import java.util.Collections;

/**
 *
 */
final class AstSpecificationFormatter {
    private int indentionLevel;

    private void indent() {
        indentionLevel++;
    }

    private void dedent() {
        indentionLevel--;

        if (indentionLevel < 0) {
            indentionLevel = 0;
        }
    }

    private String indention() {
        if (indentionLevel < 1) {
            return "";
        }

        return String.join("", Collections.nCopies(indentionLevel, "  "));
    }

    AstSpecification format(final AstSpecification input) {
        return new AstSpecification(input.getDescription(), input.getGiven(), format(input.getExpectation()));
    }

    private String format(final String expectation) {
        indentionLevel = -1;
        final StringBuilder buffer = new StringBuilder();
        final CharacterStream characters = new CharacterStream(expectation.trim());
        boolean shouldInsertNewline = false;

        while (characters.hasNext()) {
            final char c = characters.next();

            if (c == ' ' && skip(characters.peek())) {
                // Ignore white space before skip characters.
                continue;
            }

            if (c == '(') {
                shouldInsertNewline = true;
                indent();
            }


            if (shouldInsertNewline) {
                buffer.append('\n').append(indention());
                shouldInsertNewline = false;

                if (c == ' ') {
                    continue;
                }
            }

            buffer.append(c);

            if (c == ')') {
                shouldInsertNewline = true;
                dedent();
            }
        }

        return buffer.toString().trim();
    }

    private boolean skip(final char c) {
        switch (c) {
            case ' ':
            case '(':
                return true;
            default:
                return false;
        }
    }
}

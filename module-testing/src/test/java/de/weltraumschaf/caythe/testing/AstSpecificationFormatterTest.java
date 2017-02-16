package de.weltraumschaf.caythe.testing;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Tests for {@link AstSpecificationFormatter}.
 */
public class AstSpecificationFormatterTest {
    private final AstSpecificationFormatter sut = new AstSpecificationFormatter();

    @Test
    public void format() {
        final AstSpecification result = sut.format(
            new AstSpecification("", "", "(unit (+ 1 (* 2 3 [1, 1]) [2, 2]) [3, 3])"));

        assertThat(
            result.getExpectation(),
            is("" +
                "(unit\n" +
                "  (+ 1\n" +
                "    (* 2 3 [1, 1])\n" +
                "  [2, 2])\n" +
                "[3, 3])"));
    }
}
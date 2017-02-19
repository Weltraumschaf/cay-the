package de.weltraumschaf.caythe.testing;

import org.junit.Test;

import java.nio.file.Path;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link AstSpecificationFormatter}.
 */
public class AstSpecificationFormatterTest {
    private final AstSpecificationFormatter sut = new AstSpecificationFormatter();

    @Test
    public void format_withoutNewlines() {
        final AstSpecification result = sut.format(
            new AstSpecification(
                "",
                "",
                "(unit (+ 1 (* 2 3 [1, 1]) [2, 2]) [3, 3])",
                mock(Path.class)));

        assertThat(
            result.getExpectation(),
            is("" +
                "(unit\n" +
                "  (+ 1\n" +
                "    (* 2 3 [1, 1])\n" +
                "  [2, 2])\n" +
                "[3, 3])"));
    }

    @Test
    public void format_withNewlines() {
        final String ast = "(unit\n" +
            "  (statements\n" +
            "    (let\n" +
            "      (=\n" +
            "        (identifier a [1:4])\n" +
            "        (+\n" +
            "          (integer 1 [1:8])\n" +
            "          (*\n" +
            "            (integer 2 [1:12])\n" +
            "            (integer 3 [1:16])\n" +
            "          [1:12])\n" +
            "        [1:8])\n" +
            "      [1:0])\n" +
            "    [1:0])\n" +
            "  [1:0])\n" +
            "[1:0])";

        assertThat(sut.format(ast), is(ast));
    }
}
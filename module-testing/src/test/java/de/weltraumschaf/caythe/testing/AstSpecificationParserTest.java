package de.weltraumschaf.caythe.testing;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Tests for {@link AstSpecificationParser}.
 */
public class AstSpecificationParserTest {
    private final AstSpecificationParser sut = new AstSpecificationParser();

    @Test
    public void parse_exampleFile() throws IOException {
        assertThat(
            sut.parse(getClass().getResourceAsStream("/de/weltraumschaf/caythe/testing/Example.astspec")),
            is(new AstSpecification(
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula\n" +
                    "eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient\n" +
                    "montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque\n" +
                    "eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo,\n" +
                    "fringilla vel, aliquet nec, vulputate.\n",
                "let a = 1 + 2 * 3\n",
                "(unit\n" +
                    "    (statements\n" +
                    "        (let [])\n" +
                    "    [])\n" +
                    "[])\n")));
    }
}
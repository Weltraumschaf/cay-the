package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.Position;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static de.weltraumschaf.caythe.intermediate.ast.builder.LiteralBuilder.*;

/**
 * Tests for {@link ArrayLiteral}.
 */
public class ArrayLiteralTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(ArrayLiteral.class).verify();
    }

    @Test
    public void serialize_empty() {
        final ArrayLiteral sut = new ArrayLiteral(Collections.emptyList(), new Position(2, 3));

        assertThat(sut.serialize(), is("(array [2:3])"));
    }

    @Test
    public void serialize_one() {
        final List<AstNode> values = Collections.singletonList(integer(42L, 3, 5));
        final ArrayLiteral sut = new ArrayLiteral(values, new Position(2, 3));

        assertThat(sut.serialize(), is("(array (integer 42 [3:5]) [2:3])"));
    }

    @Test
    public void serialize_three() {
        final List<AstNode> values = Arrays.asList(
            string("foo", 3,5),
            string("bar", 4,6),
            string("baz", 5, 7));
        final ArrayLiteral sut = new ArrayLiteral(values, new Position(2, 3));

        assertThat(
            sut.serialize(),
            is("(array (string foo [3:5]) (string bar [4:6]) (string baz [5:7]) [2:3])"));
    }
}
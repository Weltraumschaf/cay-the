package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.model.Position;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static de.weltraumschaf.caythe.intermediate.model.ast.builder.LiteralFactory.integer;
import static de.weltraumschaf.caythe.intermediate.model.ast.builder.LiteralFactory.string;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Tests for {@link HashLiteral}.
 */
public class HashLiteralTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(HashLiteral.class).verify();
    }

    @Test
    public void serialize_empty() {
        final Map<AstNode, AstNode> values = new HashMap<>();
        final HashLiteral sut = new HashLiteral(values, new Position(1, 2));

        assertThat(sut.serialize(), is("(hash [1:2])"));
    }

    @Test
    public void serialize_notEmpty() {
        final Map<AstNode, AstNode> values = new HashMap<>();
        values.put(string("foo", 1, 2), integer(1L, 3, 4));
        values.put(string("bar", 5, 6), integer(2L, 7, 8));
        final HashLiteral sut = new HashLiteral(values, new Position(1, 2));

        assertThat(
            sut.serialize(),
            is("(hash ((string bar [5:6]) (integer 2 [7:8])) ((string foo [1:2]) (integer 1 [3:4])) [1:2])"));
    }
}
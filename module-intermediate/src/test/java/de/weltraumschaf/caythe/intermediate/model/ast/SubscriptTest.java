package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.Position;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static de.weltraumschaf.caythe.intermediate.model.ast.builder.LiteralFactory.identifier;
import static de.weltraumschaf.caythe.intermediate.model.ast.builder.LiteralFactory.integer;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Tests for {@link Subscript}.
 */
public class SubscriptTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Subscript.class).verify();
    }

    @Test
    public void serialize() {
        final Subscript sut = new Subscript(
            identifier("foo", 1, 2),
            integer(23L, 3, 4),
            new Position(1, 2));

        assertThat(sut.serialize(), is("([] (identifier foo [1:2]) (integer 23 [3:4]) [1:2])"));
    }
}
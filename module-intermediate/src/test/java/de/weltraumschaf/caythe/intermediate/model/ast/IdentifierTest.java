package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.Position;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Tests for {@link Identifier}.
 */
public class IdentifierTest {
    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Identifier.class).verify();
    }

    @Test
    public void serialize() {
        final Identifier sut = new Identifier("foo", new Position(1, 2));

        assertThat(sut.serialize(), is("(identifier foo [1:2])"));
    }
}
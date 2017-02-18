package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.Position;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Tests for {@link IntegerLiteral}.
 */
public class IntegerLiteralTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(IntegerLiteral.class).verify();
    }

    @Test
    public void serialize() {
        final IntegerLiteral sut = new IntegerLiteral(23L, new Position(1, 2));

        assertThat(sut.serialize(), is("(integer 23 [1:2])"));
    }
}
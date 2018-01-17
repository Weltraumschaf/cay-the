package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.Position;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Tests for {@link BooleanLiteral}.
 */
public class BooleanLiteralTest {
    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(BooleanLiteral.class).verify();
    }

    @Test
    public void serialize() {
        final BooleanLiteral sut = new BooleanLiteral(true, new Position(2, 3));

        assertThat(sut.serialize(), is("(boolean true [2:3])"));
    }
}
package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.model.Position;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Tests for {@link StringLiteral}.
 */
public class StringLiteralTest {
    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(StringLiteral.class).verify();
    }

    @Test
    public void serialize() {
        final StringLiteral sut = new StringLiteral("foobar", new Position(1, 2));

        assertThat(sut.serialize(), is("(string foobar [1:2])"));
    }
}
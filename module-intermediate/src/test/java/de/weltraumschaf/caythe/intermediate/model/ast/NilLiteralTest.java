package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.model.Position;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link NilLiteral}.
 */
public class NilLiteralTest {
    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(NilLiteral.class).verify();
    }

    @Test
    public void serialize() {
        final NilLiteral sut = new NilLiteral(new Position(1, 2));

        assertThat(sut.serialize(), is("(nil [1:2])"));
    }
}
package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.model.Position;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link RealLiteral}.
 */
public class RealLiteralTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(RealLiteral.class).verify();
    }

    @Test
    public void serialize() {
        final RealLiteral sut = new RealLiteral(3.14f, new Position(1, 2));

        assertThat(sut.serialize(), is("(real 3.140000104904175 [1:2])"));
    }
}
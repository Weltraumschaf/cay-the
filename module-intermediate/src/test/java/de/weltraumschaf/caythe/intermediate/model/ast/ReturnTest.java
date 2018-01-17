package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.Position;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static de.weltraumschaf.caythe.intermediate.model.ast.builder.LiteralFactory.bool;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link Return}.
 */
public class ReturnTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Return.class).verify();
    }

    @Test
    public void serialize_endlessLoop() {
        final Return sut = new Return(
            bool(true, 1, 2),
            new Position(1, 2));

        assertThat(sut.serialize(), is("(return (boolean true [1:2]) [1:2])"));
    }

}
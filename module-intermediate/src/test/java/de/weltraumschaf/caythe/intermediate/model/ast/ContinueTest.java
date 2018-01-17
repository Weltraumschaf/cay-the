package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.Position;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Tests for {@link Continue}.
 */
public class ContinueTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Continue.class).verify();
    }

    @Test
    public void serialize() {
        final Continue sut = new Continue(new Position(1, 2));

        assertThat(sut.serialize(), is("(continue [1:2])"));
    }
}
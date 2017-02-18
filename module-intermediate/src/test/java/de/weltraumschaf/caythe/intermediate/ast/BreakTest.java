package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.Position;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Tests for {@link Break}.
 */
public class BreakTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Break.class).verify();
    }

    @Test
    public void serialize() {
        final Break sut = new Break(new Position(1, 2));

        assertThat(sut.serialize(), is("(break [1:2])"));
    }
}
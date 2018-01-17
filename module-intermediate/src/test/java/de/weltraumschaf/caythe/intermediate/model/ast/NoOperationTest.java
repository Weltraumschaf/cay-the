package de.weltraumschaf.caythe.intermediate.model.ast;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Tests for {@link NoOperation}.
 */
public class NoOperationTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(NoOperation.class).verify();
    }

    @Test
    public void serialize() {
        final NoOperation sut = new NoOperation();

        assertThat(sut.serialize(), is("(noop)"));
    }
}
package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.equivalence.Notification;
import de.weltraumschaf.caythe.intermediate.model.Position;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Tests for {@link IntegerLiteral}.
 */
public class IntegerLiteralTest {
    private final IntegerLiteral sut = new IntegerLiteral(23L, new Position(1, 2));

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(IntegerLiteral.class).verify();
    }

    @Test
    public void serialize() {
        assertThat(sut.serialize(), is("(integer 23 [1:2])"));
    }

    @Test
    public void probeEquivalence_sameValue() {
        final Notification result = new Notification();

        sut.probeEquivalence(sut, result);

        assertThat(result.isOk(), is(true));
        assertThat(result.report(), is(""));
    }

    @Test
    public void probeEquivalence_differentValues() {
        final IntegerLiteral other = new IntegerLiteral(42L, Position.UNKNOWN);
        final Notification result = new Notification();

        sut.probeEquivalence(other, result);

        assertThat(result.isOk(), is(false));
        assertThat(result.report(), is("Value differ: 23 != 42"));
    }
}
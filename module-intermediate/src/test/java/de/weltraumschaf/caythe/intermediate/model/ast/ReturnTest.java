package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.equivalence.Notification;
import de.weltraumschaf.caythe.intermediate.model.Position;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static de.weltraumschaf.caythe.intermediate.model.ast.builder.LiteralFactory.bool;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link Return}.
 */
public class ReturnTest {

    private final Return sut = new Return(
        bool(true, 1, 2),
        new Position(1, 2));

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Return.class).verify();
    }

    @Test
    public void serialize_endlessLoop() {
        assertThat(sut.serialize(), is("(return (boolean true [1:2]) [1:2])"));
    }

    @Test
    public void probeEquivalence_sameValue() {
        final Notification result = new Notification();

        sut.probeEquivalence(sut, result);

        assertThat(result.isOk(), is(true));
        assertThat(result.report(), is(""));
    }

    @Test
    public void probeEquivalence_differentValue() {
        final Return other = new Return(
            bool(false, Position.UNKNOWN),
            Position.UNKNOWN);
        final Notification result = new Notification();

        sut.probeEquivalence(other, result);

        assertThat(result.isOk(), is(false));
        assertThat(result.report(), is("Value differ: (boolean true) != (boolean false)"));
    }

}
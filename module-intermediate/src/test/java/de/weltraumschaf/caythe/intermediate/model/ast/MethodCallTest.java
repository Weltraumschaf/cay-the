package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.model.Position;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import java.util.Arrays;

import static de.weltraumschaf.caythe.intermediate.model.ast.builder.LiteralFactory.identifier;
import static de.weltraumschaf.caythe.intermediate.model.ast.builder.LiteralFactory.integer;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link MethodCall}.
 */
public class MethodCallTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(MethodCall.class).verify();
    }

    @Test
    public void serialize() {
        final MethodCall sut = new MethodCall(
            identifier("foo", 1, 2),
            Arrays.asList(integer(23L, 3, 4), integer(42L, 5, 6)),
            new Position(1, 2));

        assertThat(
            sut.serialize(),
            is("(fn-call (identifier foo [1:2]) (integer 23 [3:4]) (integer 42 [5:6]) [1:2])"));
    }
}
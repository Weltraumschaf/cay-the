package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.ast.builder.UnaryOperationBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static de.weltraumschaf.caythe.intermediate.ast.builder.LiteralBuilder.integer;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Tests for {@link UnaryOperation}.
 */
public class UnaryOperationTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(UnaryOperation.class).verify();
    }

    @Test
    public void serialize() {
        final UnaryOperation sut = UnaryOperationBuilder.negate(integer(23L ,2 ,3), 1, 2);

        assertThat(sut.serialize(), is("(- (integer 23 [2:3]) [1:2])"));
    }
}
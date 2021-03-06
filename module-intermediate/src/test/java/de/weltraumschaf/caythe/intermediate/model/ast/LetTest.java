package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.model.Position;
import de.weltraumschaf.caythe.intermediate.model.TypeName;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static de.weltraumschaf.caythe.intermediate.model.ast.builder.LiteralFactory.identifier;
import static de.weltraumschaf.caythe.intermediate.model.ast.builder.LiteralFactory.integer;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Tests for {@link Let}.
 */
public class LetTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Let.class).verify();
    }

    @Test
    public void serialize() {
        final Let sut = new Let(
            new TypeName("org.snafu", "Foo"),
            new BinaryOperation(BinaryOperation.Operator.ASSIGN,
                identifier("foo", 1, 2),
                integer(23L, 3, 4),
                new Position(5, 6)),
            new Position(7, 8));

        assertThat(sut.serialize(), is("(let (= (identifier foo [1:2]) (integer 23 [3:4]) [5:6]) [7:8])"));
    }

}
package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.caythe.intermediate.model.TypeName;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static de.weltraumschaf.caythe.intermediate.ast.builder.BinaryOperationBuilder.*;
import static de.weltraumschaf.caythe.intermediate.ast.builder.LiteralBuilder.*;

/**
 * Tests for {@link Const}.
 */
public class ConstTest {
    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Const.class).verify();
    }

    @Test
    public void serialize() {
        final Const sut = new Const(
            new TypeName("org.snafu", "Foo"),
            assign(
                identifier("foo", 1,2),
                integer(23L, 3,4),
                5, 6),
            new Position(7, 8));

        assertThat(sut.serialize(), is("(const (= (identifier foo [1:2]) (integer 23 [3:4]) [5:6]) [7:8])"));
    }
}
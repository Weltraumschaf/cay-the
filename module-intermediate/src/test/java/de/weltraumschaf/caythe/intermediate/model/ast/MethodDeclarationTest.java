package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.model.Position;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static de.weltraumschaf.caythe.intermediate.model.ast.builder.BinaryOperationFactory.*;
import static de.weltraumschaf.caythe.intermediate.model.ast.builder.LiteralFactory.identifier;
import static de.weltraumschaf.caythe.intermediate.model.ast.builder.LiteralFactory.integer;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Tests for {@link MethodDeclaration}.
 */
public class MethodDeclarationTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(MethodDeclaration.class).verify();
    }

    @Test
    public void serialize() {
        final MethodDeclaration sut = new MethodDeclaration(
            Arrays.asList(identifier("foo", 2, 3), identifier("bar", 4, 5)),
            Collections.singletonList(
                subtraction(
                    integer(23L, 6, 7),
                    integer(42L, 8, 9),
                    10, 11)),
            new Position(1, 2)
        );

        assertThat(
            sut.serialize(),
            is("(fn-decl ((identifier foo [2:3]) (identifier bar [4:5])) ((- (integer 23 [6:7]) (integer 42 [8:9]) [10:11])) [1:2])"));
    }
}
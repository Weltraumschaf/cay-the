package de.weltraumschaf.caythe.intermediate.equivalence;

import de.weltraumschaf.caythe.intermediate.model.Position;
import de.weltraumschaf.caythe.intermediate.model.ast.IntegerLiteral;
import de.weltraumschaf.caythe.intermediate.model.ast.RealLiteral;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link ResultDescriber}.
 */
public class ResultDescriberTest {
    private final ResultDescriber sut = new ResultDescriber();

    @Test
    public void nodeTypeMismatch() {
        assertThat(
            sut.nodeTypeMismatch(
                new IntegerLiteral(23L, Position.UNKNOWN),
                new RealLiteral(2.3, Position.UNKNOWN)),
            is("(integer 23) != (real 2.3)"));
    }

    @Test
    public void valueCountMismatch_collections() {
        assertThat(
            sut.valueCountMismatch(
                Collections.singletonList(
                    new IntegerLiteral(23L, Position.UNKNOWN)),
                Arrays.asList(
                    new IntegerLiteral(23L, Position.UNKNOWN),
                    new IntegerLiteral(42L, Position.UNKNOWN))),
            is("1 != 2"));
    }

    @Test
    public void valueCountMismatch_maps() {
        final Map<IntegerLiteral, IntegerLiteral> a = new HashMap<>();
        a.put(
            new IntegerLiteral(23L, Position.UNKNOWN),
            new IntegerLiteral(23L, Position.UNKNOWN));
        final Map<IntegerLiteral, IntegerLiteral> b = new HashMap<>();
        b.put(
            new IntegerLiteral(23L, Position.UNKNOWN),
            new IntegerLiteral(23L, Position.UNKNOWN));
        b.put(
            new IntegerLiteral(42L, Position.UNKNOWN),
            new IntegerLiteral(42L, Position.UNKNOWN));

        assertThat(
            sut.valueCountMismatch(a, b),
            is("1 != 2"));
    }

    @Test
    public void difference_describable() {
        assertThat(
            sut.difference(
                new IntegerLiteral(23L, Position.UNKNOWN),
                new IntegerLiteral(42L, Position.UNKNOWN)),
            is("(integer 23) != (integer 42)"));
    }

    @Test
    public void difference_string() {
        assertThat(
            sut.difference(
                "foo",
                "bar"),
            is("foo != bar"));
    }

    @Test
    public void difference_number() {
        assertThat(
            sut.difference(
                23,
                42),
            is("23 != 42"));
    }
}
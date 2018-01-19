package de.weltraumschaf.caythe.intermediate.model;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link Position}.
 */
public class PositionTest {

    @Test
    public void toString_default() {
        assertThat(Position.UNKNOWN.toString(), is("Position(file='n/a', line=0, column=0)"));
    }

    @Test
    public void toString_shortFile() {
        assertThat(
            new Position("/foo/bar/baz.ct", 23, 42).toString(),
            is("Position(file='/foo/bar/baz.ct', line=23, column=42)"));
    }

    @Test
    public void toString_longFile() {
        assertThat(
            new Position("/de/weltraumschaf/caythe/frontend/transform/property_decl/OnePropertyWithCustomGetterNoSetter.ct", 23, 42).toString(),
            is("Position(file='/de/wel...operty_decl/OnePropertyWithCustomGetterNoSetter.ct', line=23, column=42)"));
    }
}
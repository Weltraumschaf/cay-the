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
        assertThat(Position.UNKNOWN.toString(), is("Position(line=0, column=0)"));
        assertThat(new Position(23, 42).toString(), is("Position(line=23, column=42)"));
    }

}
package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.caythe.intermediate.model.ast.BooleanLiteral;
import de.weltraumschaf.caythe.intermediate.model.ast.IntegerLiteral;
import de.weltraumschaf.caythe.intermediate.model.ast.RealLiteral;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link ModelDescriber}.
 */
public class ModelDescriberTest {

    private final ModelDescriber sut = new ModelDescriber();

    @Test
    public void describe_integerLiteral() {
        assertThat(
            sut.describe(new IntegerLiteral(42L, Position.UNKNOWN)),
            is("(integer 42)"));
    }

    @Test
    public void describe_realLiteral() {
        assertThat(
            sut.describe(new RealLiteral(3.14, Position.UNKNOWN)),
            is("(real 3.14)"));
    }

    @Test
    public void describe_booleanLiteral() {
        assertThat(
            sut.describe(new BooleanLiteral(true, Position.UNKNOWN)),
            is("(boolean true)"));
    }
}
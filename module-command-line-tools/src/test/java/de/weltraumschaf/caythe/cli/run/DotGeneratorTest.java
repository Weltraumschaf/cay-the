package de.weltraumschaf.caythe.cli.run;

import de.weltraumschaf.caythe.intermediate.experimental.ast.Unit;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link DotGenerator}.
 */
public final class DotGeneratorTest {
    private final DotGenerator sut = new DotGenerator();

    @Test
    public void getGraph_emptyByDefault() {
        assertThat(sut.getGraph(), is(""));
    }

    @Test
    public void visit_emptyUnit() {
        sut.visit(new Unit(Collections.emptyList()));
        assertThat(sut.getGraph(), is("graph AST {\n    Unit_0 [label=\"<unit>\"];\n}\n"));
    }
}

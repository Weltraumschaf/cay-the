package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.Position;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link AstNode}.
 */
public class AstNodeTest {
    private final AstNode sut = new AstNode() {
        @Override
        public String serialize() {
            return "";
        }
    };

    @Test
    public void sourcePosition() throws Exception {
        assertThat(sut.sourcePosition(), is(Position.UNKNOWN));
    }

    @Test
    public void accept() throws Exception {
        assertThat(sut.accept(null), is(nullValue()));
    }

}
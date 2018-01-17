package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.equivalence.Notification;
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
        public void probeEquivalence(final AstNode other, final Notification result) {

        }

        @Override
        public <R> R accept(final AstVisitor<? extends R> visitor) {
            return null;
        }

        @Override
        public String getNodeName() {
            return "";
        }

        @Override
        public String serialize() {
            return "";
        }
    };

    @Test
    public void sourcePosition() throws Exception {
        assertThat(sut.getSourcePosition(), is(Position.UNKNOWN));
    }

}
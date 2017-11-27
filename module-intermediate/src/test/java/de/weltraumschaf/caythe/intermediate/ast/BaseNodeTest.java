package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Tests for {@link BaseNode}.
 */
public class BaseNodeTest {

    private final BaseNode sut = new BaseNodeStub();

    @Test(expected = NullPointerException.class)
    public void serialize_nameMustNotBeNull() {
        sut.serialize((String) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void serialize_nameMustNotBeEmpty() {
        sut.serialize("");
    }

    @Test
    public void serialize_withoutNoAdditional() {
        assertThat(sut.serialize("foo"), is("(foo [1:2])"));
    }

    @Test
    public void serialize_withAdditional() {
        assertThat(
            sut.serialize("foo", "add"),
            is("(foo add [1:2])"));
    }

    private static final class BaseNodeStub extends BaseNode {

        BaseNodeStub() {
            super(new Position(1, 2));
        }

        @Override
        public String serialize() {
            return serialize(getNodeName());
        }

        @Override
        public String getNodeName() {
            return "bar";
        }

        @Override
        public <R> R accept(final AstVisitor<? extends R> visitor) {
            return null;
        }
    }
}
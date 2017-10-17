package de.weltraumschaf.caythe.intermediate.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link Coordinate}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class CoordinateTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Coordinate.class).verify();
    }

    @Test
    public void toLiteral() {
        final Coordinate sut = new Coordinate(
            "de.weltraumschaf",
            "test-module",
            new Version(1, 2, 3));

        assertThat(sut.toLiteral(), is("de.weltraumschaf:test-module:1.2.3"));
    }
}

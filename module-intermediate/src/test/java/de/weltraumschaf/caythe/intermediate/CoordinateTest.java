package de.weltraumschaf.caythe.intermediate;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

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

}

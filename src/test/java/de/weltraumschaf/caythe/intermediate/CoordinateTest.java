package de.weltraumschaf.caythe.intermediate;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Tests for {@link Coordinate}.
 */
public class CoordinateTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Coordinate.class).verify();
    }

}

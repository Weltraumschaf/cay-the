package de.weltraumschaf.caythe.intermediate;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Tests for {@link Version}.
 */
public class VersionTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Version.class).verify();
    }
}

package de.weltraumschaf.caythe.intermediate;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Tests for {@link Manifest}.
 */
public class ManifestTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Manifest.class).verify();
    }
}

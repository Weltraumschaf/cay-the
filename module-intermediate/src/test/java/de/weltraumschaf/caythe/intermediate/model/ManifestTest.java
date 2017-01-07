package de.weltraumschaf.caythe.intermediate.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Tests for {@link Manifest}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class ManifestTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Manifest.class).verify();
    }
}

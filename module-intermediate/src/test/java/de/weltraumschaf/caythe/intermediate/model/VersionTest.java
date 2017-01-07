package de.weltraumschaf.caythe.intermediate.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Tests for {@link Version}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class VersionTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Version.class).verify();
    }
}

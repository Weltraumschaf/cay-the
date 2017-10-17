package de.weltraumschaf.caythe.intermediate.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

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

    @Test
    public void toLiteral_withoutIdentifiers() {
        final Version sut = new Version(1, 2, 3);

        assertThat(sut.toLiteral(), is("1.2.3"));
    }

    @Test
    public void toLiteral_withIdentifiers() {
        final Version sut = new Version(1, 2, 3, "SNAPSHOT");

        assertThat(sut.toLiteral(), is("1.2.3-SNAPSHOT"));
    }
}

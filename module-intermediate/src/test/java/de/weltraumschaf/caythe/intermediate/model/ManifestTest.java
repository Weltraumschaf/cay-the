package de.weltraumschaf.caythe.intermediate.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static de.weltraumschaf.caythe.intermediate.model.Version.version;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

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

    @Test
    public void equalsForImports() {
        final Manifest a = new Manifest(
            new Coordinate("g", "a", version(1,0,0)), "n", Arrays.asList(
                new Coordinate("g1", "a1", version(2, 0, 0)),
                new Coordinate("g2", "a2", version(3, 0, 0))
        ));
        final Manifest b = new Manifest(
            new Coordinate("g", "a", version(1,0,0)), "n", Arrays.asList(
                new Coordinate("g1", "a1", version(2, 0, 0)),
                new Coordinate("g2", "a2", version(3, 0, 0))
        ));
        final Manifest c = new Manifest(
            new Coordinate("g", "a", version(1,0,0)), "n", Collections.emptyList());

        assertThat(a, is(b));
        assertThat(b, is(a));
        assertThat(a, is(not(c)));
        assertThat(b, is(not(c)));
    }
}


package de.weltraumschaf.caythe;

import static org.hamcrest.Matchers.is;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for {@link CayThe}.
 */
public class CayTheTest {

    @Test
    public void BASE_PACKAGE_DIR() {
        assertThat(CayThe.BASE_PACKAGE_DIR, is("/de/weltraumschaf/caythe"));
    }

}
package de.weltraumschaf.caythe.testing;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link AstSpecification}.
 */
public class AstSpecificationTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(AstSpecification.class).verify();
    }
}
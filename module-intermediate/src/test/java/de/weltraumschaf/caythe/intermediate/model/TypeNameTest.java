package de.weltraumschaf.caythe.intermediate.model;

import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link TypeName}.
 */
public class TypeNameTest {

    @Test
    public void fromFileName() throws Exception {
        assertThat(
            TypeName.fromFileName(Paths.get("foo/bar/baz/Snafu.ct")),
                is(new TypeName("foo.bar.baz", "Snafu")));
    }

    @Test
    public void getFullQualifiedName() {
        assertThat(
            new TypeName("foo.bar.baz", "Snafu").getFullQualifiedName(),
            is("foo.bar.baz.Snafu"));
    }

    @Test
    public void removeFileExtension() {
        assertThat(TypeName.removeFileExtension("foo/bar/baz/Snafu.ct"), is("foo/bar/baz/Snafu"));
    }
}
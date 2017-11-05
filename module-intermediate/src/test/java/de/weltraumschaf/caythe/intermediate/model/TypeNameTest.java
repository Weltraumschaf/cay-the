package de.weltraumschaf.caythe.intermediate.model;

import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link TypeName}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public class TypeNameTest {

    @Test
    public void fromFileName_noLeadingSlash() throws Exception {
        assertThat(
            TypeName.fromFileName(Paths.get("foo/bar/baz/Snafu.ct")),
            is(new TypeName("foo.bar.baz", "Snafu")));
    }

    @Test
    public void fromFileName_leadingSlash() throws Exception {
        assertThat(
            TypeName.fromFileName(Paths.get("/foo/bar/baz/Snafu.ct")),
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

    @Test
    public void removeLeadingSlash_noLeadingSlash() {
        assertThat(TypeName.removeLeadingSlash("foo/bar/baz/Snafu.ct"), is("foo/bar/baz/Snafu.ct"));
    }

    @Test
    public void removeLeadingSlash_leadingSlash() {
        assertThat(TypeName.removeLeadingSlash("/foo/bar/baz/Snafu.ct"), is("foo/bar/baz/Snafu.ct"));
    }

    @Test
    public void replaceDirectorySeparator_unixSeparator() {
        assertThat(TypeName.replaceDirectorySeparator("foo/bar/baz/Snafu"), is("foo.bar.baz.Snafu"));
    }

    @Test
    public void replaceDirectorySeparator_windowsSeparator() {
        assertThat(TypeName.replaceDirectorySeparator("foo\\bar\\baz\\Snafu"), is("foo.bar.baz.Snafu"));
    }

    @Test
    public void fromFullQualifiedName() {
        assertThat(
            TypeName.fromFullQualifiedName("org.caythe.core.basetypes.Object"),
            is(new TypeName("org.caythe.core.basetypes", "Object")));
    }
}
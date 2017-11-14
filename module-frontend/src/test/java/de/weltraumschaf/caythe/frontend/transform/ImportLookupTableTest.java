package de.weltraumschaf.caythe.frontend.transform;

import de.weltraumschaf.caythe.intermediate.model.Import;
import de.weltraumschaf.caythe.intermediate.model.TypeName;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link ImportLookupTable}.
 */
public class ImportLookupTableTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private final ImportLookupTable sut = new ImportLookupTable();

    @Test
    public void countIsZeroByDefault() {
        assertThat(sut.count(), is(0));
        assertThat(sut.has("Foo"), is(false));
    }

    @Test
    public void addImport() {
        sut.add(new Import(new TypeName("org.snafu", "Foo")));

        assertThat(sut.count(), is(1));
        assertThat(sut.has("Foo"), is(true));
    }

    @Test
    public void addImportTwice() {
        sut.add(new Import(new TypeName("org.snafu", "Foo")));

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Duplicate import for type 'org.snafu.Foo'!");

        sut.add(new Import(new TypeName("org.snafu", "Foo")));
    }

    @Test
    public void addImportConflictingBaseName() {
        sut.add(new Import(new TypeName("org.snafu", "Foo")));

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("For name 'Foo' is already a type 'org.snafu.Foo' imported!");

        sut.add(new Import(new TypeName("org.foobar", "Foo")));
    }

    @Test
    public void getNonExistent() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("There is no type with base name 'Foo' imported!");

        sut.get("Foo");
    }
}
package de.weltraumschaf.caythe.frontend.transform;

import de.weltraumschaf.caythe.intermediate.ast.AstNode;
import de.weltraumschaf.caythe.intermediate.model.*;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link SourceToIntermediateTransformer}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public class SourceToIntermediateTransformer_PropertyDeclarationTest extends TransformVisitorTestCase {

    private static final String FIXTURE_DIR = "/property_decl";
    private static final TypeName TYPE_STRING = new TypeName("org.caythe.core.basetypes", "String");
    private static final TypeName TYPE_INTEGER = new TypeName("org.caythe.core.basetypes", "Integer");
    private static final TypeName TYPE_FLOAT = new TypeName("org.caythe.core.basetypes", "Float");

    @Test
    public void noProperties() throws IOException {
        final String file = createFixtureFile(FIXTURE_DIR + "/NoProperty.ct");
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseFile(file));

        assertThat(result.getProperties(), hasSize(0));
    }

    @Test
    public void onePropertyWithOutGetterAndSetter() throws IOException {
        final String file = createFixtureFile(FIXTURE_DIR + "/OnePropertyNoGetterNoSetter.ct");
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseFile(file));

        assertThat(result.getProperties(), hasSize(1));
        assertThat(result.getProperties(), containsInAnyOrder(
            new Property("foo", Visibility.PRIVATE, new TypeName("org.caythe.core.basetypes", "String"))
        ));
    }

    @Test
    public void threePropertyWithOutGetterAndSetter() throws IOException {
        final String file = createFixtureFile(FIXTURE_DIR + "/ThreePropertyNoGetterNoSetter.ct");
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseFile(file));

        assertThat(result.getProperties(), hasSize(3));
        assertThat(result.getProperties(), containsInAnyOrder(
            new Property("foo", Visibility.PRIVATE, TYPE_STRING),
            new Property("bar", Visibility.PUBLIC, TYPE_INTEGER),
            new Property("baz", Visibility.EXPORT, TYPE_FLOAT)
        ));
    }

    @Test
    public void onePropertyWithGetter() throws IOException {
        final String file = createFixtureFile(FIXTURE_DIR + "/OnePropertyWithGetter.ct");
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseFile(file));

        assertThat(result.getProperties(), hasSize(1));
        assertThat(result.getProperties(), containsInAnyOrder(
            new Property("foo", Visibility.PUBLIC, TYPE_STRING,
                Property.defaultGetter("foo", Visibility.PUBLIC, TYPE_STRING),
                Method.NONE)
        ));
    }

    @Test
    public void onePropertyWithSetter() throws IOException {
        final String file = createFixtureFile(FIXTURE_DIR + "/OnePropertyWithSetter.ct");
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseFile(file));

        assertThat(result.getProperties(), hasSize(1));
        assertThat(result.getProperties(), containsInAnyOrder(
            new Property("foo", Visibility.PUBLIC, TYPE_STRING,
                Method.NONE,
                Property.defaultSetter("foo", Visibility.PUBLIC, TYPE_STRING))
        ));
    }

    @Test
    public void onePropertyWithGetterAndSetter() throws IOException {
        final String file = createFixtureFile(FIXTURE_DIR + "/OnePropertyWithGetterSetter.ct");
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseFile(file));

        assertThat(result.getProperties(), hasSize(1));
        assertThat(result.getProperties(), containsInAnyOrder(
            new Property("foo", Visibility.PUBLIC, TYPE_STRING,
                Property.defaultGetter("foo", Visibility.PUBLIC, TYPE_STRING),
                Property.defaultSetter("foo", Visibility.PUBLIC, TYPE_STRING))
        ));
    }

    @Test
    public void threePropertiesWithGetterAndSetter() throws IOException {
        final String file = createFixtureFile(FIXTURE_DIR + "/ThreePropertyWithGetterSetter.ct");
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseFile(file));

        assertThat(result.getProperties(), hasSize(3));
        assertThat(result.getProperties(), containsInAnyOrder(
            new Property("foo", Visibility.PRIVATE, TYPE_STRING,
                Property.defaultGetter("foo", Visibility.PRIVATE, TYPE_STRING),
                Method.NONE),
            new Property("bar", Visibility.PUBLIC, TYPE_INTEGER,
                Property.defaultGetter("bar", Visibility.PUBLIC, TYPE_INTEGER),
                Property.defaultSetter("bar", Visibility.PUBLIC, TYPE_INTEGER)),
            new Property("baz", Visibility.EXPORT, TYPE_FLOAT,
                Method.NONE,
                Property.defaultSetter("baz", Visibility.EXPORT, TYPE_FLOAT))
        ));
    }

    @Test
    public void onePropertyWithCustomGetterNoSetter() throws IOException {
        final String file = createFixtureFile(FIXTURE_DIR + "/OnePropertyWithCustomGetterNoSetter.ct");
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseFile(file));

        assertThat(result.getProperties(), hasSize(1));
        fail("Add assertion for method body of custom getter.");
    }

    @Test
    @Ignore("TODO")
    public void onePropertyWithCustomGetterDefaultSetter() {}

    @Test
    @Ignore("TODO")
    public void onePropertyWithCustomSetterNoGetter() {}

    @Test
    @Ignore("TODO")
    public void onePropertyWithCustomSetterDefaultGetter() {}

    @Test
    @Ignore("TODO")
    public void onePropertyWithCustomGetterCustomSetter() {}

}

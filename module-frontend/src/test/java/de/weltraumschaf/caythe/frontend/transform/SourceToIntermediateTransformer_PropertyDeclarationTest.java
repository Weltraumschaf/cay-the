package de.weltraumschaf.caythe.frontend.transform;

import de.weltraumschaf.caythe.intermediate.model.Position;
import de.weltraumschaf.caythe.intermediate.model.ast.AstNode;
import de.weltraumschaf.caythe.intermediate.model.*;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static de.weltraumschaf.caythe.frontend.transform.IntermediateModelIsEquivalent.equivalent;
import static de.weltraumschaf.caythe.intermediate.model.ast.builder.BinaryOperationFactory.addition;
import static de.weltraumschaf.caythe.intermediate.model.ast.builder.BlockBuilder.block;
import static de.weltraumschaf.caythe.intermediate.model.ast.builder.LiteralFactory.identifier;
import static de.weltraumschaf.caythe.intermediate.model.ast.builder.LiteralFactory.integer;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

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
    private static final TypeName TYPE_REAL = new TypeName("org.caythe.core.basetypes", "Real");

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
            new Property("baz", Visibility.EXPORT, TYPE_REAL)
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
            new Property("baz", Visibility.EXPORT, TYPE_REAL,
                Method.NONE,
                Property.defaultSetter("baz", Visibility.EXPORT, TYPE_REAL))
        ));
    }

    @Test
    @Ignore("TODO")
    public void onePropertyWithCustomGetterNoSetter() throws IOException {
        final String file = createFixtureFile(FIXTURE_DIR + "/OnePropertyWithCustomGetterNoSetter.ct");
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseFile(file));

        assertThat(result.getProperties(), hasSize(1));
        final AstNode getterBody =
            block(file, 6, 8)
                .statements()
                .returnStatement(
                    addition(
                        identifier("foo", new Position(file, 7, 15)),
                        integer(42L, new Position(file, 7, 21)),
                        new Position(file, 7, 19)),
                    7, 8)
                .end()
                .end();

        final Property expected = new Property("foo", Visibility.PUBLIC, TYPE_INTEGER,
            Property.customGetter("foo", Visibility.PUBLIC, TYPE_INTEGER, getterBody),
            Method.NONE);

        assertThat(result.getProperties(), containsInAnyOrder(expected));
        assertThat(result.getProperty(0), equivalent(expected));
    }

    @Test
    @Ignore("TODO")
    public void onePropertyWithCustomGetterDefaultSetter() throws IOException {
        final String file = createFixtureFile(FIXTURE_DIR + "/OnePropertyWithCustomGetterDefaultSetter.ct");
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseFile(file));
    }

    @Test
    @Ignore("TODO")
    public void onePropertyWithCustomSetterNoGetter() throws IOException {
        final String file = createFixtureFile(FIXTURE_DIR + "/OnePropertyWithCustomSetterNoGetter.ct");
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseFile(file));
    }

    @Test
    @Ignore("TODO")
    public void onePropertyWithCustomSetterDefaultGetter() throws IOException {
        final String file = createFixtureFile(FIXTURE_DIR + "/OnePropertyWithCustomSetterDefaultGetter.ct");
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseFile(file));
    }

    @Test
    @Ignore("TODO")
    public void onePropertyWithCustomGetterCustomSetter() throws IOException {
        final String file = createFixtureFile(FIXTURE_DIR + "/OnePropertyWithCustomGetterCustomSetter.ct");
        final SourceToIntermediateTransformer sut = createSut(file);

        final Type result = sut.visit(parseFile(file));
    }

}

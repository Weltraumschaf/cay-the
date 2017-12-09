package de.weltraumschaf.caythe.frontend.transform;

import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.caythe.intermediate.ast.AstNode;
import de.weltraumschaf.caythe.intermediate.model.*;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static de.weltraumschaf.caythe.intermediate.ast.builder.BinaryOperationFactory.addition;
import static de.weltraumschaf.caythe.intermediate.ast.builder.BlockBuilder.block;
import static de.weltraumschaf.caythe.intermediate.ast.builder.LiteralFactory.identifier;
import static de.weltraumschaf.caythe.intermediate.ast.builder.LiteralFactory.integer;
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
                        identifier("foo", new Position(file, 7, 16)),
                        integer(42L, new Position(file, 7, 22)),
                        new Position(file, 7, 20)),
                    7, 9)
                .end()
                .end();

        assertThat(result.getProperties(), containsInAnyOrder(
            new Property("foo", Visibility.PUBLIC, TYPE_INTEGER,
                Property.customGetter("foo", Visibility.PUBLIC, TYPE_INTEGER, getterBody),
                Method.NONE)));
    }

    @Test
    @Ignore("TODO")
    public void onePropertyWithCustomGetterDefaultSetter() {
    }

    @Test
    @Ignore("TODO")
    public void onePropertyWithCustomSetterNoGetter() {
    }

    @Test
    @Ignore("TODO")
    public void onePropertyWithCustomSetterDefaultGetter() {
    }

    @Test
    @Ignore("TODO")
    public void onePropertyWithCustomGetterCustomSetter() {
    }

}

package de.weltraumschaf.caythe.frontend.transform;

import de.weltraumschaf.caythe.frontend.CayTheManifestBaseVisitor;
import de.weltraumschaf.caythe.frontend.CayTheManifestParser;
import de.weltraumschaf.caythe.frontend.SyntaxError;
import de.weltraumschaf.caythe.intermediate.model.Coordinate;
import de.weltraumschaf.caythe.intermediate.model.Manifest;
import de.weltraumschaf.caythe.intermediate.model.Version;

import de.weltraumschaf.commons.validate.Validate;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

/**
 * Default implementation which converts the parsed tree from a manifest file into intermediate model.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class ManifestToIntermediateTransformer extends CayTheManifestBaseVisitor<Manifest> {

    private static final Function<String, String> MISSING_DIRECTIVE =
        (String missingDirective) -> String.format(
            "There is no %s declared in the manifest! A %s directive is mandatory.",
            missingDirective,
            missingDirective);

    private static final String EMPTY_LITERAL = "";
    private String group = EMPTY_LITERAL;
    private String artifact = EMPTY_LITERAL;
    private Version version = Version.NONE;
    private String namespace = EMPTY_LITERAL;
    private Collection<Coordinate> imports = new ArrayList<>();


    @Override
    protected Manifest defaultResult() {
        return Manifest.NONE;
    }

    @Override
    public Manifest visitManifest(CayTheManifestParser.ManifestContext ctx) {
        super.visitManifest(ctx);

        if (EMPTY_LITERAL.equals(group)) {
            throw SyntaxError.newError(MISSING_DIRECTIVE.apply("group"));
        }

        if (EMPTY_LITERAL.equals(artifact)) {
            throw SyntaxError.newError(MISSING_DIRECTIVE.apply("artifact"));
        }

        if (Version.NONE.equals(version)) {
            throw SyntaxError.newError(MISSING_DIRECTIVE.apply("version"));
        }

        if (EMPTY_LITERAL.equals(namespace)) {
            throw SyntaxError.newError(MISSING_DIRECTIVE.apply("namespace"));
        }

        return new Manifest(new Coordinate(group, artifact, version), namespace, imports);
    }

    @Override
    public Manifest visitGroupDirective(CayTheManifestParser.GroupDirectiveContext ctx) {
        if (EMPTY_LITERAL.equals(group)) {
            group = ctx.fullQualifiedName().getText();
        } else {
            throw error(ctx.start, "Redefined group!");
        }

        return defaultResult();
    }

    @Override
    public Manifest visitArtifactDirective(CayTheManifestParser.ArtifactDirectiveContext ctx) {
        if (EMPTY_LITERAL.equals(artifact)) {
            artifact = ctx.fullQualifiedName().getText();
        } else {
            throw error(ctx.start, "Redefined artifact!");
        }

        return defaultResult();
    }

    @Override
    public Manifest visitNamespaceDirective(CayTheManifestParser.NamespaceDirectiveContext ctx) {
        if (EMPTY_LITERAL.equals(namespace)) {
            namespace = ctx.fullQualifiedName().getText();
        } else {
            throw error(ctx.start, "Redefined namespace!");
        }

        return defaultResult();
    }

    @Override
    public Manifest visitVersionDirective(CayTheManifestParser.VersionDirectiveContext ctx) {
        if (Version.NONE.equals(version)) {
            CayTheManifestParser.VersionContext v = ctx.version();
            final String identifiers =
                v.identifiers == null ?
                    EMPTY_LITERAL :
                    v.identifiers.getText();
            version = new Version(
                Integer.parseInt(v.major.getText()),
                Integer.parseInt(v.minor.getText()),
                Integer.parseInt(v.patch.getText()),
                identifiers
            );
        } else {
            throw error(ctx.start, "Redefined version!");
        }

        return defaultResult();
    }

    @Override
    public Manifest visitImportDirective(CayTheManifestParser.ImportDirectiveContext ctx) {
        CayTheManifestParser.CoordinateContext coordinate = ctx.coordinate();
        final CayTheManifestParser.VersionContext v = coordinate.version();
        final String identifiers =
            v.identifiers == null ?
                EMPTY_LITERAL :
                v.identifiers.getText();
        imports.add(new Coordinate(
            coordinate.group.getText(),
            coordinate.artifact.getText(),
            new Version(
                Integer.parseInt(v.major.getText()),
                Integer.parseInt(v.minor.getText()),
                Integer.parseInt(v.patch.getText()),
                identifiers)
        ));
        return defaultResult();
    }

    private SyntaxError error(final Token token, final String message, final Object... args) {
        Validate.notNull(token, "token");
        Validate.notEmpty(message, "message");
        return SyntaxError.newError(
            String.format(message, args)
                + String.format(" (at line %d, column %d)", token.getLine(), token.getCharPositionInLine() + 1));
    }

    @Override
    public String toString() {
        return "ManifestToIntermediateTransformer{" +
            "group=" + group +
            ", artifact=" + artifact +
            ", namespace=" + namespace +
            '}';
    }
}

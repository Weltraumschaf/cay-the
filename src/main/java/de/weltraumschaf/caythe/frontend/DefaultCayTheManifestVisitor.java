package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.caythe.intermediate.Coordinate;
import de.weltraumschaf.caythe.intermediate.Manifest;
import de.weltraumschaf.caythe.intermediate.Version;
import de.weltraumschaf.commons.validate.Validate;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation which converts the parsed tree from a manifest file into intermediate model.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class DefaultCayTheManifestVisitor extends CayTheManifestBaseVisitor<Manifest> {

    private static final String NO_LITERAL = "";
    private String group = NO_LITERAL;
    private String artifact = NO_LITERAL;
    private Version version = Version.NULL;
    private String namespace = NO_LITERAL;
    private Collection<Coordinate> imports = new ArrayList<>();

    @Override
    protected Manifest defaultResult() {
        return Manifest.NULL;
    }

    @Override
    public Manifest visitManifest(CayTheManifestParser.ManifestContext ctx) {
        super.visitManifest(ctx);
        /*
            TODO Validation:
            - missing: group, artifact, namespace, version.
            - version must not be 0.0.0
            - imports
                - no duplicates
                - versions > 0.0.0
         */
        return new Manifest(new Coordinate(group, artifact, version), namespace, imports);
    }

    @Override
    public Manifest visitGroupDirective(CayTheManifestParser.GroupDirectiveContext ctx) {
        if (NO_LITERAL.equals(group)) {
            group = ctx.fullQualifiedName().getText();
        } else {
            throw error(ctx.start, "Redefined group!");
        }

        return super.visitGroupDirective(ctx);
    }

    @Override
    public Manifest visitArtifactDirective(CayTheManifestParser.ArtifactDirectiveContext ctx) {
        if (NO_LITERAL.equals(artifact)) {
            artifact = ctx.fullQualifiedName().getText();
        } else {
            throw error(ctx.start, "Redefined artifact!");
        }

        return super.visitArtifactDirective(ctx);
    }

    @Override
    public Manifest visitNamespaceDirective(CayTheManifestParser.NamespaceDirectiveContext ctx) {
        if (NO_LITERAL.equals(namespace)) {
            namespace = ctx.fullQualifiedName().getText();
        } else {
            throw error(ctx.start, "Redefined namespace!");
        }

        return super.visitNamespaceDirective(ctx);
    }

    @Override
    public Manifest visitVersionDirective(CayTheManifestParser.VersionDirectiveContext ctx) {
        if (Version.NULL.equals(version)) {
            CayTheManifestParser.VersionContext v = ctx.version();
            version = new Version(
                Integer.parseInt(v.major.getText()),
                Integer.parseInt(v.minor.getText()),
                Integer.parseInt(v.patch.getText())
            );
        } else {
            throw error(ctx.start, "Redefined version!");
        }

        return super.visitVersionDirective(ctx);
    }

    @Override
    public Manifest visitImportDirective(CayTheManifestParser.ImportDirectiveContext ctx) {
        final CayTheManifestParser.VersionContext v = ctx.version();
        imports.add(new Coordinate(
            ctx.group.getText(),
            ctx.artifact.getText(),
            new Version(
                Integer.parseInt(v.major.getText()),
                Integer.parseInt(v.minor.getText()),
                Integer.parseInt(v.patch.getText()))
        ));
        return super.visitImportDirective(ctx);
    }

    private SyntaxError error(final Token token, final String message, final Object ... args) {
        Validate.notNull(token, "token");
        Validate.notEmpty(message, "message");
        return new SyntaxError(
            String.format(message, args)
                + String.format(" (at line %d, column %d)", token.getLine(), token.getCharPositionInLine() + 1));
    }

    @Override
    public String toString() {
        return "DefaultCayTheManifestVisitor{" +
            "group=" + group +
            ", artifact=" + artifact +
            ", namespace=" + namespace +
            '}';
    }
}

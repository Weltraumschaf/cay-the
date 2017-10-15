package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.caythe.intermediate.model.Manifest;

/**
 * Transforms the AST from a manifest fie to the intermediate format.
 */
public final class ManifestToIntermediateTransformer  extends CayTheManifestBaseVisitor<Manifest> {
    @Override
    protected Manifest defaultResult() {
        return Manifest.NULL;
    }
}

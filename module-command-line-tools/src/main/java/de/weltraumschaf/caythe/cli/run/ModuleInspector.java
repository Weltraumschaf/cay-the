package de.weltraumschaf.caythe.cli.run;

import de.weltraumschaf.caythe.intermediate.model.Coordinate;
import de.weltraumschaf.caythe.intermediate.model.Manifest;
import de.weltraumschaf.caythe.intermediate.model.Module;
import de.weltraumschaf.commons.application.IO;
import org.stringtemplate.v4.ST;

import java.util.stream.Collectors;

/**
 * Inspects the given module to gather its information.
 */
final class ModuleInspector {
    private static final String MANIFEST_INFO_TEMPLATE
        = "group     : <group>\n"
        + "artifact  : <artifact>\n"
        + "version   : <version>\n"
        + "namespace : <namespace>\n"
        + "imports   : <if(imports)>"
        + "<imports:{ import | <import>\n}>"
        + "<else>"
        + "-\n"
        + "<endif>";

    void inspect(final Module module, final IO io) {
        dumpManifestInfo(module.getManifest(), io);
    }

    void dumpManifestInfo(final Manifest manifest, final IO io) {
        final ST info = new ST(MANIFEST_INFO_TEMPLATE);
        final Coordinate moduleCoordinate = manifest.getCoordinate();
        info.add("group", moduleCoordinate.getGroup());
        info.add("artifact", moduleCoordinate.getArtifact());
        info.add("version", moduleCoordinate.getVersion().toLiteral());
        info.add("namespace", manifest.getNamespace());
        info.add("imports", manifest.getImports().stream().map(Coordinate::toLiteral).collect(Collectors.toList()));
        io.print(info.render());
    }
}

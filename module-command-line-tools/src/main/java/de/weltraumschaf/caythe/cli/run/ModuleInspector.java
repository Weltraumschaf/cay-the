package de.weltraumschaf.caythe.cli.run;

import de.weltraumschaf.caythe.intermediate.model.Coordinate;
import de.weltraumschaf.caythe.intermediate.model.Manifest;
import de.weltraumschaf.caythe.intermediate.model.Module;
import de.weltraumschaf.commons.application.IO;
import de.weltraumschaf.commons.validate.Validate;
import org.stringtemplate.v4.ST;

import java.nio.file.Path;
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
        + "\n<imports:{ import |   <import>\n}>"
        + "<else>"
        + "-\n"
        + "<endif>";

    private final IO io;

    ModuleInspector(final IO io) {
        super();
        this.io = Validate.notNull(io, "io");
    }

    void inspect(final Module module, final Path moduleDir) {
        io.print(preamble(moduleDir));
        io.print(dumpManifestInfo(module.getManifest()));
        io.println("");
    }

    String preamble(final Path moduleDir) {
        final StringBuilder buffer = new StringBuilder();

        buffer.append("Inspecting Module\n");
        buffer.append("=================\n");
        buffer.append("\n");
        buffer.append("Location : ").append(moduleDir).append("\n");
        buffer.append("\n");

        return buffer.toString();
    }

    String dumpManifestInfo(final Manifest manifest) {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("Manifest\n");
        buffer.append("--------\n");
        buffer.append("\n");

        final ST info = new ST(MANIFEST_INFO_TEMPLATE);
        final Coordinate moduleCoordinate = manifest.getCoordinate();
        info.add("group", moduleCoordinate.getGroup());
        info.add("artifact", moduleCoordinate.getArtifact());
        info.add("version", moduleCoordinate.getVersion().toLiteral());
        info.add("namespace", manifest.getNamespace());
        info.add("imports", manifest.getImports().stream().map(Coordinate::toLiteral).collect(Collectors.toList()));
        buffer.append(info.render());

        return buffer.toString();
    }
}

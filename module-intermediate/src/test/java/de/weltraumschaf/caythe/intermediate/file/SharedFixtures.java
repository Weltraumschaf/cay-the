package de.weltraumschaf.caythe.intermediate.file;

import de.weltraumschaf.caythe.intermediate.model.Coordinate;
import de.weltraumschaf.caythe.intermediate.model.Manifest;
import de.weltraumschaf.caythe.intermediate.model.Module;

import java.util.Arrays;
import java.util.Collections;

import de.weltraumschaf.caythe.intermediate.model.Version;

/**
 * Some shared fixtures.
 */
final class SharedFixtures {
    static final Manifest MANIFEST_FIXTURE =
        new Manifest(new Coordinate("de.weltraumschaf", "caythe", new Version(1, 0, 0)), "de.weltraumschaf.caythe",
            Arrays.asList(new Coordinate("com.foobar", "baz", new Version(1, 2, 3)),
                new Coordinate("org.foobar", "snafu", new Version(3, 0, 0))));
    static final Module MODULE_FIXTURE = new Module(MANIFEST_FIXTURE, Collections.emptyList(), Collections.emptyList());
    static final String JSON_FIXTURE =
        "{\"manifest\":{\"coordinate\":{\"group\":\"de.weltraumschaf\",\"artifact\":\"caythe\",\"version\":{\"major\":1,\"minor\":0,\"patch\":0,\"identifiers\":\"\"}},\"namespace\":\"de.weltraumschaf.caythe\",\"imports\":[{\"group\":\"com.foobar\",\"artifact\":\"baz\",\"version\":{\"major\":1,\"minor\":2,\"patch\":3,\"identifiers\":\"\"}},{\"group\":\"org.foobar\",\"artifact\":\"snafu\",\"version\":{\"major\":3,\"minor\":0,\"patch\":0,\"identifiers\":\"\"}}]},\"units\":[],\"resources\":[]}";
}

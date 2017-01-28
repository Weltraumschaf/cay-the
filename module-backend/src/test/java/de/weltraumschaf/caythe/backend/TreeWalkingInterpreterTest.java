package de.weltraumschaf.caythe.backend;

import java.io.IOException;

import de.weltraumschaf.caythe.CayThe;
import de.weltraumschaf.caythe.frontend.CayTheSourceParser;
import de.weltraumschaf.caythe.frontend.CayTheSourceVisitor;
import de.weltraumschaf.caythe.frontend.Parsers;
import org.junit.Test;

import de.weltraumschaf.caythe.backend.experimental.types.ObjectType;

/**
 * Tests for {@link TreeWalkingInterpreter}.
 */
public class TreeWalkingInterpreterTest {
    private final Parsers parsers = new Parsers();

    @SuppressWarnings("unchecked")
    private final CayTheSourceVisitor<ObjectType> sut = new TreeWalkingInterpreter();

    @Test
    public void file() throws IOException {
        final CayTheSourceParser parser = parsers.newSourceParser(
            getClass().getResourceAsStream(CayThe.BASE_PACKAGE_DIR + "/backend/test.ct"));

        sut.visit(parser.unit());
    }
}

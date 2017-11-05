package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.backend.types.NullType;
import de.weltraumschaf.caythe.backend.types.ObjectType;
import de.weltraumschaf.caythe.frontend.CayTheSourceParser;
import de.weltraumschaf.caythe.frontend.Parsers;
import de.weltraumschaf.caythe.frontend.SourceToIntermediateTransformer;
import de.weltraumschaf.caythe.intermediate.model.Type;
import de.weltraumschaf.caythe.intermediate.model.TypeName;
import de.weltraumschaf.commons.testing.rules.CapturedOutput;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link AstWalkingInterpreter}
 */
@Ignore
public final class AstWalkingInterpreterTest {
    @Rule
    public final CapturedOutput output = new CapturedOutput();
    private final Parsers parsers = new Parsers();
    private final AstWalkingInterpreter sut = new AstWalkingInterpreter();

    private Type parse(final String src ) throws IOException {
        final CayTheSourceParser parser = parsers.newSourceParser(new ByteArrayInputStream(src.getBytes()));
        return new SourceToIntermediateTransformer(Paths.get("Type.ct")).visit(parser.type());
    }

    @Test
    public void functionWithLoopWhichReturns() throws IOException {
        output.expectOut("6\n");

        final String src = "\n" +
            "let j = 3\n" +
            "\n" +
            "let looper = fn() {\n" +
            "    let j = 0\n" +
            "\n" +
            "    loop {\n" +
            "        if j > 5 {\n" +
            "\n" +
            "            return j\n" +
            "        }\n" +
            "\n" +
            "        j = j + 1\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "puts(looper())\n";

//        final ObjectType result = parse(src).accept(new AstWalkingInterpreter());

//        assertThat(result, is(not(nullValue())));
    }

    @Test
    public void returnInUnit() throws IOException {
        output.expectOut("foo");
        output.expectOut(not("bar"));

        final String src = "\n" +
            "puts(\"foo\")\n" +
            "return\n" +
            "puts(\"bar\")\n";

//        final ObjectType result = parse(src).accept(new AstWalkingInterpreter());

//        assertThat(result, is(NullType.NULL));
    }

    @Test
    public void returnInFunction() throws IOException {
        output.expectOut("5");

        final String src = "\n" +
            "let addition = fn(a, b) {\n" +
            "    return a + b\n" +
            "}\n" +
            "\n" +
            "puts(addition(2, 3))\n";

//        final ObjectType result = parse(src).accept(new AstWalkingInterpreter());

//        assertThat(result, is(NullType.NULL));
    }

    @Test
    public void returnNestedInIfInFunction() throws IOException {
        output.expectOut("5\n7\n");

        final String src = "\n" +
            "let max = fn(a, b) {\n" +
            "    if a > b {\n" +
            "        return a\n" +
            "    }\n" +
            "\n" +
            "    return b\n" +
            "}\n" +
            "\n" +
            "puts(max(5, 3))\n" +
            "puts(max(5, 7))\n";

//        final ObjectType result = parse(src).accept(new AstWalkingInterpreter());

//        assertThat(result, is(NullType.NULL));
    }

    @Test
    public void returnNestedInLoopInIfInFunction() throws IOException {
        output.expectOut("5\n");

        final String src = "\n" +
            "let looper = fn() {\n" +
            "    let i = 0\n" +
            "\n" +
            "    loop {\n" +
            "        if i > 4 {\n" +
            "            return i\n" +
            "        }\n" +
            "\n" +
            "        i = i + 1\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "puts(looper())\n";

//        final ObjectType result = parse(src).accept(new AstWalkingInterpreter());

//        assertThat(result, is(NullType.NULL));
    }
}

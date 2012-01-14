package de.weltraumschaf.caythe;

import java.util.Arrays;
import joptsimple.OptionSet;
import joptsimple.OptionParser;
import joptsimple.OptionException;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class OptionsTest {

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test public void createParser() {
        OptionSet opts;
        OptionParser p = Options.createParser();
        opts = p.parse(new String[]{"-d", "-h", "-x", "-i", "-n", "-a", "-f", "-c", "-r"});

        assertTrue(opts.has("d"));
        assertTrue(opts.has("h"));
        assertTrue(opts.has("x"));
        assertTrue(opts.has("i"));
        assertTrue(opts.has("n"));
        assertTrue(opts.has("a"));
        assertTrue(opts.has("f"));
        assertTrue(opts.has("c"));
        assertTrue(opts.has("r"));

        opts = p.parse(new String[]{"-l", "pascal", "-m", "compile"});
        assertTrue(opts.has("l"));
        assertEquals("pascal", opts.valueOf("l"));
        assertTrue(opts.has("m"));
        assertEquals("compile", opts.valueOf("m"));

        opts = p.parse(new String[]{"--lang", "pascal", "--mode", "compile"});
        assertTrue(opts.has("lang"));
        assertEquals("pascal", opts.valueOf("lang"));
        assertTrue(opts.has("mode"));
        assertEquals("compile", opts.valueOf("mode"));

        thrown.expect(OptionException.class);
        thrown.expectMessage(JUnitMatchers.containsString("l"));
        opts = p.parse(new String[]{"-l"});
        thrown.expect(OptionException.class);
        thrown.expectMessage(JUnitMatchers.containsString("lang"));
        opts = p.parse(new String[]{"--lang"});

        thrown.expect(OptionException.class);
        thrown.expectMessage(JUnitMatchers.containsString("m"));
        opts = p.parse(new String[]{"-m"});
        thrown.expect(OptionException.class);
        thrown.expectMessage(JUnitMatchers.containsString("mode"));
        opts = p.parse(new String[]{"--mode"});
    }

    @Test public void optionValues() {
        Options opts;
        OptionParser p = Options.createParser();

        opts = new Options(p.parse(new String[]{}));
        assertFalse(opts.isCrossRefernecesEnabled());
        assertFalse(opts.isDebugEnabled());
        assertFalse(opts.isFunctionCallsEnabled());
        assertFalse(opts.isFunctionReturnsEnabled());
        assertFalse(opts.isHelpEnabled());
        assertFalse(opts.isIntermediateCodeEnabled());
        assertFalse(opts.isLineNumbersEnabled());
        assertFalse(opts.isVarAssignsEnabled());
        assertFalse(opts.isVarFetchesEnabled());

        opts = new Options(p.parse(new String[]{"-d", "-h", "-x",  "-f", "-c", "-r"}));
        assertTrue(opts.isCrossRefernecesEnabled());
        assertTrue(opts.isDebugEnabled());
        assertTrue(opts.isFunctionCallsEnabled());
        assertTrue(opts.isFunctionReturnsEnabled());
        assertTrue(opts.isHelpEnabled());
        assertFalse(opts.isIntermediateCodeEnabled());
        assertFalse(opts.isLineNumbersEnabled());
        assertFalse(opts.isVarAssignsEnabled());
        assertTrue(opts.isVarFetchesEnabled());

        opts = new Options(p.parse(new String[]{"-d", "-h", "-x", "-i", "-n", "-a", "-f", "-c", "-r"}));
        assertTrue(opts.isCrossRefernecesEnabled());
        assertTrue(opts.isDebugEnabled());
        assertTrue(opts.isFunctionCallsEnabled());
        assertTrue(opts.isFunctionReturnsEnabled());
        assertTrue(opts.isHelpEnabled());
        assertTrue(opts.isIntermediateCodeEnabled());
        assertTrue(opts.isLineNumbersEnabled());
        assertTrue(opts.isVarAssignsEnabled());
        assertTrue(opts.isVarFetchesEnabled());

        opts = new Options(p.parse(new String[]{"-l", "pascal", "-m", "compile"}));
        assertEquals("pascal", opts.getLanguage());
        assertEquals("compile", opts.getMode());

        opts = new Options(p.parse(new String[]{"--lang", "pascal", "--mode", "compile"}));
        assertEquals("pascal", opts.getLanguage());
        assertEquals("compile", opts.getMode());

        opts = new Options(p.parse(new String[]{"--lang", "pascal", "--mode", "execute", "-ixn", "pascal/newton.pas"}));
        assertEquals("pascal", opts.getLanguage());
        assertEquals("execute", opts.getMode());
        assertTrue(opts.isCrossRefernecesEnabled());
        assertTrue(opts.isIntermediateCodeEnabled());
        assertTrue(opts.isLineNumbersEnabled());
        assertEquals(Arrays.asList("pascal/newton.pas"), opts.nonOptionArguments());
    }

    @Test public void createHelpText() {
        StringBuilder sb = new StringBuilder();
        sb.append(" -l, --lang <name>              The language used.\n");
        sb.append(" -m, --mode <execute|compile>   Either compile or execute.\n");
        sb.append(" -x                             Show variable cross reference.\n");
        sb.append(" -i                             Intermediate code tree.\n");
        sb.append(" -n                             Show line numbers.\n");
        sb.append(" -a                             Show variable asignemt infos.\n");
        sb.append(" -f                             Show variable fetch infos.\n");
        sb.append(" -c                             Show function call infos.\n");
        sb.append(" -r                             SHow function return infos.\n");
        sb.append(" -d                             Show debug output.\n");
        sb.append(" -h                             Show this help.");

        assertEquals(sb.toString(), Options.createHelpText());
    }

    @Test public void createUsage() {
        assertEquals("--lang <name> --mode <execute|compile> [-xinafcrdh]", Options.createUsage());
    }
}

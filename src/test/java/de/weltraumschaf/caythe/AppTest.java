package de.weltraumschaf.caythe;

import java.util.Arrays;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class AppTest {

    /**
     * -l pascal|caythe --execute|--compile -i -x -h -d
     */
    @Test public void parseCliArgs() {
        OptionParser parser = new OptionParser();
        parser.accepts("execute");
        parser.accepts("compile");
        parser.accepts("l").withRequiredArg();
        parser.accepts("i");
        parser.accepts("x");
        parser.accepts("h");
        parser.accepts("d");

        String[] args1 = {"-l", "pascal", "--execute", "-i", "-x", "-h", "hello.pas"};
        OptionSet opts1 = parser.parse(args1);
        assertTrue(opts1.has("l"));
        assertEquals("pascal", opts1.valueOf("l"));
        assertTrue(opts1.has("execute"));
        assertFalse(opts1.has("compile"));
        assertTrue(opts1.has("i"));
        assertTrue(opts1.has("x"));
        assertTrue(opts1.has("h"));
        assertFalse(opts1.has("d"));
        assertEquals(Arrays.asList("hello.pas"), opts1.nonOptionArguments());

        String[] args2 = {"-l", "caythe", "--compile", "-i", "-d", "hello.pas"};
        OptionSet opts2 = parser.parse(args2);
        assertTrue(opts2.has("l"));
        assertEquals("caythe", opts2.valueOf("l"));
        assertFalse(opts2.has("execute"));
        assertTrue(opts2.has("compile"));
        assertTrue(opts2.has("i"));
        assertFalse(opts2.has("x"));
        assertFalse(opts2.has("h"));
        assertTrue(opts2.has("d"));
        assertEquals(Arrays.asList("hello.pas"), opts1.nonOptionArguments());

        String[] args3 = {"-l", "caythe", "--compile", "-ixhd", "hello.pas"};
        OptionSet opts3 = parser.parse(args3);
        assertTrue(opts3.has("l"));
        assertEquals("caythe", opts3.valueOf("l"));
        assertTrue(opts3.has("compile"));
        assertTrue(opts3.has("i"));
        assertTrue(opts3.has("x"));
        assertTrue(opts3.has("h"));
        assertTrue(opts3.has("d"));
        assertEquals(Arrays.asList("hello.pas"), opts1.nonOptionArguments());
    }
}

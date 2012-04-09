package de.weltraumschaf.caythe.frontend.pascal.tokens;

import static org.junit.Assert.fail;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class PascalNumberTokenTest {

    @Ignore
    @Test public void testExtract() throws Exception {
        System.out.println("extract");
        PascalNumberToken instance = null;
        instance.extract();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Ignore
    @Test public void testExtractNumber() throws Exception {
        System.out.println("extractNumber");
        StringBuilder textBuffer = null;
        PascalNumberToken instance = null;
        instance.extractNumber(textBuffer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}

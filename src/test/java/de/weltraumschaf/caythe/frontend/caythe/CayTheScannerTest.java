package de.weltraumschaf.caythe.frontend.caythe;

import de.weltraumschaf.caythe.frontend.Source;
import de.weltraumschaf.caythe.util.SourceHelper;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CayTheScannerTest {

    private static final String FIXTURE_DIR = "/de/weltraumschaf/caythe/frontend/caythe";

    private Source createSourceFromFixture(String fixtureFile) throws FileNotFoundException, URISyntaxException {
        URL resource = getClass().getResource(FIXTURE_DIR + '/' + fixtureFile);
        return SourceHelper.createFrom(resource.toURI());
    }

    @Test public void scan() throws Exception {
        CayTheScanner scanner = new CayTheScanner(createSourceFromFixture("real_source.ct"));
        assertEquals(null, scanner.extractToken());
    }
}

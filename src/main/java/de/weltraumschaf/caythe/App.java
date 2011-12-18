
package de.weltraumschaf.caythe;

import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sven Strittmatter <sst@kwick.de>
 * @author Kai Stempfle <kst@kwick.de>
 */
public class App {

    private boolean debug;

    public App(boolean debug) {
        this.debug = debug;
    }

    public int execute() throws Error {
        System.out.println("Hello world!");

        String propertyFile = "/META-INF/application.properties";
        InputStream is      = getClass().getResourceAsStream(propertyFile);
        Properties props    = new Properties();

        try {
            props.load(is);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            throw new Error("Error opening app properties: " + propertyFile);
        }

        String[] driverTypes = props.getProperty("foo").split(",");

        for (String driverType : driverTypes) {
            System.out.println(driverType);
        }

        return 0;
    }
}

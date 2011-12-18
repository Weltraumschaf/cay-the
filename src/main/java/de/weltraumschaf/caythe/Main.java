
package de.weltraumschaf.caythe;

import java.io.IOException;

/**
 *
 * @author Sven Strittmatter <sst@kwick.de>
 */
public class Main {
    private static boolean debug = true;

    public static void main(String[] args) {
        try {
            System.exit(run(args));
        } catch (Error e) {
            System.out.println("bla" + e.getMessage());

            if (debug) {
                e.printStackTrace();
            }

            System.exit(e.getCode());
        } catch (Exception e) {
            System.out.println("blub" + e.getMessage());

            if (debug) {
                e.printStackTrace();
            }

            System.exit(-1);
        }
    }

    public static int run(String[] args) throws Error {
        App app = new App(debug);
        return app.execute();
    }
}

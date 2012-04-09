package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.caythe.Language;
import de.weltraumschaf.caythe.frontend.caythe.CayTheScanner;
import de.weltraumschaf.caythe.frontend.caythe.CayTheTopDownParser;
import de.weltraumschaf.caythe.frontend.pascal.PascalScanner;
import de.weltraumschaf.caythe.frontend.pascal.PascalTopDownParser;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class FrontendFactory {

    public enum Type {
        TOP_DOWN
    }

    public static Parser createParser(Language lang, Source source) throws Exception {
        return createParser(lang, Type.TOP_DOWN, source);
    }

    public static Parser createParser(Language lang, Type type, Source source) throws Exception {
        switch (lang) {
            case PASCAL: {
                Scanner scanner = new PascalScanner(source);

                if (Type.TOP_DOWN == type) {
                    return new PascalTopDownParser(scanner);
                } else {
                    throw new Exception("Other pascal parsers than TOP_DOWN are not implemented, yet!");
                }
            }
            case CAYTHE: {
                Scanner scanner = new CayTheScanner(source);
                return new CayTheTopDownParser(scanner);
            }
            default: {
                throw new Exception(String.format("Invalid language %s or type %s!", lang, type));
            }
        }
    }
}

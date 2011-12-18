package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.caythe.frontend.pascal.PascalParserTD;
import de.weltraumschaf.caythe.frontend.pascal.PascalScanner;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class FrontendFactory {
    public enum Language {
        PASCAL
    }

    public enum Type {
        TOP_DOWN
    }

    public static Parser createParser(Language lang, Type type, Source source) throws Exception {
        if (Language.PASCAL == lang && Type.TOP_DOWN == type) {
            Scanner scanner = new PascalScanner(source);
            return new PascalParserTD(scanner);
        } else {
            throw new Exception(String.format("Invalid language %s or type %s!", lang, type));
        }
    }
}

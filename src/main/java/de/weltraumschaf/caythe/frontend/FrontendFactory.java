package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.caythe.Language;
import de.weltraumschaf.caythe.frontend.caythe.CayTheScanner;
import de.weltraumschaf.caythe.frontend.caythe.CayTheTopDownParser;
import de.weltraumschaf.caythe.frontend.pascal.PascalScanner;
import de.weltraumschaf.caythe.frontend.pascal.PascalTopDownParser;

/**
 * Factory to create {@link Parser parsers}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class FrontendFactory {

    /**
     * Creates the parser for the given language and source.
     *
     * @param lang
     * @param source
     * @return
     * @throws Exception
     */
    public static Parser createParser(Language lang, Source source) throws IllegalArgumentException {
        Scanner scanner = createScanner(lang, source);

        switch (lang) {
            case PASCAL: {
                return new PascalTopDownParser(scanner);
            }
            case CAYTHE: {
                return new CayTheTopDownParser(scanner);
            }
        }

        throw new RuntimeException(String.format("FrontendFactory#createScanner() should throw an exception on invalid language '%s'!", lang));
    }

    /**
     * Creates the scanner for the given language and source.
     * 
     * @param lang
     * @param source
     * @return
     * @throws IllegalArgumentException
     */
    private static Scanner createScanner(Language lang, Source source) throws IllegalArgumentException {
        switch (lang) {
            case PASCAL: {
                return new PascalScanner(source);
            }
            case CAYTHE: {
                return new CayTheScanner(source);
            }
            default: {
                throw new IllegalArgumentException(String.format("Unsupported language '%s'!", lang));
            }
        }
    }
}

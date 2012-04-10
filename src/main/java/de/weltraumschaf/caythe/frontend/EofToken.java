package de.weltraumschaf.caythe.frontend;

/**
 * Generic token to signal end of the file.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class EofToken extends Token {

    public EofToken(Source source) throws Exception {
        super(source);
    }

    @Override
    protected void extract() throws Exception {
        // There is nothing to extract at EOF.
    }

}

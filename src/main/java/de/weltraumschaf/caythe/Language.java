package de.weltraumschaf.caythe;

/**
 * Enumerates all implemented languages.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public enum Language {
    PASCAL, // The example from the book.
    CAYTHE; // My own first language

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

}

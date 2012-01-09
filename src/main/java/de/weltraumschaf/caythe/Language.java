package de.weltraumschaf.caythe;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public enum Language {
    PASCAL,
    CAYTHE;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

}

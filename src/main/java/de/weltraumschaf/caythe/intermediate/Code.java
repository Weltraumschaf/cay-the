package de.weltraumschaf.caythe.intermediate;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public interface Code {
    public CodeNode setRoot(CodeNode node);
    public CodeNode getRoot();
}
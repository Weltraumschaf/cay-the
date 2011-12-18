package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.caythe.intermediate.ICode;
import de.weltraumschaf.caythe.intermediate.SymbolTable;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public abstract class Parser {
    protected static SymbolTable symTab;

    static {
        symTab = null;
    }

    protected Scanner scanner;
    protected ICode iCode;

    public Parser(Scanner scanner) {
        this.scanner = scanner;
        this.iCode   = null;
    }

    public abstract void parse() throws Exception;
    public abstract int getErrorCount();

    public Token currentToken() {
        return scanner.currentToken();
    }

    public Token nextToken() throws Exception {
        return scanner.nextToken();
    }
}

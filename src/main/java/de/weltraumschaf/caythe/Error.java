package de.weltraumschaf.caythe;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class Error extends Throwable {

    private int code;

    public Error(String message, int code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public Error(String message, int code) {
        this(message, code, null);
    }

    public Error(String message) {
        this(message, 0, null);
    }

    public int getCode() {
        return code;
    }
    
}

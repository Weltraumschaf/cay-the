package de.weltraumschaf.caythe.frontend;

/**
 * Defines the interface for error code objects.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public interface ErrorCode {

    /**
     * Int status code of error.
     *
     * @return
     */
    public int getStatus();

    /**
     * The error code message.
     *
     * @return
     */
    public String getMessage();

}

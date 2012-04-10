package de.weltraumschaf.caythe.frontend;

/**
 * Defines the interface for error code objects.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface ErrorCode {

    /**
     * Status code of error.
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

package de.weltraumschaf.caythe;

/**
 * Exception type which provides an error code.
 *
 * Used inside {@link App} to gve proper exit codes to the system on errors.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class Error extends Throwable {

    /**
     * The default error code used if no code provided to constructor.
     */
    public static final int DEFAULT_ERROR_CODE = -1;

    /**
     * The error code.
     *
     * By default this is -1.
     */
    private int code;

    /**
     * Designated constructor.
     *
     * @param message The error message.
     * @param code    The error code.
     * @param cause   Former thrown exception.
     */
    public Error(String message, int code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    /**
     * Creates an error where {@link Throwable#getCause()} is null.
     *
     * @param message The error message.
     * @param code    The error code.
     */
    public Error(String message, int code) {
        this(message, code, null);
    }

    /**
     * Creates an error where {@link code} is -1 and {@link Throwable#getCause()} is null.
     *
     * @param message The error message.
     */
    public Error(String message) {
        this(message, DEFAULT_ERROR_CODE, null);
    }

    /**
     * Returns the error code.
     *
     * If the Error was not created by {@link Error#Error(java.lang.String, int)}
     * or {@link Error#Error(java.lang.String, int, java.lang.Throwable)} it will
     * return {@link Error#DEFAULT_ERROR_CODE} by default.
     *
     * @return
     */
    public int getCode() {
        return code;
    }

}

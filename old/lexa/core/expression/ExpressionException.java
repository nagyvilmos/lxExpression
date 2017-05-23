/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * ExpressionException.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: March 2013
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * DD-MON-YY    ??
 *================================================================================
 */
package lexa.core.expression;

/**
 * An exception that occurred building or evaluating an {@link Expression}.
 * @author William
 * @since 2013-03
 */
public class ExpressionException extends Exception {

    /**
     * Constructs a new exception with the specified detail message and cause.
     * <p>Note that the detail message associated with cause is not automatically
     * incorporated in this exception's detail message.
     * @param message   the detail message (which is saved for later retrieval by the
     * {@link Exception#getMessage()} method).
     * */
    public ExpressionException(String message) {
        super(message, null);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * <p>Note that the detail message associated with cause is not automatically
     * incorporated in this exception's detail message.
     * @param message   the detail message (which is saved for later retrieval by the
     * {@link Exception#getMessage()} method).
     * @param cause     the cause (which is saved for later retrieval by the
     * {@link Exception#getCause()} method). (A null value is permitted, and indicates that
     * the cause is nonexistent or unknown.
     */
    public ExpressionException(String message, Throwable cause) {
        super(message, cause);
    }
}

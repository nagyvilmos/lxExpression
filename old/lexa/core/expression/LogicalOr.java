/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * LogicalAnd.java
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
 * Perform a logical OR on the results from the expressions.
 * <p>Each expression must return results of type {@link Boolean}.
 * <p>If any result is {@code null}, the return will be {@code null}, otherwise it will be
 * {@code true} or {@code false} depending on the result.
 * <p>If any expression returns {@code null} or {@code true}, then no further expressions are evaluated.
 * @author William
 * @since 2013-03
 */
class LogicalOr
        extends MultipleExpression {

	static void logicalOr(ExpressionTokens tokens)
			throws ExpressionException
	{
		LogicalAnd.logicalAnd(tokens);
		if (tokens.getType() == WordType.OR)
		{
			Expression left = tokens.expression();
			LogicalOr.logicalOr(tokens.next());
			tokens.setExpression(new LogicalOr(left, tokens.expression()));
		}
	}

    /**
     * Create a logical OR object.
     * @param lhs the left hand side of the expression
     * @param rhs the right hand side of the expression
     */
    LogicalOr(Expression lhs, Expression rhs) {
        super(LogicalOr.class, lhs, rhs);

    }

    /**
     * Checks if the result of an evaluation is valid.
     * <br>
     * The result is valid if it is not {@code null} and can be caste to a {@link Boolean}.
     * @param result the result from an evaluated expression.
     * @return {@code true} if the result is valid.
     * @throws ExpressionException when an error occurs in validating the result.
     */
    @Override
    protected boolean validate(Object result) throws ExpressionException {
        return super.validate(result) &&
                Boolean.class.isAssignableFrom(result.getClass());
    }

    /**
     * Check if processing is completed.
     * <br>
     * Once the overall result is {@code true} then processing is complete.
     * @param overall the existing result from the previously evaluated expressions.
     * @return {@code true} if the result is set to {@code true}.
     */
    @Override
    protected boolean complete(Object overall) {
        return (Boolean)overall;
    }

    /**
     * Combines the existing overall result with the new result.
     * <p>As evaluation stops at any {@code true} result, the overall value will always
     * be {@code false}.  As a result this method will just return the new result.
     * @param overall the existing result from the previously evaluated expressions.
     * @param result the result from an evaluated expression.
     * @return The combined result.
     * @throws ExpressionException when an error occurs in combining the result.
     */
    @Override
    protected Object combine(Object overall, Object result) throws ExpressionException {
        return result;
    }

    /**
     * Returns a string to represent the expression.
     * @return a string representing the expression
     */
    @Override
    public String toString() {
        return "or" + super.toString();
    }
}

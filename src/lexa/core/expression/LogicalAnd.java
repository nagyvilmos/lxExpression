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
 * Perform a logical AND on the results from the expressions.
 * <p>Each expression must return results of type {@see Boolean}.
 * <p>If any result is {@code null}, the return will be {@code null}, otherwise it will be
 * {@code true} or {@code false} depending on the result.
 * <p>If any expression returns {@code null} or {@code false}, then no further expressions are evaluated.
 * @author William
 * @since 2013-03
 */
class LogicalAnd
        extends MultipleExpression {

	static void logicalAnd(ExpressionTokens tokens) throws ExpressionException
	{
		Compare.compare(tokens);
		if (tokens.getType() == WordType.AND)
		{
			Expression left = tokens.expression();
			LogicalAnd.logicalAnd(tokens.next());
			tokens.setExpression(new LogicalAnd(left, tokens.expression()));
		}
	}

    /**
     * Create a logical AND object.
     * @param lhs the left hand side of the expression
     * @param rhs the right hand side of the expression
     * @throws ExpressionException when an error occurs in creating the logical AND
     */
    LogicalAnd(Expression lhs, Expression rhs) {
        super(LogicalAnd.class, lhs, rhs);
    }

    /**
     * Checks if the result of an evaluation is valid.
     * <br>
     * The result is valid if it is not {@code null} and can be caste to a {@see Boolean}.
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
     * Once the overall result is {@code false} then processing is complete.
     * @param overall the existing result from the previously evaluated expressions.
     * @return {@code true} if the result is set to {@code false}.
     * @throws ExpressionException when an error occurs in checking the result.
     */
    @Override
    protected boolean complete(Object overall) {
        return !(Boolean)overall;
    }

    /**
     * Combines the existing overall result with the new result.
     * <p>As evaluation stops at any {@code false} result, the overall value will always
     * be {@code true}.  As a result this method will just return the result.
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
        return "and" + super.toString();
    }
}

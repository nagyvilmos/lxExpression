/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * Multiply.java
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
 * Multiply the results of multiple expressions.
 * @author William
 * @since 2013-03
 */
public class Multiply
    extends Numeric {

	static void term(ExpressionTokens tokens)
			throws ExpressionException
	{
		Power.factor(tokens);
		Expression left = tokens.expression();
		switch (tokens.getType())
		{
			case MULTIPLY:
			{
				Multiply.term(tokens.next());
				tokens.setExpression(new Multiply(left,tokens.expression()));
				break;
			}
			case DIVIDE:
			{
				Multiply.term(tokens.next());
				tokens.setExpression(new Divide(left,tokens.expression()));
				break;
			}
		}
	}

    /**
     * Create a numeric expression to add a list of expressions.
     * @param expressions a list of expressions to parse
     */
    public Multiply(Expression ... expressions) {
        super(Multiply.class, expressions);
    }

    /**
     * Calculate the multiple of two {@link Double} numbers.
     * @param lhs the left hand side of the expression.
     * @param rhs the right hand side of the expression.
     * @return the sum of the two numbers.
     */
    @Override
    protected Double calcDouble(Double lhs, Double rhs) {
        return lhs * rhs;
    }

    /**
     * Calculate the multiple of two {@link Integer} numbers.
     * @param lhs the left hand side of the expression.
     * @param rhs the right hand side of the expression.
     * @return the sum of the two numbers.
     */
    @Override
    protected Integer calcInteger(Integer lhs, Integer rhs) {
        return lhs * rhs;
    }

    /**
     * Returns a string to represent the expression.
     * @return a string representing the expression
     */
    @Override
    public String toString() {
        return "multiply" + super.toString();
    }
}

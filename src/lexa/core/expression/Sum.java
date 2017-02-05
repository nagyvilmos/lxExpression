/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * Subtract.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: March 2013
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 2013-08-11   WNW -           Fix double to subtract not add.
 *================================================================================
 */
package lexa.core.expression;

import static lexa.core.expression.WordType.ADD;
import static lexa.core.expression.WordType.SUBTRACT;

/**
 * Subtract the results of multiple expressions.
 * @author William
 * @since 2013-03
 */
public class Sum
    extends Numeric {

	static void addition(ExpressionTokens tokens)
			throws ExpressionException
	{
		Multiply.term(tokens);
		Expression left = tokens.expression();
		switch (tokens.getType())
		{
			case ADD:
			{
				Sum.addition(tokens.next());
				tokens.setExpression(new Add(left,tokens.expression()));
				break;
			}
			case SUBTRACT:
			{
				Sum.addition(tokens.next());
				tokens.setExpression(new Subtract(left,tokens.expression()));
				break;
			}
		}
	}

    /**
     * Create a numeric expression to subtract a list of expressions.
     * @param expressions a list of expressions to parse
     */
    public Sum(Expression ... expressions) {
        super(Subtract.class, expressions);
    }

    /**
     * Calculate the difference of two {@see Double} numbers.
     * @param lhs the left hand side of the expression.
     * @param rhs the right hand side of the expression.
     * @return the difference of the two numbers.
     */
    @Override
    protected Double calcDouble(Double lhs, Double rhs) {
        return lhs + rhs;
    }

    /**
     * Calculate the difference of two {@see Integer} numbers.
     * @param lhs the left hand side of the expression.
     * @param rhs the right hand side of the expression.
     * @return the difference of the two numbers.
     */
    @Override
    protected Integer calcInteger(Integer lhs, Integer rhs) {
        return lhs + rhs;
    }

    /**
     * Returns a string to represent the expression.
     * @return a string representing the expression
     */
    @Override
    public String toString() {
        return "sub" + super.toString();
    }
}

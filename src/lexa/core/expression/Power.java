/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * Power.java
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
 * Raise the result of an expression by an exponent.
 * @author William
 * @since 2013-03
 */
public class Power
        extends Numeric {

	static void factor(ExpressionTokens tokens)
			throws ExpressionException
	{
		Conditional.condition(tokens);
		Expression left = tokens.expression();
		switch (tokens.getType())
		{
			case POWER:
			{
				Power.factor(tokens.next());
				tokens.setExpression(new Power(left,tokens.expression()));
				break;
			}
			case MODULUS:
			{
				Power.factor(tokens.next());
				tokens.setExpression(new Modulus(left,tokens.expression()));
				break;
			}
		}
	}

    /**
     * Create a numeric expression to raise a number to a power.
     * @param base an expression to provide the base
     * @param exponent an expression to provide the exponent
     */
    public Power(Expression base, Expression exponent) {
        super(Power.class, base, exponent);
    }

    /**
     * Calculate one {@see Double} number raised to the power of another.
     * @param lhs the left hand side of the expression; base.
     * @param rhs the right hand side of the expression; exponent.
     * @return returns {@code lhs ^ rhs}.
     */
    @Override
    protected Double calcDouble(Double lhs, Double rhs) {
        return Math.pow(lhs, rhs);
    }

    /**
     * Calculate one {@see Integer} number raised to the power of another.
     * @param lhs the left hand side of the expression; value.
     * @param rhs the right hand side of the expression; modulo.
     * @return {@code null} if the exponent is less than zero,
     * otherwise  returns {@code lhs ^ rhs}.
     */
    @Override
    protected Integer calcInteger(Integer lhs, Integer rhs) {
        if (rhs < 0) {
            // we cannot provide integer powers for negative numbers.
            return null;
        }
        return iPow(lhs, rhs);
    }

    /**
     * Simple implementation of binary power for integers.
     * @param base number to be raised
     * @param exp exponent to raise the number
     * @return the base raised to the exponent.
     */
    private int iPow (int base, int exp) {

        if (exp == 0) {
            return 1;
        }
        int mid = exp/2;
        int pow = iPow(base, mid);
        if (exp % 2 == 0) {
            return pow * pow;
        }
        return base * pow * pow;
    }

    /**
     * Returns a string to represent the expression.
     * @return a string representing the expression
     */    @Override
    public String toString() {
        return "pow" + super.toString();
    }
}

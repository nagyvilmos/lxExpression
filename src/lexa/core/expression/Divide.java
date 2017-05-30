/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * Divide.java
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
 * Divide the results of two expressions.
 * @author William
 * @since 2013-03
 */
public class Divide
    extends Numeric {

    /**
     * Create a numeric expression to divide one expression by another.
     * @param numerator an expression to provide the numerator
     * @param denominator an expression to provide the numerator
     */
    public Divide(Expression numerator, Expression denominator) {
        super(Divide.class, numerator, denominator);
    }

    /**
     * Calculate the division of two {@link Double} numbers.
     * @param lhs the left hand side of the expression; numerator.
     * @param rhs the right hand side of the expression; denominator.
     * @return {@code null} if the denominator is zero,
     * otherwise the lhs divided by the rhs.
     */
    @Override
    protected Double calcDouble(Double lhs, Double rhs) {
        if (rhs == 0.0) {
            return null;
        }
        return lhs / rhs;
    }

    /**
     * Calculate the division of two {@link Integer} numbers.
     * @param lhs the left hand side of the expression; numerator.
     * @param rhs the right hand side of the expression; denominator.
     * @return {@code null} if the denominator is zero,
     * otherwise the lhs divided by the rhs.
     */
    @Override
    protected Integer calcInteger (Integer lhs, Integer rhs) {
        if (rhs == 0) {
            return null;
        }
        return lhs / rhs;
    }

    /**
     * Returns a string to represent the expression.
     * @return a string representing the expression
     */
    @Override
    public String toString() {
        return "divide" + super.toString();
    }
}

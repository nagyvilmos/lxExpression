/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * Modulus.java
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
 * Find the modulus of two expressions.
 * @author William
 * @since 2013-03
 */
public class Modulus
        extends Numeric {

    /**
     * Create a numeric expression to find the modulus of two numbers.
     * @param value an expression to provide the value
     * @param modulo an expression to provide the modulo
     */
    public Modulus(Expression value, Expression modulo) {
        super(value, modulo);
    }

    /**
     * Calculate the modulus of two {@see Double} numbers.
     * @param lhs the left hand side of the expression; value.
     * @param rhs the right hand side of the expression; modulo.
     * @return {@code null} if the modulo is less than or equal to zero,
     * otherwise returns {@code lhs (mod rhs)}.
     */
    @Override
    protected Double calcDouble(Double lhs, Double rhs) {
        if (rhs <= 0.0) {
            return null;
        }
        return lhs % rhs;
    }

    /**
     * Calculate the modulus of two {@see Integer} numbers.
     * @param lhs the left hand side of the expression; value.
     * @param rhs the right hand side of the expression; modulo.
     * @return {@code null} if the modulo is less than or equal to zero,
     * otherwise returns {@code lhs (mod rhs)}.
     */
    @Override
    protected Integer calcInteger(Integer lhs, Integer rhs) {
        if (rhs <= 0) {
            return null;
        }
        return lhs % rhs;
    }

    /**
     * Returns a string to represent the expression.
     * @return a string representing the expression
     */
    @Override
    public String toString() {
        return "mod" + super.toString();
    }
}

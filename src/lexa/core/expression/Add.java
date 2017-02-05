/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * Add.java
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
 * Add the results of multiple expressions.
 * @author William
 * @since 2013-03
 */
public class Add
    extends Numeric {

    /**
     * Create a numeric expression to add a list of expressions.
     * @param expressions a list of expressions to parse
     */
    public Add(Expression ... expressions) {
        super(Add.class, expressions);
    }

    /**
     * Calculate the sum of two {@see Double} numbers.
     * @param lhs the left hand side of the expression.
     * @param rhs the right hand side of the expression.
     * @return the sum of the two numbers.
     */
    @Override
    protected Double calcDouble(Double lhs, Double rhs) {
        return lhs + rhs;
    }

    /**
     * Calculate the sum of two {@see Integer} numbers.
     * @param lhs the left hand side of the expression.
     * @param rhs the right hand side of the expression.
     * @return the sum of the two numbers.
     */
    @Override
    protected Integer calcInteger (Integer lhs, Integer rhs) {
        return lhs + rhs;
    }

    /**
     * Returns a string to represent the expression.
     * @return a string representing the expression
     */
    @Override
    public String toString() {
        return "add" + super.toString();
    }
}

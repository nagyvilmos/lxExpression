/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * Not.java
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

import lexa.core.data.DataSet;

/**
 * A NOT expression.
 * <br>
 * This class simply returns the NOT value of the base expression.
 * @author William
 * @since 2013-03
 */
public class Not
        extends Expression {

    /** the expression to NOT */
    private final Expression expression;

    /**
     * Create a NOT expression.
     * @param expression the expression to NOT
     */
    public Not(Expression expression) {
        this.expression = expression;
    }

    /**
     * Evaluate the NOT value.
     * @param data  The input to feed the expression.
     * @return The NOT of the base evaluation
     * @throws ExpressionException when an error occurs in evaluation the expression
     */
    @Override
    public Object evaluate(DataSet data)
            throws ExpressionException {
        Object object = this.expression.evaluate(data);
        if (object == null) {
            return null;
        }
        if (Boolean.class.isAssignableFrom(object.getClass())) {
            return !(Boolean)object;
        }
        throw new ExpressionException("Value not boolean.");
    }
}

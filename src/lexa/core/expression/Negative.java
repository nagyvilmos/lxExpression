/*==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * Negative.java (lxExpression)
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: March 2013
 *==============================================================================
 */
package lexa.core.expression;

import lexa.core.data.DataSet;

/**
 * A negative expression.
 * <br>
 * This class simply returns the negative value of the base expression.
 * @author William
 * @since 2013-03
 */
public class Negative extends Expression {

    /** the expression to negate */
    private final Expression expression;

    /**
     * Create a negative expression.
     * @param expression the expression to negate
     */
    public Negative(Expression expression) {
        this.expression = expression;
    }

    /**
     * Evaluate the negative value.
     * @param data  The input to feed the expression.
     * @return The negative of the base evaluation
     * @throws ExpressionException when an error occurs in evaluation the expression
     */
    @Override
    public Object evaluate(DataSet data)
            throws ExpressionException {
        Object object = this.expression.evaluate(data);
        if (object == null) {
            return null;
        }
        if (Integer.class.isAssignableFrom(object.getClass())) {
            return -(Integer)object;
        }
        if (Double.class.isAssignableFrom(object.getClass())) {
            return -(Double)object;
        }
        throw new ExpressionException("Value not a number.");
    }

    static void negative(ExpressionTokens tokens)
            throws ExpressionException
    {
        Element.element(tokens);
        tokens.setExpression(
                new Negative(
                        tokens.expression()
                )
        );
    }
}

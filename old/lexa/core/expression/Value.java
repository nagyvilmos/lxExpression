/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * Value.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: October 2013
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
 * Represent an expression to return a value
 * <p>A value returns the item from a {@link DataSet} referenced by
 * its expression.
 * @author William
 * @since 2013-03
 */
public class Value
        extends Expression {

	static void primary(ExpressionTokens tokens) throws ExpressionException
	{
		switch (tokens.getType())
		{
			case SUBTRACT:
			{
				Element.element(tokens.next());
				tokens.setExpression(new Negative(tokens.expression()));
				return;
			}
			case NOT:
			{
				Element.element(tokens.next());
				tokens.setExpression(new Not(tokens.expression()));
				return;
			}
			case VALUE:
			{
				Element.element(tokens.next());
				tokens.setExpression(new Value(tokens.expression()));
				return;
			}
			default:
			{
				Element.element(tokens);
			}
		}
	}
    /** The expression of the element */
    private final Expression expression;

    /**
     * Create a value.
     * @param expression the expression that defines which value is required.
     */
    public Value(Expression expression) {

        this.expression = expression;
    }

    /**
     * Evaluation the element.
	 * 
     * <p>Retrieve the item referenced by the return of evaluating the expression parameter. 
     * @param data  The input to feed the expression.
     * @return The result of the evaluation
     * @throws ExpressionException when an error occurs in evaluation the expression
     */
    @Override
    public Object evaluate(DataSet data)
            throws ExpressionException
	{
		return data.getValue((String)this.expression.evaluate(data));
    }

    /**
     * The string representation of the element.
     * @return a string representing the element.
     */
    @Override
    public String toString()
	{
		return "val (" + this.expression.toString() + ')';
    }
}

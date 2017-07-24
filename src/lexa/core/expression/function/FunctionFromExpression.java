/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * FunctionFromExpression.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: September 2013
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * date			who	why			what
 *================================================================================
 */
package lexa.core.expression.function;

import lexa.core.data.DataSet;
import lexa.core.expression.Expression;
import lexa.core.expression.ExpressionException;


class FunctionFromExpression
        extends FunctionDefinition {
    private static final String ARGUMENTS = "arguments";
    private static final String DESCRIPTION = "description";
    private static final String EXPRESSION = "expression";
	private final String definition;
	private final String description;
    private Expression expression;

    FunctionFromExpression(String name, DataSet function)
	{
        super(name, function.getString(FunctionFromExpression.ARGUMENTS));
        this.definition = 
                function.getString(FunctionFromExpression.EXPRESSION);
		this.description = function.contains(FunctionFromExpression.DESCRIPTION) ?
				function.getString(FunctionFromExpression.DESCRIPTION) :
				this.definition;
	}

	@Override
	public String describe()
	{
		return this.description;
	}
    @Override
    public Object execute(DataSet arguments)
            throws ExpressionException
	{
        return this.expression.evaluate(arguments);
    }

	@Override
	public void parse(FunctionLibrary library)
			throws ExpressionException
	{
		this.expression = Expression.parse(definition, library);
	}

}

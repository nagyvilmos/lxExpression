/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lexa.core.expression.function.standard;

import lexa.core.expression.ExpressionException;
import lexa.core.expression.function.Function;
import lexa.core.expression.function.FunctionLibrary;

/**
 *
 * @author william
 */
public abstract class StaticFunction
		extends Function
{

	public StaticFunction(String name, String ... arguments)
	{
		super(name, arguments);
	}

	@Override
	public void parse(FunctionLibrary library)
			throws ExpressionException
	{
		// does not need to do anything;
	}
	
	
}
